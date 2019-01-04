package com.moneymoney.account;

public class CurrentAccount {
	private double creditLimit;
	private BankAccount bankAccount;

	public CurrentAccount(String accountHolderName, double accountBalance, double creditLimit) {
		bankAccount = new BankAccount(accountHolderName, accountBalance);
		this.creditLimit = creditLimit;
	}

	public CurrentAccount(String accountHolderName, double creditLimit) {
		bankAccount = new BankAccount(accountHolderName);
		this.creditLimit = creditLimit;
	}

	public CurrentAccount(int accountNumber, String accountHolderName, double accountBalance, double CreditLimit) {
		bankAccount = new BankAccount(accountNumber, accountHolderName, accountBalance);
		this.creditLimit = creditLimit;
	}

	public double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Override
	public String toString() {
		return "CurrentAccount [creditLimit=" + creditLimit + ", bankAccount=" + bankAccount + "]";
	}

}
