package com.moneymoney.account.dao;

import java.sql.SQLException;
import java.util.List;

import com.moneymoney.account.CurrentAccount;
import com.moneymoney.exception.AccountNotFoundException;

public interface CurrentAccountDAO {
	CurrentAccount createNewAccount(CurrentAccount account) throws SQLException, ClassNotFoundException;
	boolean updateAccount(CurrentAccount account) throws ClassNotFoundException, SQLException; 
	CurrentAccount getAccountByID(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	 CurrentAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException;
	 List<CurrentAccount> getAllCurrentAccount() throws SQLException, ClassNotFoundException;
	 void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException;
	 double checkBalance(int accountNumber) throws ClassNotFoundException, SQLException;

}
