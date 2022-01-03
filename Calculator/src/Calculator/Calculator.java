package Calculator;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Calculator implements ActionListener {
	JFrame frame;
	JTextField textField;
	// 0 -> 9
	JButton[] numberButtons = new JButton[10];
	// add, sub, multiply, divide, decimal, delete, clear, negative, equal
	JButton[] functionButtons = new JButton[9];
	JButton addButton, subButton, mulButton, divButton;
	JButton dotButton, equButton, delButton, clrButton, negButton;
	JPanel panel;

	Font font = new Font("Courier", Font.ITALIC, 30);/* Ink Free */

	double num1 = 0, num2 = 0, result = 0;
	char operator;
	Boolean needClear = true;

	public Calculator() {
		frame = new JFrame("Simple calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420, 550);
		frame.setLayout(null);

		// Add text field
		textField = new JTextField();
		textField.setBounds(50, 25, 300, 50);
		textField.setFont(font);
		textField.setEditable(false);
		frame.add(textField);

		// Create function buttons and number buttons
		addButton = new JButton("+");
		subButton = new JButton("-");
		mulButton = new JButton("*");
		divButton = new JButton("/");
		dotButton = new JButton(".");
		equButton = new JButton("=");
		delButton = new JButton("Del");
		clrButton = new JButton("Clr");
		negButton = new JButton("(-)");

		functionButtons[0] = addButton;
		functionButtons[1] = subButton;
		functionButtons[2] = mulButton;
		functionButtons[3] = divButton;
		functionButtons[4] = dotButton;
		functionButtons[5] = equButton;
		functionButtons[6] = delButton;
		functionButtons[7] = clrButton;
		functionButtons[8] = negButton;

		for (int i = 0; i < functionButtons.length; i++) {
			functionButtons[i].addActionListener(this);
			functionButtons[i].setFont(font);
			functionButtons[i].setFocusable(false);
		}

		// Add number buttons
		for (int i = 0; i < numberButtons.length; i++) {
			numberButtons[i] = new JButton(String.valueOf(i));
			numberButtons[i].addActionListener(this);
			numberButtons[i].setFont(font);
			numberButtons[i].setFocusable(false);
		}

		// Add [-], Delete, Clear buttons
		negButton.setBounds(50, 430, 100, 50);
		frame.add(negButton);

		delButton.setBounds(150, 430, 100, 50);
		frame.add(delButton);

		clrButton.setBounds(250, 430, 100, 50);
		frame.add(clrButton);

		// Add +, -, *, / and number buttons
		panel = new JPanel();
		panel.setBounds(50, 100, 300, 300);
		panel.setLayout(new GridLayout(4, 4, 10, 10));

		panel.add(numberButtons[1]);
		panel.add(numberButtons[2]);
		panel.add(numberButtons[3]);
		panel.add(addButton);
		panel.add(numberButtons[4]);
		panel.add(numberButtons[5]);
		panel.add(numberButtons[6]);
		panel.add(subButton);
		panel.add(numberButtons[7]);
		panel.add(numberButtons[8]);
		panel.add(numberButtons[9]);
		panel.add(mulButton);
		panel.add(dotButton);
		panel.add(numberButtons[0]);
		panel.add(equButton);
		panel.add(divButton);

		frame.add(panel);

		// Show all
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		Calculator calc = new Calculator();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// if number button is clicked
		if (needClear == true && e.getSource() != addButton && e.getSource() != subButton && e.getSource() != mulButton
				&& e.getSource() != divButton && e.getSource() != negButton) {
			textField.setText("");
			needClear = false;
		}
		for (int i = 0; i < numberButtons.length; i++) {
			if (e.getSource() == numberButtons[i]) {
				textField.setText(textField.getText().concat(String.valueOf(i)));
			}
		}
		if (e.getSource() == dotButton) {
			if (!textField.getText().contains(".")) {
				textField.setText(textField.getText().concat("."));
			}
		}
		if (e.getSource() == addButton) {
			num1 = textField.getText().isEmpty() ? 0 : Double.parseDouble(textField.getText());
			operator = '+';
			textField.setText("");
		}
		if (e.getSource() == subButton) {
			num1 = textField.getText().isEmpty() ? 0 : Double.parseDouble(textField.getText());
			operator = '-';
			textField.setText("");
		}
		if (e.getSource() == mulButton) {
			num1 = textField.getText().isEmpty() ? 0 : Double.parseDouble(textField.getText());
			operator = '*';
			textField.setText("");
		}
		if (e.getSource() == divButton) {
			num1 = textField.getText().isEmpty() ? 0 : Double.parseDouble(textField.getText());
			operator = '/';
			textField.setText("");
		}
		if (e.getSource() == equButton) {
			num2 = textField.getText().isEmpty() ? 0 : Double.parseDouble(textField.getText());
			switch (operator) {
			case '+':
				result = num1 + num2;
				break;
			case '-':
				result = num1 - num2;
				break;
			case '*':
				result = num1 * num2;
				break;
			case '/':
				result = num1 / num2;
				break;
			}
			textField.setText(String.valueOf(result));
			num1 = result;
			num2 = 0;
			needClear = true;
		}
		if (e.getSource() == clrButton) {
			textField.setText("");
		}
		if (e.getSource() == delButton) {
			String temp = textField.getText();
			if (temp.length() > 0) {
				textField.setText(temp.substring(0, temp.length() - 1));
			}
			num1 = 0;
			num2 = 0;
		}
		if (e.getSource() == negButton) {
			double temp = textField.getText().isEmpty() ? 0 : Double.parseDouble(textField.getText());
			temp *= -1;
			textField.setText(String.valueOf(temp));
		}
	}

}
