package com.moneymoney.account.service;

import java.sql.SQLException;
import java.util.List;

import com.moneymoney.account.CurrentAccount;
import com.moneymoney.account.dao.CurrentAccountDAO;
import com.moneymoney.account.dao.CurrentAccountDAOImpl;
import com.moneymoney.account.factory.AccountFactory;
import com.moneymoney.exception.InsufficientFundsException;
import com.moneymoney.exception.InvalidInputException;

public class CurrentAccountServiceImpl implements CurrentAccountService {
	private AccountFactory factory;
	private CurrentAccountDAO currentAccountDAO;

	public CurrentAccountServiceImpl() {
		factory = AccountFactory.getInstance();
		currentAccountDAO = new CurrentAccountDAOImpl();
	}

	@Override
	public CurrentAccount createNewAccount(String accountHolderName, double accountBalance, double creditLimit)
			throws ClassNotFoundException, SQLException {
		CurrentAccount account = factory.createNewSavingsAccount(accountHolderName, accountBalance, creditLimit);
		currentAccountDAO.createNewAccount(account);
		return null;
	}

	@Override
	public CurrentAccount updateAccount(CurrentAccount account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CurrentAccount getAccountById(int accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CurrentAccount deleteAccount(int accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CurrentAccount> getAllCurrentAccount() throws ClassNotFoundException, SQLException {
		return currentAccountDAO.getAllCurrentAccount();
	}

	@Override
	public void fundTransfer(CurrentAccount sender, CurrentAccount receiver, double amount)
			throws ClassNotFoundException, SQLException, InsufficientFundsException, InvalidInputException {
		withdraw(sender, amount);
		deposit(receiver, amount);
	}

	@Override
	public void deposit(CurrentAccount account, double amount)
			throws ClassNotFoundException, SQLException, InvalidInputException {
		if (amount > 0) {
			double currentBalance = account.getBankAccount().getAccountBalance();
			currentBalance += amount;
			currentAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
		} else {
			throw new InvalidInputException("Invalid Input Amount!");
		}
	}

	@Override
	public void withdraw(CurrentAccount account, double amount) throws ClassNotFoundException, SQLException, InsufficientFundsException{
		double currentBalance=account.getBankAccount().getAccountBalance();

		if(amount>(account.getBankAccount().getAccountBalance()+account.getCreditLimit())){
			throw new InsufficientFundsException("Insufficient Funds");
		}else if(amount<0){
			throw new InvalidInputException("Invalid Amount");
		}else
			currentBalance-=amount;
	}
}
