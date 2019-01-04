package com.moneymoney.account.factory;

import com.moneymoney.account.CurrentAccount;
import com.moneymoney.account.SavingsAccount;

public final class AccountFactory {
	
	private static AccountFactory factory = new AccountFactory();

	private AccountFactory() {
		
	}
	
	public static AccountFactory getInstance() {
		return factory;
	}

	public SavingsAccount createNewSavingsAccount(String accountHolderName, double accountBalance, boolean salary) {
		return new SavingsAccount(accountHolderName, accountBalance, salary);
	}
	public CurrentAccount createNewSavingsAccount(String accountHolderName, double accountBalance, double creditLimit) {
		return new CurrentAccount(accountHolderName, accountBalance, creditLimit);
	}
}
