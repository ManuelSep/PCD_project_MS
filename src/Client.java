import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

public class Client extends Thread {
	private Interface gui;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private LinkedList<Request> pendingRequests = new LinkedList<>();

	public static void main(String[] args) throws IOException {
		try {
			new Client().runClient();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void runClient() throws IOException, ClassNotFoundException {
		try {

			socket = new Socket("localhost", 8080);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			gui = new Interface(this);
			while (true) {
				handleServerAnswers();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleServerAnswers() throws ClassNotFoundException, IOException{
		System.out.println("reached handleServerAswers");
		try {
			Object messageReceived = in.readObject();
			pendingRequests.add((Request) messageReceived);
			filterMessage(messageReceived, out);
		} catch (Exception exception){
			exception.getStackTrace();
		}
	}

	private void filterMessage(Object messageReceived, ObjectOutputStream out) {
		if (messageReceived instanceof SuccessCreatingFile) {
			String message = ((SuccessCreatingFile) messageReceived).getMessage();
			System.out.println(message);
		} else if (messageReceived instanceof File) {
			System.out.println("Received file " + messageReceived);
			PCDFile fileReceived = new LocalFile((File) messageReceived);
			try {
				gui.setCurrentSelectShowFile(fileReceived.read());
				System.out.println("Content of txt file " + fileReceived.read());
			} catch (Exception exception) {
				exception.getStackTrace();
			}
		} else if (messageReceived instanceof SuccessSendingFileSize) {
			gui.setSize(((SuccessSendingFileSize) messageReceived).getSizeFile());
			System.out.println(((SuccessSendingFileSize)messageReceived).getMessage());
		} else if (messageReceived instanceof SuccessDeletingFile){
			System.out.println(((SuccessDeletingFile)messageReceived).getMessage());
		} else if (messageReceived instanceof Failure){
			if (messageReceived instanceof CommandNotFound)
				System.out.println(((CommandNotFound)messageReceived).getMessage());
			else {
				System.out.println(((Failure)messageReceived).getMessage());
			}
		} else if (messageReceived instanceof String[]) {
			System.out.println("Initializing the FileName list");
			gui.setTextList((String[])messageReceived);
		}

		else {
			System.out.println("Something went wrong");
		}
	}

	public void askForAllFiles(){
		try{
			System.out.println("Asking for all files");
			AllFiles afiles = new AllFiles();

			out.writeObject(afiles);
		} catch (Exception exception) {
			exception.getStackTrace();
		}
	}

	public void askToCreateNewFile(NewFile newFile) {
		try {
			System.out.println("SENDING SHOW FILE " + newFile.getFileName());
			out.writeObject(newFile);

		} catch (IOException ex) {
			ex.getStackTrace();
		}
	}

	public void askToShowFile(ShowFile showFile) {
		try {
			System.out.println("SENDING SHOW FILE " + showFile.getFileName());
			out.writeObject(showFile);

		} catch (IOException ex) {
			ex.getStackTrace();
		}
	}

	public void askToDeleteFile(DeleteFile deleteFile) {
		try {
			System.out.println("Asking to delete " + deleteFile.getFileName());
			out.writeObject(deleteFile);
		} catch (IOException exception) {
			exception.getStackTrace();
		}
	}

	public void askForSizeOfFile(SizeOfFile sizeOfFile){
		try {
			System.out.println("Asking for size of file " + sizeOfFile.getFileName());
			out.writeObject(sizeOfFile);
		} catch (IOException exception) {
			exception.getStackTrace();
		}
	}


	private void closeSocket() {
		// TODO Auto-generated method stub
	}
}
