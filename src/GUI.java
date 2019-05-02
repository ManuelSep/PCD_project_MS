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
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

	public class GUI {
	
		private JFrame frame;
		private JButton btnTamanho;
		private JButton btnExibir;
		private JButton btnEditar;
		private JButton btnNovo;
		private JButton btnApagar;
		private JList<String> resultsList;
		private DefaultListModel<String> modelList;
		private String ButtonName;
		
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
			
			JPanel panelResult = new JPanel();
			frame.getContentPane().add(panelResult, BorderLayout.CENTER);
			modelList = new DefaultListModel<>();
			resultsList = new JList<>(modelList);
			JScrollPane scrollList = new JScrollPane(resultsList);
			scrollList.setPreferredSize(new Dimension(340, 218));
			panelResult.add(scrollList);
			
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
			buttonPanel.add(btnNovo);
			btnApagar  = new JButton("Apagar");
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
		
		public JList<String> getListResult() {
			
			return resultsList;
		}

		public DefaultListModel<String> getModelList() {
			return modelList;
		}
}
