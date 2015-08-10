package com.abc;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;

public class CustomerTest {

	Account checkingAccount;
	Account savingsAccount;
	Customer henry;

	private static final double DOUBLE_DELTA = 1e-15;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() {
		checkingAccount = new Account(Account.CHECKING);
		savingsAccount = new Account(Account.SAVINGS);
		henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
		checkingAccount.deposit(100.0);
		savingsAccount.deposit(4000.0);
		savingsAccount.withdraw(200.0);
	}

	@Test // Test customer statement generation
	public void testGetStatement() {
		assertEquals("Statement for Henry\n" + "\n" + "Checking Account\n" + "  deposit $100.00\n" + "Total $100.00\n"
				+ "\n" + "Savings Account\n" + "  deposit $4,000.00\n" + "  withdrawal $200.00\n" + "Total $3,800.00\n"
				+ "\n" + "Total In All Accounts $3,900.00", henry.getStatement());
	}

	@Test
	public void testGetStatementWithNoAcct() {
		Customer oscar = new Customer("Oscar");
		assertEquals("", oscar.getStatement());
	}

	@Test
	public void testOneAccount() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTwoAcounts() {
		assertEquals(2, henry.getNumberOfAccounts());
	}

	@Test
	public void testThreeAccount() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
		oscar.openAccount(new Account(Account.CHECKING));
		oscar.openAccount(new Account(Account.MAXI_SAVINGS));
		assertEquals(3, oscar.getNumberOfAccounts());
	}

	@Test
	public void testZeroAccounts() {
		Customer oscar = new Customer("Oscar");
		assertEquals(0, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTotalInterestEarner() {
		assertEquals(6.71, henry.totalInterestEarned(), DOUBLE_DELTA);
	}

	@Test
	public void testTotalInterestEarnedWithNoAcct() {
		Customer oscar = new Customer("Oscar");
		assertEquals(0.0, oscar.totalInterestEarned(), DOUBLE_DELTA);
	}

	@Test
	public void testBalanceTransferWithNegativeValue() {
		exception.expect(IllegalArgumentException.class);
		henry.transfer(checkingAccount, savingsAccount, -10);

	}

	@Test
	public void testBalanceTransferWithZeroValue() {
		exception.expect(IllegalArgumentException.class);
		henry.transfer(checkingAccount, savingsAccount, 0);

	}

	@Test
	public void testBalanceTransferWithExcessValue() {
		exception.expect(IllegalArgumentException.class);
		henry.transfer(checkingAccount, savingsAccount, 10000000);
	}

	@Test
	public void testBalanceTransferWithValidValue() {
		henry.transfer(checkingAccount, savingsAccount, 10);
		assertEquals(-10.0,
				checkingAccount.getTransactions().get(checkingAccount.getTransactions().size() - 1).getAmount(),
				DOUBLE_DELTA);
		assertEquals(10.0,
				savingsAccount.getTransactions().get(savingsAccount.getTransactions().size() - 1).getAmount(),
				DOUBLE_DELTA);
	}

}
