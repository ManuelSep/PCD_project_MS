import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
				texts.add(fileName);
//				gui.setTextList(texts);
				modelList.addElement(fileName);
				
			}
			gui = new GUI();
			addButtonActions();
			
			gui.open();
			
			
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addButtonActions() {
//		
//		gui.getBtnEditar().addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				btnEditar();
//			}
//			
//			private void btnEditar() {
//				for (int i = 0; i < buttonOptions.length; i++) {
//					if(buttonOptions[i] == "Editar") {
//						gui.createButtonFrame(buttonOptions[i]);
//					}
//				}
//			}
//		});
		
//			gui.getBtnSearch().addActionListener(new ActionListener() {
//
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					buttonSearch();
//				}
//
//				private void buttonSearch() {
//					gui.getModelList().clear();
//					String word = gui.getTextSearch().getText();
//					gui.getModelList().addElement(word);
//				}
//			});
		
//			gui.getBtnDownload().addActionListener(new ActionListener() {
//
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					download();
//				}
//
//				private void download() {
//					//gui.getModelList().clear();
//					String word = gui.getListResult().getSelectedValue();
//					gui.getModelList().addElement(word);
//				}
//			});
		
		

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
