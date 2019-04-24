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

public interface PCDDirectory extends Serializable {

	
	//	COMEÇA AQUI O CODIGO do Directory!!!!!!
	public final int maxSize = 1024;
	public static final int PORTO = 8080;
	public ServerSocket serverSocket;
	public Socket socket;
	public ArrayList <Client> usersList;
	
	public static void main(String[] args) throws IOException {
		try {
			new PCDDirectory().startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void startServer() throws IOException {
		usersList = new ArrayList<Client>();
		serverSocket = new ServerSocket(PORTO);
		System.out.println("Ready, wait for users.");
		while (true) {
			socket = serverSocket.accept();
			System.out.println("Conection accepted");
			doConnection(socket);
		}
	}

	public static void doConnection(Socket socket) throws IOException {
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

	public static void closeServerSocket() {
		try {
			System.out.println("Directory will close.");
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	ACABA AQUI O CODIGO DO DIRECTORY!!!!!
	
	/**
	* Método que verifica se existe um ficheiro ou uma sub-pasta nesta pasta com o
	* nome name.
	*
	* @param name nome da entrada a procurar
	* @return true caso exista um ficheiro ou pasta com o nome name false caso contrário
	* @throws IOException
	*/
	
	
	public abstract boolean fileExists(String name) throws IOException;
	public abstract PCDFile newFile(String name) throws FileSystemException, IOException;
	
	public abstract void delete(String name) throws FileSystemException, IOException;
	/**
	* Método que devolve um array com o nome de todas as pastas e ficheiros existentes
	* nesta pasta.
	*
	* @return um array com todos os nomes
	* @throws FileSystemException caso a entrada não exista.
	* @throws IOException
	*/
	public abstract String[] getDirectoryListing() throws FileSystemException, IOException;
	/**
	* Método que devolve uma entrada que representa o ficheiro (PCDFile)
	* existente nesta pasta com o nome name.
	*
	* @param name nome do ficheiro
	* @return a entrada que representa o ficheiro
	* @throws IOException
	* @throws FileSystemException
	*/
	public abstract PCDFile getFile(String name) throws FileSystemException, IOException;
}
