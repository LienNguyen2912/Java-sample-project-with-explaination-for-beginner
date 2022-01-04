package TextEditor;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener {
	static final int WIDTH = 800;
	static final int HEIGHT = 800;
	JTextArea textArea;
	JScrollPane scrollPane;
	JLabel fontLabel;
	JSpinner fontSizeSpinner;
	JButton fontColorButton;
	JComboBox<String> fontBox;
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem openMenuItem;
	JMenuItem saveMenuItem;
	JMenuItem exitMenuItem;
	
	
	public TextEditor() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Simple text editor");
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);

		// Add text area
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial", Font.PLAIN, 20));

		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(700, 700));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Add font edit functionality
		fontLabel = new JLabel("Font: ");
		
		fontSizeSpinner = new JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(50, 20));
		fontSizeSpinner.setValue(20);
		fontSizeSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				textArea.setFont(
						new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue()));
			}
		});
		
		// Add font color functionality
		fontColorButton = new JButton("Color");
		fontColorButton.addActionListener(this);
		
		// Add font type functionality
		String[] fonts  = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontBox = new JComboBox<>(fonts);
		fontBox.setSelectedItem("Arial");
		fontBox.addActionListener(this);
		
		// Add menu bar containing 3 menu items
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		openMenuItem = new JMenuItem("Open");
		saveMenuItem = new JMenuItem("Save");
		exitMenuItem = new JMenuItem("Exit");
		
		openMenuItem.addActionListener(this);
		saveMenuItem.addActionListener(this);
		exitMenuItem.addActionListener(this);
		
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(exitMenuItem);
		menuBar.add(fileMenu);
		
		// Add to main frame
		this.setJMenuBar(menuBar);
		this.add(fontLabel);
		this.add(fontSizeSpinner);
		this.add(fontColorButton);
		this.add(fontBox);
		this.add(scrollPane);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Change font color
		if (e.getSource() == fontColorButton) {
			Color currentColor = textArea.getForeground();
			Color color = JColorChooser.showDialog(null, "Choose a color", currentColor);
			textArea.setForeground(color);
		}
		
		// Change font name
		if (e.getSource() == fontBox) {
			textArea.setFont(new Font((String)fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));
		}
		
		// Open menu item
		if (e.getSource() == openMenuItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
			fileChooser.setFileFilter(filter);
			
			int response = fileChooser.showOpenDialog(null);
			if (response == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;
				try {
					fileIn = new Scanner(file);
					if (file.isFile()) {
						while(fileIn.hasNext()) {
							String line = fileIn.nextLine() + "\n";
							textArea.append(line);
						}
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					fileIn.close();
				}
			}
		}
		
		// Save file
		if (e.getSource() == saveMenuItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			int response = fileChooser.showSaveDialog(null);
			if (response == JFileChooser.APPROVE_OPTION) {
				File file;
				PrintWriter fileOut = null;
				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(textArea.getText());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					fileOut.close();
				}
			}
		}
		
		// Exit
		if (e.getSource() == exitMenuItem) {
			System.exit(0);
		}
	}

}
