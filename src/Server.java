import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;

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
	
	public static void main(String[] args) {
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
		socket = serverSocket.accept();
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		System.out.println("Connection accepted");
		while (true) {

			handleClient();
		}
	}

	private void sendAllFiles() {
		try{
			System.out.println("Sending All Files" + localDirectory.getDirectoryListing());
			out.writeObject(localDirectory.getDirectoryListing());
		} catch (Exception exception) {
			exception.getStackTrace();
		}
	}

	private void handleClient() throws IOException {
		System.out.println("Handling with client");
		try {
			Object messageReceived = in.readObject();
			filterMessage(messageReceived, out);
		} catch (Exception exception){
			exception.getStackTrace();
		}
	}
	private void sendFile(FileActions fileActions) {
		String fileName = fileActions.getFileName();
		try {
			PCDFile fileToSend = localDirectory.getFile(fileName);
			out.writeObject(fileToSend.getFile());
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
			PCDFile newFiles = localDirectory.newFile(fileName);
			System.out.println("HERE" + newFiles);
			out.writeObject(new SuccessCreatingFile(fileName + newFiles.getFile().getAbsolutePath()));
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
			sendFile((ShowFile) messageReceived);
		} else if (messageReceived instanceof  EditFile) {
			sendFile((EditFile) messageReceived); // mudar isto para also editar, ie, receber texto novo.
		} else if (messageReceived instanceof DeleteFile) {
			deleteFile((DeleteFile) messageReceived);
		} else if (messageReceived instanceof SizeOfFile ) {
			sendSizeOfFile((SizeOfFile) messageReceived);
		} else if (messageReceived instanceof NewFile) {
			newFile((NewFile) messageReceived);
		} else if (messageReceived instanceof AllFiles) {
			System.out.println("Received a request to get AllFiles");
			sendAllFiles();
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
