import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class Client extends Thread{
	private DefaultListModel<String> modelList;
//	private JList<String> resultsList = new JList<>(modelList);
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private GUI gui;
	private ArrayList<String> texts = new ArrayList<>();
	private String [] buttonOptions = {"Tamanho","Exibir","Editar"};
	
	public static void main(String[] args) throws IOException {
		try {
			new Client().runClient();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void runClient() throws IOException, ClassNotFoundException {	
		try {
			socket = new Socket("localhost", 8080);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.writeObject(new String("INSC 127.0.0.1 8081"));
			System.out.println("Connected to Server");
			String[] messageFromServer = (String[])in.readObject();
			System.out.println("cheguei aqui");
			
			for (String fileName : messageFromServer) {
				System.out.println(fileName);
//				texts.add(fileName);
				
//				modelList.addElement(fileName);
				
			}
			
			gui = new GUI();
			addButtonActions();
			
			gui.open();
			
			gui.setTextList(messageFromServer);
			
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleServerAnswers() throws ClassNotFoundException, IOException{
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());


	}



	private void addButtonActions() {
		gui.getFrame().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				closeSocket();
			}

		});

	}
	
	private void closeSocket() {
		// TODO Auto-generated method stub
	}
}
