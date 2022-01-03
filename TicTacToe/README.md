# Tic-Tac-Toe  Java sample project with explaination
We will create a basic Tic-Tac-Toe game in Java.</br>
![1](https://user-images.githubusercontent.com/73010204/147872658-7ded9873-c59d-44f9-bc85-0313e6c4e9a6.png)
![2](https://user-images.githubusercontent.com/73010204/147872659-db3fd275-f99f-4315-a3d4-daec77605de8.png)
![3](https://user-images.githubusercontent.com/73010204/147872660-eafe73a8-a846-4d8b-8c80-8329837e7d74.png)

## GUI components
Create a _Tictactoe_ class that implements _ActionListener_
```sh
public class TicTacToe implements ActionListener
```
Firstly, we need a frame to contain all widgets.
- When user click **X** button, the application is closed too
- Show the frame in the middle of the screen whenever it is loaded
```sh
JFrame frame = new JFrame("Tictactoe");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setSize(600, 600);
frame.getContentPane().setBackground(new Color(50, 50, 50));
frame.setLayout(new BorderLayout());
frame.setLocationRelativeTo(null);
```
Next, we need a _JPanel_ and a _JLabel_ to display game status such as next turn and the game winner
```sh
JPanel titlePanel = new JPanel();
JLabel titleTextField = new JLabel();
titleTextField.setBackground(new Color(25, 25, 25));
titleTextField.setForeground(new Color(25, 255, 0));
titleTextField.setFont(new Font("Ink Free",Font.BOLD,65));
titleTextField.setHorizontalAlignment(JLabel.CENTER);
titleTextField.setText("Tic-Tac-Toe");
titleTextField.setOpaque(true);

titlePanel.setLayout(new BorderLayout());
titlePanel.setBounds(0, 0, 600, 60);
titlePanel.add(titleTextField);
```
Then, we need a _JPanel_ containing 9 _JButton_ for users to play
```sh
static final int BUTTON_COUNT = 9; 
JPanel buttonPanel = new JPanel();
JButton[] buttons = new JButton[BUTTON_COUNT];
buttonPanel.setLayout(new GridLayout(3, 3));
buttonPanel.setBackground(new Color(150,150,150));

for (int i = 0; i < BUTTON_COUNT; i++) {
	buttons[i] = new JButton();
	buttonPanel.add(buttons[i]);
	buttons[i].setFont(new Font("MV Boli",Font.BOLD,120));
	buttons[i].setFocusable(false);
	buttons[i].addActionListener(this);
}
```
Finally, we add all on the main frame and set it visible
```sh
frame.add(titlePanel, BorderLayout.NORTH);
frame.add(buttonPanel);
frame.setVisible(true);
```
## Decide who plays next
Random the the first player
```sh
Random randfirstTurn = new Random();
```
- if _randfirstTurn_ is 0 then it is X turn.
- if _randfirstTurn_ is 1 then it is O turn.
- Use a variable called _boolean player1Turn_ to store the next turn value.
```sh
private void randFirstTurn() {
	if (randfirstTurn.nextInt(2) == 0) {
		player1Turn = true;
		titleTextField.setText("X turn");
	} else {
		player1Turn = false;
		titleTextField.setText("O turn");
	}
}
```
## Check game status whenever users input X or O

Game status could be
- X or O is winner, then game is finished.
- Game is drew, neither X nor O is winner, game is finished.
- Game can be continuous, update the next turn.
 
```sh
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
```
## Completed source code
**Main.java**
```sh
package TicTacToe;
public class Main {
	public static void main(String[] args) {
		TicTacToe tictactoe = new TicTacToe();
	}
}
```
**Tictactoe.java**
```sh
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
```

## Congratulations!
We 've implemented Tic-Tac-Toe game in Java
## Reference
Youtube **Bro code** channel
