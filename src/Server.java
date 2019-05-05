import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.nio.file.FileSystemException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	public ArrayList <Client> usersList;
	
	public static void main(String[] args) throws IOException {
		try {
			new Server("/Users/franciscoazevedo/Desktop").startServer();

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
			System.out.println("Conection accepted");
			sendAllFiles(socket);
			doConnection(socket);
		}
	}

	public void sendAllFiles(Socket socket) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(localDirectory.getDirectoryListing());
	}
	
	public static void doConnection(Socket socket) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		out.writeObject("messagem do server");
		String message;
		try {
			message = (String) in.readObject();
			System.out.println(message);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
