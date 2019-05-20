import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JList;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

	public class GUI {
	
		private JFrame frame;
		private JButton btnTamanho;
		private JButton btnExibir;
		private JButton btnEditar;
		private JButton btnNovo;
		private JButton btnApagar;
		private ArrayList<String> textsList;
		private DefaultListModel<String> files = new DefaultListModel<>();
		private JList<String> fileNameList = new JList<String>();
		private JPanel panelResult;
//		private JList<String> resultsList = new JList<>(modelList);
		private String ButtonName;
		private File file;
		
		public GUI() {
			initialize();
		}
		
		public void open(){
			frame.setVisible(true);
		}

		/**
		 * Create the application.
		 * Initialize the contents of the frame.
		 */
		private void initialize() {
			frame = new JFrame("File explorer");
			frame.setResizable(false);
			frame.setBounds(300, 300, 500, 300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(new BorderLayout());
						
			/***** Center *****/
			
			panelResult = new JPanel();
	
//			for (String text : getTextList()) {
//				panelResult.add(new JLabel(text));
//			}
			
			panelResult.add(fileNameList);
			frame.getContentPane().add(panelResult, BorderLayout.CENTER);
			
			
			
			/***** Bottom *****/
			
			JPanel buttonPanel = new JPanel();
			frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
			btnTamanho = new JButton("Tamanho");
			buttonPanel.add(btnTamanho);
			btnExibir = new JButton("Exibir");
			buttonPanel.add(btnExibir);
			btnEditar = new JButton("Editar");
			buttonPanel.add(btnEditar);
			
			btnNovo = new JButton("Novo");
			btnNovo.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					 file = new File("../PCD_project_MS/src/text_files/newFile.txt");
					  
					//Create the file
					try {
						if (file.createNewFile())
						{
						    System.out.println("File is created!");
						    files.addElement("newFile.txt");
						} else {
						    System.out.println("File already exists.");
						}
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					 
					//Write Content
					FileWriter writer;
					try {
						writer = new FileWriter(file);
						writer.write("this is a new file");
						writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			buttonPanel.add(btnNovo);
			
			btnApagar  = new JButton("Apagar");
			btnApagar.addActionListener(new ActionListener() {
				String fileNameToRemove;
				@Override
				public void actionPerformed(ActionEvent e) {
					//devolve o nome do ficheiro que estava selecionado quando se carrega no botao apagar
					fileNameToRemove = fileNameList.getSelectedValue();
					files.removeElement(fileNameToRemove);
					
					//isto é so para poder fazer varias vezes é codigo que vai ser mudado!
					file = new File("../PCD_project_MS/src/text_files/newFile.txt");
					file.delete();
				}
			});
			buttonPanel.add(btnApagar);
		}
		
	
		public void createButtonFrame(String ButtonName) {
			frame = new JFrame(ButtonName);
			frame.setResizable(false);
			frame.setBounds(200, 200, 400, 200);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//open(); Abre uma nova frame como desejado mas ao fechar fecha todas as JFrames
		}
		
		public JButton getBtnEditar() {
			return btnEditar;
		}
		
		public JFrame getFrame() {
			return frame;
		}
		
		
		public ArrayList<String> getTextList(){
			return textsList;
		}
		
		public void setTextList(String[] textsList){
			for (String string : textsList) {
				files.addElement(string);
			}
			fileNameList.setModel(files);
		}

		public DefaultListModel<String> getModelList() {
			return files;
		}
}
