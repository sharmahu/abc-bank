package com.abc;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AccountTest {
	
	 private static final double DOUBLE_DELTA = 1e-15;
	 
	 Account checkingAccount;
	 Account savingsAccount;
	 Account maxi_saving_account;
	
	 
	 @Before
	    public void setUp() {
		 checkingAccount = new Account(Account.CHECKING);
		 savingsAccount = new Account(Account.SAVINGS);
		 maxi_saving_account = new Account(Account.MAXI_SAVINGS);
	    }

	@Rule
	  public final ExpectedException exception = ExpectedException.none();

	
	@Test
	public void testDepositWithNegativeValues() {
		exception.expect(IllegalArgumentException.class);
	    checkingAccount.deposit(-3000);		
	}
	
	@Test
	public void testDepositWithZeroValues() {
		exception.expect(IllegalArgumentException.class);
	    checkingAccount.deposit(0);		
	}
	
	@Test
	public void testDepositWithPositiveValues() {
	    checkingAccount.deposit(100.00);
	    assertEquals(100.00, checkingAccount.getTransactions().get(0).getAmount(),  DOUBLE_DELTA);
	}
	
	@Test
	public void testWithdrawalWithNegativeValues() {
		exception.expect(IllegalArgumentException.class);
	    checkingAccount.withdraw(-3000);		
	}
	
	@Test
	public void testWithdrawalWithZeroValues() {
		exception.expect(IllegalArgumentException.class);
	    checkingAccount.withdraw(0);		
	}
	
	@Test
	public void testWithdrawWithPositiveValues() {
	    checkingAccount.withdraw(100.00);
	    assertEquals(-100.00, checkingAccount.getTransactions().get(0).getAmount(),  DOUBLE_DELTA);
	}
	
	@Test
	public void testSumTransactionWithoutTransaction() {
		Account account1 = new Account(Account.CHECKING);
	    assertEquals(0.0, account1.sumTransactions(),  DOUBLE_DELTA);
	}
	
	@Test
	public void testSumTransactionWithTransaction() {	
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(new Transaction(1000));
		transactions.add(new Transaction(-100));
		transactions.add(new Transaction(200));
		checkingAccount.setTransactions(transactions);
	    assertEquals(1100, checkingAccount.sumTransactions(),  DOUBLE_DELTA);
	}

	@Test
	public void testInterestEarnedForCheckingAccount() {	
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions = new ArrayList<Transaction>();
		transactions.add(new Transaction(1000));
		transactions.add(new Transaction(-100));
		transactions.add(new Transaction(200));
		checkingAccount.setTransactions(transactions);
	    assertEquals(1.10, checkingAccount.interestEarned(),  DOUBLE_DELTA);
	}
	
	@Test
	public void testInterestEarnedForSavingAccountLessThan2000() {	
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions = new ArrayList<Transaction>();
		transactions.add(new Transaction(1000));
		transactions.add(new Transaction(-100));
		transactions.add(new Transaction(200));
		savingsAccount.setTransactions(transactions);
	    assertEquals(1.20, savingsAccount.interestEarned(),  DOUBLE_DELTA);
	}
	
	@Test
	public void testInterestEarnedForSavingAccountBalanceMoreThan2000() {	
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions = new ArrayList<Transaction>();
		transactions.add(new Transaction(6000));
		transactions.add(new Transaction(-100));
		transactions.add(new Transaction(200));
		savingsAccount.setTransactions(transactions);
	    assertEquals(11.21, savingsAccount.interestEarned(),  DOUBLE_DELTA);
	}
	
	@Test
	public void testInterestEarnedForMaxiSavingAccount() {	
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions = new ArrayList<Transaction>();
		transactions.add(new Transaction(1000));
		transactions.add(new Transaction(-100));
		transactions.add(new Transaction(200));
		maxi_saving_account.setTransactions(transactions);
	    assertEquals(1.10, maxi_saving_account.interestEarned(),  DOUBLE_DELTA);
	}
	
	@Test
	public void testInterestEarnedForJunkAccountType() {
		Account junkAccountType = new Account(4);
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions = new ArrayList<Transaction>();
		transactions.add(new Transaction(1000));
		transactions.add(new Transaction(-100));
		transactions.add(new Transaction(200));
		junkAccountType.setTransactions(transactions);
	    assertEquals(0.00, junkAccountType.interestEarned(),  DOUBLE_DELTA);
	}
}
