package com.moneymoney.account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.moneymoney.account.CurrentAccount;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

public class CurrentAccountDAOImpl implements CurrentAccountDAO {
	@Override
	public CurrentAccount createNewAccount(CurrentAccount account) throws SQLException, ClassNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)");
		preparedStatement.setInt(1, account.getBankAccount().getAccountNumber());
		preparedStatement.setString(2, account.getBankAccount().getAccountHolderName());
		preparedStatement.setDouble(3, account.getBankAccount().getAccountBalance());
		preparedStatement.setDouble(4, account.getCreditLimit());
		preparedStatement.setObject(5, null);
		preparedStatement.setString(6, "CA");
		preparedStatement.executeUpdate();
		preparedStatement.close();
		DBUtil.commit();
		return account;

	}

	@Override
	public List<CurrentAccount> getAllCurrentAccount() throws SQLException, ClassNotFoundException {
		List<CurrentAccount> currentAccounts = new ArrayList();
		Connection connection = DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNT");
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			double creditLimit = resultSet.getDouble("creditLimit");
			CurrentAccount currentAccount = new CurrentAccount(accountNumber,accountHolderName, accountBalance, creditLimit);
			currentAccounts.add(currentAccount);
			DBUtil.commit();

		}
		return currentAccounts;
	}

	@Override
	public void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		connection.setAutoCommit(false);
		PreparedStatement preparedStatement = connection.prepareStatement
				("UPDATE ACCOUNT SET account_bal=? where account_id=?");
		preparedStatement.setDouble(1, currentBalance);
		preparedStatement.setInt(2, accountNumber);
		preparedStatement.executeUpdate();
	}

	@Override
	public boolean updateAccount(CurrentAccount account) throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement
				("UPDATE ACCOUNT SET account_hn=?,salary=? where account_id=?");
		preparedStatement.setString(1, account.getBankAccount().getAccountHolderName());
		preparedStatement.setDouble(2,account.getCreditLimit() );
		preparedStatement.setInt(3, account.getBankAccount().getAccountNumber());
		int count  = preparedStatement.executeUpdate();
		boolean result = false;
		if(count!=0){
			result = true;
		}
		DBUtil.commit();
	
	return result;
	}

	@Override
	public CurrentAccount getAccountByID(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement
				("SELECT * FROM account where account_id=?");
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		CurrentAccount currentAccount = null;
		if(resultSet.next()) {
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			double creditLimit = resultSet.getDouble("creditLimit");
			currentAccount=new CurrentAccount(accountHolderName,accountBalance,creditLimit);
		return currentAccount;
		}
		throw new AccountNotFoundException("Account with account number "+accountNumber+" does not exist.");
	}

	@Override
	public CurrentAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM account WHERE account_id=?");
		preparedStatement.setInt(1, accountNumber);
		preparedStatement.execute();
		DBUtil.commit();
		return null;
	}

	@Override
	public double checkBalance(int accountNumber) throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement
				("SELECT * FROM account where account_id=?");
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				double accountBalance = resultSet.getDouble(1);
			return accountBalance;		
	}
			return 0;
}
}
