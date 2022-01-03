package TicTacToe;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TicTacToe implements ActionListener{
	static final int BUTTON_COUNT = 9; 
	Random randfirstTurn = new Random();
	boolean player1Turn;
	// create a frame to contain all widgets
	JFrame frame = new JFrame("Tictactoe");
	
	// title to display next turn and the game result
	JPanel titlePanel = new JPanel();
	JLabel titleTextField = new JLabel();
	
	JPanel buttonPanel = new JPanel();
	JButton[] buttons = new JButton[BUTTON_COUNT];
		
	public TicTacToe() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.getContentPane().setBackground(new Color(50, 50, 50));
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null); // show windows in the middle of screen other than top left
		
		titleTextField.setBackground(new Color(25, 25, 25));
		titleTextField.setForeground(new Color(25, 255, 0));
		titleTextField.setFont(new Font("Ink Free",Font.BOLD,65));
		titleTextField.setHorizontalAlignment(JLabel.CENTER);
		titleTextField.setText("Tic-Tac-Toe");
		titleTextField.setOpaque(true);
		
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setBounds(0, 0, 600, 60);
		titlePanel.add(titleTextField);
		
		buttonPanel.setLayout(new GridLayout(3, 3));
		buttonPanel.setBackground(new Color(150,150,150));
		
		for (int i = 0; i < BUTTON_COUNT; i++) {
			buttons[i] = new JButton();
			buttonPanel.add(buttons[i]);
			buttons[i].setFont(new Font("MV Boli",Font.BOLD,120));
			buttons[i].setFocusable(false);
			buttons[i].addActionListener(this);
		}
		
		frame.add(titlePanel, BorderLayout.NORTH);
		frame.add(buttonPanel);
		frame.setVisible(true);
		
		randFirstTurn();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < BUTTON_COUNT; i++) {
			if (e.getSource() == buttons[i]) {
				if (!buttons[i].getText().equals("")) {
					return;
				}
				buttons[i].setForeground(new Color(255,0,0));
				if (player1Turn) {
					buttons[i].setText("X");
					player1Turn = false;
					titleTextField.setText("O turn");
				} else {
					buttons[i].setText("O");
					player1Turn = true;
					titleTextField.setText("X turn");
				}
				checkStatus();
			}
		}
	}

	private void randFirstTurn() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (randfirstTurn.nextInt(2) == 0) {
			player1Turn = true;
			titleTextField.setText("X turn");
		} else {
			player1Turn = false;
			titleTextField.setText("O turn");
		}
	}

	private void checkStatus() {
		// Check if X or O wins
		int[][] winPatterns = {	{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6},  
								{1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
		for (int i = 0; i < winPatterns.length; i++) {
			if (buttons[winPatterns[i][0]].getText().equals("")) {
				continue;
			}
			if (buttons[winPatterns[i][0]].getText().equals(buttons[winPatterns[i][1]].getText()) &&
					buttons[winPatterns[i][0]].getText().equals(buttons[winPatterns[i][2]].getText())) {
				if (buttons[winPatterns[i][0]].getText().equals("X")) {
					showWinner(winPatterns[i][0], winPatterns[i][1], winPatterns[i][2], true);	// X wins
					return;
				} else {
					showWinner(winPatterns[i][0], winPatterns[i][1], winPatterns[i][2], false);// O wins
					return;
				}
			}
		}
		
		// Check if game drew
		for (int i = 0; i < BUTTON_COUNT; i++) {
			if (buttons[i].getText().equals("")) {
				return;
			}
		}
		drawGame();
	}
	
	private void drawGame() {
		titleTextField.setText("Game drew");
		for (int i = 0; i < BUTTON_COUNT; i++) {
			buttons[i].setEnabled(false);
		}
	}
	
	private void showWinner(int pos1, int pos2, int pos3, boolean xWins) {
		buttons[pos1].setBackground(Color.CYAN);
		buttons[pos2].setBackground(Color.CYAN);
		buttons[pos3].setBackground(Color.CYAN);
		for (int i = 0; i < BUTTON_COUNT; i++) {
			buttons[i].setEnabled(false);
		}
		if (xWins) {
			titleTextField.setText("X wins");
		}
		else {
			titleTextField.setText("O wins");
		}
	}
}
