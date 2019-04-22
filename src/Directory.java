import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
	
public class Directory {

	
//		private String route;
//		private LinkedList<User> users;
	//	
//		public Directory(String route) {
//			this.route = route;
//			users = new LinkedList<User>();
//		}
	//	
//		public void addUser(User user) {
//			users.add(user);
//		}
		private final int maxSize = 1024;
		public static final int PORTO = 8080;
		private ServerSocket serverSocket;
		private Socket socket;
		private ArrayList <Client> usersList;

		public static void main(String[] args) throws IOException {
			try {
				new Directory().startServer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void startServer() throws IOException {
			usersList = new ArrayList<Client>();
			serverSocket = new ServerSocket(PORTO);
			System.out.println("Ready, wait for users.");
			while (true) {
				socket = serverSocket.accept();
				System.out.println("Conection accepted");
				doConnection(socket);
			}
		}
	
		private void doConnection(Socket socket) throws IOException {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
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
