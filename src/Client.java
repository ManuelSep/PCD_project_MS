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
			System.out.println("Connected to Server");
			System.out.println("cheguei aqui");
			/*
			gui = new GUI();
			addButtonActions();
			
			gui.open();
			
			gui.setTextList(messageFromServer);
			*/
			sendShowFile(new ShowFile("testeA.txt"), out);
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
			filterMessage(messageReceived, out);
		} catch (Exception exception){
			exception.getStackTrace();
		}
	}

	private void filterMessage(Object messageReceived, ObjectOutputStream out) {
		if (messageReceived instanceof SuccessCreatingFile) {
			System.out.println("reached handleServerAswers");
			String message = ((SuccessCreatingFile) messageReceived).getMessage();
			System.out.println(message);
		} else if (messageReceived instanceof PCDFile) {
			System.out.println("received file " + messageReceived);
		} else {
			System.out.println("ELSE" + messageReceived);
		}
	}

	public void sendShowFile(ShowFile showFile, ObjectOutputStream out) {
		try {
			System.out.println("SENDING SHOW FILE " + showFile.getFileName());
			out.writeObject(showFile);

		} catch (IOException ex) {
			ex.getStackTrace();
		}
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
