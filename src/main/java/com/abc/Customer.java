package com.abc;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.abs;

public class Customer {
	private String name;
	private List<Account> accounts;

	public Customer(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Customer openAccount(Account account) {
		if (accounts == null)
			accounts = new ArrayList<Account>();
		accounts.add(account);
		return this;
	}

	public int getNumberOfAccounts() {
		if (accounts != null && !accounts.isEmpty())
			return accounts.size();
		else
			return 0;
	}

	public void transfer(Account source, Account destination, double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			double balance = sumTransaction(source);
			if (balance >= amount) {
				source.withdraw(amount);
				destination.deposit(amount);
			} else {
				throw new IllegalArgumentException("Insufficient balance to make transfer");
			}
		}
	}

	public double totalInterestEarned() {
		double total = 0.0;
		if (isAccountValid()) {
			for (Account a : accounts)
				total += a.interestEarned();
		}
		return total;
	}

	public String getStatement() {
		StringBuilder statement = new StringBuilder();
		double total = 0.0;
		if (isAccountValid()) {
		statement.append("Statement for ");
		statement.append(name);
		statement.append("\n");
			for (Account a : accounts) {
				statement.append("\n");
				statement.append(statementForAccount(a));
				statement.append("\n");
				total += a.sumTransactions();
			}
			statement.append("\nTotal In All Accounts ");
			statement.append(toDollars(total));
		}
		return statement.toString();
	}

	private String statementForAccount(Account a) {
		StringBuilder statement = getPrettyAccountType(a);
		double total = getTransactionDetailsForStmt(a, statement);
		statement.append("Total ");
		statement.append(toDollars(total));
		return statement.toString();
	}

	private double sumTransaction(Account a) {
		double total = 0.0;
		if (a.getTransactions() != null && !a.getTransactions().isEmpty()) {
			for (Transaction t : a.getTransactions()) {
				total += t.getAmount();
			}
		}
		return total;
	}

	private double getTransactionDetailsForStmt(Account a, StringBuilder statement) {
		double total = 0.0;
		if (a.getTransactions() != null && !a.getTransactions().isEmpty()) {
			for (Transaction t : a.getTransactions()) {
				statement.append("  ");
				statement.append((t.getAmount() < 0 ? "withdrawal" : "deposit"));
				statement.append(" ");
				statement.append(toDollars(t.getAmount()));
				statement.append("\n");
				total += t.getAmount();
			}
		}
		return total;
	}

	private StringBuilder getPrettyAccountType(Account account) {
		StringBuilder statement = new StringBuilder();
		switch (account.getAccountType()) {
		case Account.CHECKING:
			statement.append("Checking Account\n");
			break;
		case Account.SAVINGS:
			statement.append("Savings Account\n");
			break;
		case Account.MAXI_SAVINGS:
			statement.append("Maxi Savings Account\n");
			break;
		}
		return statement;
	}

	private String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}

	private boolean isAccountValid() {
		if (accounts != null && !accounts.isEmpty())
			return true;
		else
			return false;
	}
	
}
