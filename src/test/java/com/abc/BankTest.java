package com.abc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;

	Bank bank;
	Customer john;

	@Before
	public void setUp() {
		bank = new Bank();
		john = new Customer("John");
		john.openAccount(new Account(Account.CHECKING));
	}

	@Test
	public void testCustomerSummaryWithOneAccount() {
		bank.addCustomer(john);
		assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
	}

	@Test
	public void testCustomerSummaryWithMultipleAccounts() {
		john.openAccount(new Account(Account.SAVINGS));
		bank.addCustomer(john);
		assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
	}

	@Test
	public void testCustomerSummaryWithNullCustomerList() {
		bank.setCustomers(null);
		assertEquals("", bank.customerSummary());
	}

	@Test
	public void testCustomerSummaryWithEmptyCustomerList() {
		List<Customer> customers = new ArrayList<Customer>();
		bank.setCustomers(customers);
		assertEquals("", bank.customerSummary());
	}

	@Test
	public void testTotalInterestPaidOnCheckingAccount() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.CHECKING);
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);
		checkingAccount.deposit(100.0);
		assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void testTotalInterestPaidWithCustomerListNull() {
		List<Customer> customers = new ArrayList<Customer>();
		bank.setCustomers(customers);
		assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void testTotalInterestPaidWithCustomerListEmpty() {
		bank.setCustomers(null);
		assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void testTotalInterestPaidOnSavingsAccount() {
		Account checkingAccount = new Account(Account.SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
		checkingAccount.deposit(1500.0);
		assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void testTotalInterestPaidOnMaxiSavingsAccount() {
		Account checkingAccount = new Account(Account.MAXI_SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
		checkingAccount.deposit(3000.0);
		assertEquals(153.8, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void testGetFirstCustomerWithValidData() {
		bank.addCustomer(new Customer("Bill").openAccount(new Account(Account.MAXI_SAVINGS)));
		bank.addCustomer(new Customer("John").openAccount(new Account(Account.MAXI_SAVINGS)));
		bank.addCustomer(new Customer("Alen").openAccount(new Account(Account.MAXI_SAVINGS)));
		assertEquals("Bill", bank.getFirstCustomer());
	}

	@Test
	public void testGetFirstCustomerWithEmptyCustomerList() {
		List<Customer> customers = new ArrayList<Customer>();
		bank.setCustomers(customers);
		assertEquals("No Entry Found", bank.getFirstCustomer());
	}

	@Test
	public void testGetFirstCustomerWithNullCustomerList() {
		Bank bank = new Bank();
		bank.setCustomers(null);
		assertEquals("No Entry Found", bank.getFirstCustomer());
	}
}
