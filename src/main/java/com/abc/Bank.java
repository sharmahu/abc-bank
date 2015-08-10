package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
	private List<Customer> customers;

	public void addCustomer(Customer customer) {
		if (customers == null)
			customers = new ArrayList<Customer>();
		customers.add(customer);
	}

	public String customerSummary() {
		if (customers != null && !customers.isEmpty()) {
			StringBuilder summary = new StringBuilder();
			summary.append("Customer Summary");
			for (Customer c : customers) {
				summary.append("\n - ");
				summary.append(c.getName());
				summary.append(" (");
				summary.append(format(c.getNumberOfAccounts(), "account"));
				summary.append(")");
			}
			return summary.toString();
		} else {
			return "";
		}
	}

	// Make sure correct plural of word is created based on the number passed
	// in:
	// If number passed in is 1 just return the word otherwise add an 's' at the
	// end
	private String format(int number, String word) {
		StringBuilder summary = new StringBuilder();
		summary.append(number);
		summary.append(" ");
		if (number == 1) {
			summary.append(word);
		} else {
			summary.append(word);
			summary.append("s");
		}
		return summary.toString();
	}

	public double totalInterestPaid() {
		if (customers != null && !customers.isEmpty())
			return checkIfCustomerExist();
		else
			return 0.0;
	}

	private double checkIfCustomerExist() {
		double total = 0.0;
		for (Customer c : customers)
			total += c.totalInterestEarned();
		return total;
	}

	public String getFirstCustomer() {
		if (customers != null && !customers.isEmpty()) {
			return customers.get(0).getName();
		} else {
			return "No Entry Found";
		}
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	
}
