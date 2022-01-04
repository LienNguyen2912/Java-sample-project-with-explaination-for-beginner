package ATM;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
	private String firstName;
	private String lastName;
	private byte[] pinHash;
	private String uuid;
	private ArrayList<Account> accounts;
	
	public User(String firstName, String lastName, String pin, Bank theBank) {
		// set user's name
		this.firstName = firstName;
		this.lastName = lastName;
		// store password as MD5 hash for security
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error by MessageDigest");
			e.printStackTrace();
			System.exit(1);
		}
		// get UUID assigned by Bank
		this.uuid = theBank.getNewUserUUID();
		
		// create empty account list
		this.accounts = new ArrayList<>();
		
		System.out.println("New user " + this.lastName + " " + this.firstName + " uuid " + this.uuid + " created");
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	public String getUUID() {
		return this.uuid;
	}

	public boolean validatePin(String pin) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
		}catch (NoSuchAlgorithmException e) {
			System.err.println("error by MessageDigest");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}
	
	public void printAccountSummary() {
		System.out.println(this.firstName + " accounts summary");
		for (int i = 0; i < this.accounts.size(); i++) {
			int accountNo = i + 1; 
			System.out.printf("Account " + accountNo +
							this.accounts.get(i).getSummaryLine());
			System.out.println();
		}
		System.out.println();
	}
	
	public String getFullName( ) {
		return this.firstName + " " + this.lastName;
	}
	
	public int getNumAccounts() {
		return this.accounts.size();
	}
	
	public void printAccountTransactionHistory(int accountIndex) {
		this.accounts.get(accountIndex).printTransactionHistory();
	}
	
	public double getAccountBalance(int accountIndex) {
		return this.accounts.get(accountIndex).getBalance();
	}
	
	public String getAccountUUID(int accountIndex) {
		return this.accounts.get(accountIndex).getUUID();
	}

	public void addAccTransaction(int accIdx, double amount, String transactionMessage) {
		this.accounts.get(accIdx).addTransaction(amount, transactionMessage);
	}
}
