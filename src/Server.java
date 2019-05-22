import java.io.*;
import java.net.Socket;
import java.nio.file.FileSystemException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class Server implements Serializable {
	
	public final int maxSize = 1024;
	public static final int PORTO = 8080;
	private ServerSocket serverSocket;
	private Socket socket;
	private String rootName;
	private LocalDirectory localDirectory;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	public ArrayList <Client> usersList;
	
	public static void main(String[] args) throws IOException {
		try {
			new Server("../PCD_project_MS/src/text_files").startServer();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Server(String rootName) {
		this.rootName = rootName;
		localDirectory = new LocalDirectory(rootName); 
	}
	
	
	public void startServer() throws IOException {
//		usersList = new ArrayList<Client>();
		serverSocket = new ServerSocket(PORTO);
		System.out.println("Ready, wait for users.");
		while (true) {
			socket = serverSocket.accept();
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			System.out.println("Connection accepted");
			handleClient();
		}
	}

	public void sendAllFiles() throws IOException {
		out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(localDirectory.getDirectoryListing());
	}

	private void handleClient() throws IOException {
		System.out.println("Handling with client");
		try {
			Object messageReceived = in.readObject();
			System.out.println("Grab object" + messageReceived);
			System.out.println("Received object " + messageReceived);
			filterMessage(messageReceived, out);
		} catch (Exception exception){
			exception.getStackTrace();
		}
	}
	private void sendFile(FileActions fileActions) {
		System.out.println("RECHEAD SEND FILE");
		String fileName = fileActions.getFileName();
		try {
			System.out.println("RECHEAD SEND FILE 69" +  fileName + localDirectory.getFile(fileName));
			PCDFile fileToSend = localDirectory.getFile(fileName);
			System.out.println("Sending File:" + fileToSend );
			out.writeObject(fileToSend);
			System.out.println("Files");
		} catch (IOException exception) {
			exception.getStackTrace();
		}
	}

	private void deleteFile(DeleteFile deleteFile) {
		String fileName = deleteFile.getFileName();
		try {
			localDirectory.delete(fileName);
			out.writeObject(new SuccessDeletingFile(fileName));
		} catch (IOException exception) {
			exception.getStackTrace();
		}
	}

	private void sendSizeOfFile(SizeOfFile sizeOfFile) {
		String fileName = sizeOfFile.getFileName();
		try {
			int size = localDirectory.getFile(fileName).length();
			out.writeObject(new SuccessSendingFileSize(fileName, String.valueOf(size)));
		} catch (IOException exception) {
			exception.getStackTrace();
		}
	}

	private void newFile(NewFile newFile) {
		String fileName = newFile.getFileName();
		try {
			localDirectory.newFile(fileName);
			out.writeObject(new SuccessCreatingFile(fileName));
		} catch (IOException exception) {
			exception.getStackTrace();
		}
	}

	private void sendErrorMessage() {
		try {
			out.writeObject(new CommandNotFound());
		} catch (IOException exception) {
			exception.getStackTrace();
		}
	}


	private void filterMessage(Object messageReceived, ObjectOutputStream out) {
		if(messageReceived instanceof ShowFile) {
			System.out.println("reached showFile" + messageReceived);
			sendFile((ShowFile) messageReceived);
		} else if (messageReceived instanceof  EditFile) {
			sendFile((EditFile) messageReceived); // mudar isto para also editar, ie, receber texto novo.
		} else if (messageReceived instanceof DeleteFile) {
			deleteFile((DeleteFile) messageReceived);
		} else if (messageReceived instanceof SizeOfFile ) {
			sendSizeOfFile((SizeOfFile) messageReceived);
		} else if (messageReceived instanceof NewFile) {
			newFile((NewFile) messageReceived);
		} else {
			sendErrorMessage();
		}
	}

	public void closeServerSocket() {
		try {
			System.out.println("Directory will close.");
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
