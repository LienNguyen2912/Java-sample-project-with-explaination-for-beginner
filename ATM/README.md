# ATM - Java sample project with explaination
We will create a simple **ATM** application in Java.</br>
When launching the application, a bank is created first then a user named Yamaki Kento is created with a random uuid assigned by the bank.</br>
A bank may have many users and many accounts. A user can have one or many accounts. Every account can have many transactions.</br>
![1](https://user-images.githubusercontent.com/73010204/148015217-ce2fe648-428c-4690-8649-d14c31c53d2f.PNG)</br>
★The user is asked to log in by entering the user UUID and password( pin) until login is successful</br>
![2](https://user-images.githubusercontent.com/73010204/148015222-82fe8f27-2f83-4986-b180-c928900b92eb.PNG)</br>
★After login successfully, the user's account summary information and a menu is displayed.</br>
![3](https://user-images.githubusercontent.com/73010204/148015223-4526ed18-af76-4e71-a36c-f340c6a452ed.PNG)</br>
★The menu contains:
- 1 Show account transaction history
- 2 Withdraw
- 3 Deposit
- 4 Transfer
- 5 Quit

★**If the user selects 1**, the account index to show transaction history will be asked. Please remember that a user can have mutliple accounts</br>
![6](https://user-images.githubusercontent.com/73010204/148015229-4504fcb1-3c8e-4987-ad54-6fa57813bdaf.PNG)
★**If the user selects 2**, prompt for the user to enter the account index to withdraw and the amount. The amount input must be less than the account balance. Then a withdrawal transaction will be created and executed.</br>
★**If the user selects 3**, prompt for user to enter the account index to deposit and the amount. Then a deposit transaction will be created and executed.</br>
![4](https://user-images.githubusercontent.com/73010204/148015227-6d033f84-08ab-428c-b5ae-3a59fdb2e7c0.PNG)</br>
★**If the user selects 4**, prompt for the user to enter the 2 account indexes to withdraw and deposit, also the amount. Then 2 transactions for withdrawing and deposit will be created and executed.</br>
![5](https://user-images.githubusercontent.com/73010204/148015228-8bb0a865-6dd3-4e68-8cb5-3d4d5320e2bc.PNG)</br>
★After completing processing every selection, the menu is displayed again until the user enters **5** (quit)

## Class diagram
We will create 4 classes:
- ATM
- Bank
- User
- Account
- Transaction

![a](https://user-images.githubusercontent.com/73010204/148015230-7d496d9b-b546-4e87-9f32-c1f2a40aa937.PNG)
## Sequence diagram
Below is the sequence diagram when the application starts</br>
![b](https://user-images.githubusercontent.com/73010204/148015233-86e9ad92-b032-41b0-8f3d-1da23db7e8db.PNG)</br>
Next is the sequence diagram when the user logins, or selects 2. Because the diagrams of other selections are similar so no need to draw all.</br>
![d](https://user-images.githubusercontent.com/73010204/148021240-6852dc75-241c-4d60-8183-95caf61bc92d.PNG)

## Completed source code
**ATM.java**
```sh
package ATM;

import java.util.Scanner;

public class ATM {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// initialize a Bank
		Bank theBank = new Bank ("MUFG");
	
		// add a user
		User aUser = theBank.addUser("Kento", "Yamaki", "abc1234");
		
		// create one more account for created user
		Account newAccount = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		// login
		User curUser;
		while (true) {
			curUser = ATM.loginPrompt(theBank, sc);
			ATM.printUserMenu(curUser, sc);
		}
	}
	
	public static User loginPrompt(Bank bank, Scanner sc) {
		String userId;
		String pin;
		User authUser;
		// prompt user id/ pin until login successfully
		do {
			System.out.println("Welcome to the " + bank.getName());
			System.out.println("Enter user id: ");
			userId = sc.nextLine();
			System.out.println("Enter pin: ");
			pin = sc.nextLine();
			
			authUser = bank.userLogin(userId, pin);
			if (authUser == null) {
				System.out.println("Incorrect user ID/ pin. Please try again");
			}
		}while(authUser == null);
		return authUser;
	}
	
	public static void printUserMenu(User user, Scanner sc) {
		// print a summary of the user's account
		user.printAccountSummary();
		// show menu list
		int choice;
		do {
			System.out.println("Welcome "+ user.getFullName() + "! What would you like to do");
			System.out.println("1 Show account transaction history");
			System.out.println("2 Withdraw");
			System.out.println("3 Deposit");
			System.out.println("4 Transfer");
			System.out.println("5 Quit");
			System.out.println();
			System.out.println("Enter choice: ");
			choice = sc.nextInt();
			if (choice < 1 || choice > 5) {
				System.out.println("Enter choice from 1 ~ 5 please");
			}
		}while(choice < 1 || choice > 5);
		
		//process choice
		switch (choice) {
		case 1:
			ATM.showTransHistory(user, sc);
			break;
		case 2:
			ATM.withDraw(user, sc);
			break;
		case 3:
			ATM.deposit(user, sc);
			break;
		case 4:
			ATM.transfer(user, sc);
			break;
		case 5:
			// gobble up rest of previous input
			sc.nextLine();
			break;
		}
		
		//redisplay this menu unless user wants to quit
		if (choice != 5) {
			ATM.printUserMenu(user, sc);
		}
	}

	public static void showTransHistory(User user, Scanner sc) {
		int accountNoChoice;
		int numAccounts = user.getNumAccounts();
		do {
			System.out.printf("Enter the account no(1 - %d) whose transations you would like to see history ",
					numAccounts);
			accountNoChoice = sc.nextInt() - 1;
			if (accountNoChoice < 0 || accountNoChoice >= numAccounts) {
				System.out.printf("invalid account no. Enter the account no(1 - %d) please", numAccounts);
			}
		}while(accountNoChoice < 0 || accountNoChoice >= numAccounts);
	
		// print user's selected account 's transaction history
		user.printAccountTransactionHistory(accountNoChoice);
	}
	
	public static void withDraw(User user, Scanner sc) {
		int fromAccNo;
		double amount;
		double accBalance;
		int numAccounts = user.getNumAccounts();
		// get the account to transfer from
		do {
			System.out.printf("Enter the number of account to transfer from (1-%d): ", numAccounts);
			fromAccNo = sc.nextInt()-1;
			if (fromAccNo < 0 || fromAccNo >= numAccounts) {
				System.out.printf("invalid account no. Enter the account no(1 - %d) please", numAccounts);
			}
		}while (fromAccNo < 0 || fromAccNo >= numAccounts);
		accBalance = user.getAccountBalance(fromAccNo);
		// get the amount to transfer
		do {
			System.out.printf("Enter amount to transfer (max $%.2f): $", accBalance);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than 0");
			} else if (amount > accBalance) {
				System.out.println("Amount must be smaller the current balance " + accBalance);
			}
		}while(amount < 0 || amount > accBalance);

		// do transfer
		sc.nextLine();
		// get transfer message
		System.out.println("Enter transfer message: ");
		String transferMessage = sc.nextLine();
		if (transferMessage == "") {
			transferMessage = String.format("Withdraw from account %s $%.2f", user.getAccountUUID(fromAccNo), amount);
		}
		user.addAccTransaction(fromAccNo, -1*amount, transferMessage);
	}
	
	public static void deposit(User user, Scanner sc) {
		int toAccNo;
		double amount;
		int numAccounts = user.getNumAccounts();
		// get the account to transfer to
		do {
			System.out.printf("Enter the number of account to deposit to (1-%d): ", numAccounts);
			toAccNo = sc.nextInt()-1;
			if (toAccNo < 0 || toAccNo >= numAccounts) {
				System.out.printf("invalid account no. Enter the account no(1 - %d) please", numAccounts);
			}
		}while (toAccNo < 0 || toAccNo >= numAccounts);
		
		// get the amount to deposit
		do {
			System.out.printf("Enter amount to transfer: $");
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than 0");
			}
		}while(amount < 0);
		// do transfer
		sc.nextLine();
		// get transfer message
		System.out.println("Enter transfer message: ");
		String transferMessage = sc.nextLine();
		if (transferMessage == "") {
			transferMessage = String.format("Deposit to account %s $%.2f", user.getAccountUUID(toAccNo), amount);
		}
		user.addAccTransaction(toAccNo, amount, transferMessage);
	}
	
	public static void transfer(User user, Scanner sc) {
		int fromAccNo;
		int toAccNo;
		double amount;
		double accBalance;
		int numAccounts = user.getNumAccounts();
		// get the account to transfer from
		do {
			System.out.printf("Enter the number of account to transfer from (1-%d): ", numAccounts);
			fromAccNo = sc.nextInt()-1;
			if (fromAccNo < 0 || fromAccNo >= numAccounts) {
				System.out.printf("invalid account no. Enter the account no(1 - %d) please", numAccounts);
			}
		}while (fromAccNo < 0 || fromAccNo >= numAccounts);
		accBalance = user.getAccountBalance(fromAccNo);
		
		// get the account to transfer to
		do {
			System.out.printf("Enter the number of account to transfer to (1-%d): ", numAccounts);
			toAccNo = sc.nextInt()-1;
			if (toAccNo < 0 || toAccNo >= numAccounts) {
				System.out.printf("invalid account no. Enter the account no(1 - %d) please", numAccounts);
			}
		}while (toAccNo < 0 || toAccNo >= numAccounts);
		
		// get the amount to transfer
		do {
			System.out.printf("Enter amount to transfer (max $%.2f): $", accBalance);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than 0");
			} else if (amount > accBalance) {
				System.out.println("Amount must be smaller the current balance " + accBalance);
			}
		}while(amount < 0 || amount > accBalance);
		
		// do transfer
		user.addAccTransaction(fromAccNo, -1*amount, String.format("Transfer to account %s", user.getAccountUUID(toAccNo)));
		user.addAccTransaction(toAccNo, amount, String.format("Transfer from account %s", user.getAccountUUID(fromAccNo)));
	}
}
```

## Congratulations!
That's it! We 've implemented a simple ATM application in Java
## Reference
Youtube **TechLiterate** channel
