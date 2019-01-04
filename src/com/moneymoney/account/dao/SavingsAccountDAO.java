package com.moneymoney.account.dao;

import java.sql.SQLException;
import java.util.List;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.exception.AccountNotFoundException;

public interface SavingsAccountDAO {
	
	SavingsAccount createNewAccount(SavingsAccount account) throws ClassNotFoundException, SQLException;
	boolean updateAccount(SavingsAccount account) throws SQLException, ClassNotFoundException;
	SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	SavingsAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException;
	void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException;
	void commit() throws SQLException;
	double checkAccountBalance(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	SavingsAccount searchAccount(int accountNumber) throws ClassNotFoundException, SQLException;
	List<SavingsAccount> searchAccountByHolderName(String holderName) throws ClassNotFoundException, SQLException;
	List<SavingsAccount> sortByAccountHolderName() throws ClassNotFoundException, SQLException;
	List<SavingsAccount> sortByAccountHolderNameInDescendingOrder() throws ClassNotFoundException, SQLException;
	List<SavingsAccount> sortByAccountBalance() throws ClassNotFoundException, SQLException;
	List<SavingsAccount> sortByBalanceRange(int minimumBalance,
			int maximumBalance) throws ClassNotFoundException, SQLException;
	List<SavingsAccount> sortByBalanceRangeInDescendingOrder(
			int minimumBalanceValue, int maximumBalanceValue) throws ClassNotFoundException, SQLException;
	
	
	
}
