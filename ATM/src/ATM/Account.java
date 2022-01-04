package ATM;

import java.util.ArrayList;

public class Account {

	private String name;
	private User holder;
	private String uuid;
	private ArrayList<Transaction> transactions;
	
	public Account(String name, User holder, Bank theBank) {
		this.name = name;
		this.holder = holder;
		
		// get account uuid assigned by theBank
		this.uuid = theBank.getNewAccountUUID();
		
		// initialize transactions
		this.transactions = new ArrayList<>();
	}
	public String getUUID() {
		return this.uuid;
	}
	
	public String getSummaryLine() {
		// get the account's balance
		double balance = this.getBalance();
		if (balance >= 0) {
			return String.format(". Account UUID: %s:  Balance: %.2f. Account name: %s", this.uuid, balance, this.name);
		} else {
			return String.format(". Account UUID: %s:  Balance: (%.2f). Account name: %s", this.uuid, balance, this.name);
		}
	}
	
	public double getBalance() {
		double balance = 0;
		for (Transaction tran:transactions) {
			balance += tran.getAmount();
		}
		return balance;
	}
	
	public void printTransactionHistory() {
		System.out.println("Transaction history for account uuid: " + this.uuid);
		for (int i = this.transactions.size()-1; i >=0; i-- ) {
			System.out.printf(this.transactions.get(i).getSummaryLine());
			System.out.println();
		}
		System.out.println();
		
	}
	public void addTransaction(double amount, String transactionMessage) {
		Transaction trans = new Transaction(amount, this, transactionMessage);
		this.transactions.add(trans);
	}
}
