package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;

	private final int accountType;
	private List<Transaction> transactions;

	public Account(int accountType) {
		this.accountType = accountType;
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			if (transactions == null)
				transactions = new ArrayList<Transaction>();
			transactions.add(new Transaction(amount));
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			if (transactions == null)
				transactions = new ArrayList<Transaction>();
			transactions.add(new Transaction(-amount));
		}
	}

	/**
	 * @return
	 */
	public double interestEarned() {
		double amount = sumTransactions();
		switch (accountType) {
		case SAVINGS:
			if (amount <= 1000)
				return calculateDailyInterest(amount, 0.001);
			else
				return (calculateDailyInterest(1000, 0.001) + calculateDailyInterest((amount - 1000), 0.002));
		case MAXI_SAVINGS:
			boolean isWithdrawal = checkIfWithdrawalWithin10Days(DateProvider.getInstance().oldDate(10));
			if (isWithdrawal == true)
				return calculateDailyInterest(amount, 0.001);
			else
				return calculateDailyInterest(amount, 0.05);
		case CHECKING:
			return calculateDailyInterest(amount, 0.001);
		default:
			return 0.0;
		}
	}

	private double calculateDailyInterest(double amount, double rate) {
		double interest = 1.0;
		for (int i = 1; i <= 365; i++) {
			interest = (interest * (1 + (rate / 365)));
		}
		double result = amount * (interest - 1);
		return Math.round(result*100.0)/100.0;
	}

	private boolean checkIfWithdrawalWithin10Days(Date tenthDayFromNow) {
		boolean isWithdrawal = false;
		if (transactions != null && !transactions.isEmpty()) {
			for (Transaction t : transactions) {
				Date transactionDate = t.getTransactionDate();
				if (t.getAmount() < 0 && transactionDate.after(tenthDayFromNow)) {
					isWithdrawal = true;
					break;
				}
			}
		}
		return isWithdrawal;
	}

	public double sumTransactions() {
		if (transactions != null && !transactions.isEmpty())
			return checkIfTransactionsExist();
		else
			return 0.0;
	}

	private double checkIfTransactionsExist() {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.getAmount();
		return amount;
	}

	public int getAccountType() {
		return accountType;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}
