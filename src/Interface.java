import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Interface {
    private Client client;
    private JFrame mainFrame;
    private JList<String> fileNameList = new JList<>();
    private DefaultListModel<String> files = new DefaultListModel<>();
    private JPanel screenPanel;
    private JPanel buttonPanel;
    private ArrayList<JButton> buttons = new ArrayList<>();
    private JButton sizeButton = new JButton("Size");
    private JButton showButton = new JButton("Show");
    private JButton editButton = new JButton("Edit");
    private JButton newButton = new JButton("New");
    private JButton deleteButton = new JButton("Delete");
    private String currentSize = "";
    private String currentSelectShowFile = "";

    public Interface(Client client) {
        this.client = client;
        client.askForAllFiles();
        initializeMainFrame();
        open();
    }

    private void initializeMainFrame(){
        mainFrame = new JFrame("File Explorer");
        mainFrame.setBounds(300, 300, 500, 300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        initializeScreenPanel();
        setButtons();
    }

    private void open(){
        mainFrame.setVisible(true);
    }

    private void initializeScreenPanel(){
        screenPanel = new JPanel();
        System.out.println("Ask for all files - Interface");
        fileNameList.setModel(files);
        screenPanel.add(fileNameList);
        mainFrame.add(screenPanel, BorderLayout.CENTER);
    }

    public void setTextList(String[] textsList){
        for (String string : textsList) {
            files.addElement(string);
        }
    }

    private void setButtons(){
        buttonPanel = new JPanel();
        buttons.add(sizeButton); buttons.add(showButton); buttons.add(editButton); buttons.add(newButton); buttons.add(deleteButton);
        for (JButton button: buttons) {
            buttonPanel.add(button);
            setActionListeners(button);
        }
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);

    }

    private void setActionListeners(JButton button){
        System.out.println("Reached the action listener");
        if(button.getText() == "Size") {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   sizeButtonListener();
                }
            });
        } else if (button.getText() == "Show") {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showButtonListener();
                }
            });
        }
    }

    private void showButtonListener(){
        currentSelectShowFile = "empty4848481";
        client.askToShowFile(new ShowFile(fileNameList.getSelectedValue()));
        if(currentSelectShowFile.equals("empty4848481"))
            showButtonListener();
        else
            showFileFrame();

    }

    private void showFileFrame(){
        JDialog showFileFrame = new JDialog();
        showFileFrame.setBounds(300, 300, 500, 300);
        showFileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel showFilePanel = new JPanel();
        JTextArea text = new JTextArea();
        text.insert(currentSelectShowFile, 0);
        showFilePanel.add(text);
        showFileFrame.add(showFilePanel);
        showFileFrame.setVisible(true);

    }

    private void sizeButtonListener(){
        currentSize = "";
        client.askForSizeOfFile(new SizeOfFile(fileNameList.getSelectedValue()));
        if (currentSize.isEmpty())
            sizeButtonListener();
        else
            JOptionPane.showMessageDialog(null,  "Size of file " + fileNameList.getSelectedValue() + " is of " + currentSize + "Bytes");
    }

    public void setSize(String size) {
        System.out.println("Setting the size of the current file to " +  size);
        this.currentSize = size;
    }

    public void setCurrentSelectShowFile(String text) {
        System.out.println("Setting the currentText to " + text);
        this.currentSelectShowFile = text;
    }
}
