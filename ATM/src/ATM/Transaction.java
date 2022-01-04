package ATM;

import java.util.Date;

public class Transaction {
	private Date timestamp;
	private String transactionMessage;
	private Account inAccount;
	private double amount;
	
	public Transaction(double amount, Account inAccount) {
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.transactionMessage = "";
	}
	
	public Transaction(double amount, Account inAccount, String transactionMessage) {
		this(amount, inAccount);
		this.transactionMessage = transactionMessage;
	}
	
	public double getAmount() {
		return this.amount;
	}
	
	public String getSummaryLine() {
		if (this.amount >= 0) { // deposit
			return String.format("%s: $ %.2f : %s", 
					this.timestamp.toString(), this.amount, this.transactionMessage);
		} else { // withdraw
			return String.format("%s: $(%.2f) : %s", 
					this.timestamp.toString(), this.amount, this.transactionMessage);
		}
	}
}
