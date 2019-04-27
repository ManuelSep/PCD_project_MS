import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread{

		private Socket socket;
		private ObjectOutputStream out;
		private ObjectInputStream in;
		private GUI gui;
		
		public static void main(String[] args) throws IOException {
			new Client().runClient();
		}

		private void runClient() throws IOException {	
			try {
				socket = new Socket("localhost", 8080);
				out = new ObjectOutputStream(socket.getOutputStream());
				//in = new ObjectInputStream(socket.getInputStream());
				out.writeObject(new String("INSC 127.0.0.1 8081"));
				System.out.println("Connected to Directory");
				
				gui = new GUI();
				addButtonActions();
				gui.open();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void addButtonActions() {
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
