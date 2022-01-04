package ATM;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

	private ArrayList<User> users;
	private ArrayList<Account> accounts;
	private String name;
	

	public Bank(String name) {
		this.name = name;
		this.users = new ArrayList<>();
		this.accounts = new ArrayList<>();
	}
	
	public String getNewUserUUID() {
		String uuid;
		Random ran = new Random();
		int len = 6;
		boolean nonUnique;
		
		// continue looping until get a unique ID 
		do {
			uuid = "";
			for (int i = 0; i < len; i++) {
				uuid += ((Integer)ran.nextInt(10)).toString();
			}
			nonUnique = false;
			for (User user: this.users) {
				if (uuid.compareTo(user.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
		}while(nonUnique);
		
		return uuid;
	}
	
	public String getNewAccountUUID() {
		String uuid;
		Random ran = new Random();
		int len = 10;
		boolean nonUnique;
		
		// continue looping until get a unique ID 
		do {
			uuid = "";
			for (int i = 0; i < len; i++) {
				uuid += ((Integer)ran.nextInt(10)).toString();
			}
			nonUnique = false;
			for (Account account: this.accounts) {
				if (uuid.compareTo(account.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
		}while(nonUnique);
		
		return uuid;
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	public User addUser(String firstName, String lastName, String pin) {
		// create a new User
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		// create a saving account for the new user
		Account newAccount = new Account ("Savings", newUser, this);
		newUser.addAccount(newAccount);
		
		//this.addAccount(newAccount);
		this.accounts.add(newAccount);
		return newUser;
	}
	
	public User userLogin(String uuid, String pin) {
		for (User u: this.users ) {
			if(u.getUUID().equals(uuid) && u.validatePin(pin)) {
				return u;
			}
		}
		return null;
	}
	
	public String getName() {
		return this.name;
	}
}
