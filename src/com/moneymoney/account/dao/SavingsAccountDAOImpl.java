package com.moneymoney.account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

public class SavingsAccountDAOImpl implements SavingsAccountDAO {

	public SavingsAccount createNewAccount(SavingsAccount account)
			throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)");
		preparedStatement
				.setInt(1, account.getBankAccount().getAccountNumber());
		preparedStatement.setString(2, account.getBankAccount()
				.getAccountHolderName());
		preparedStatement.setDouble(3, account.getBankAccount()
				.getAccountBalance());
		preparedStatement.setBoolean(4, account.isSalary());
		preparedStatement.setObject(5, null);
		preparedStatement.setString(6, "SA");
		preparedStatement.executeUpdate();
		preparedStatement.close();
		DBUtil.commit();
		return account;
	}

	public List<SavingsAccount> getAllSavingsAccount()
			throws ClassNotFoundException, SQLException {
		List<SavingsAccount> savingsAccounts = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNT");
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			savingsAccounts.add(savingsAccount);
		}
		DBUtil.commit();
		return savingsAccounts;
	}

	@Override
	public void updateBalance(int accountNumber, double currentBalance)
			throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		connection.setAutoCommit(false);
		PreparedStatement preparedStatement = connection
				.prepareStatement("UPDATE ACCOUNT SET account_bal=? where account_id=?");
		preparedStatement.setDouble(1, currentBalance);
		preparedStatement.setInt(2, accountNumber);
		preparedStatement.executeUpdate();
		DBUtil.commit();
	}

	@Override
	public SavingsAccount getAccountById(int accountNumber)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT * FROM account where account_id=?");
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		SavingsAccount savingsAccount = null;
		if (resultSet.next()) {
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			return savingsAccount;
		}
		throw new AccountNotFoundException("Account with account number "
				+ accountNumber + " does not exist.");
	}

	public boolean updateAccount(SavingsAccount account) throws SQLException,
			ClassNotFoundException {
		boolean result = false;

		{
			Connection connection = DBUtil.getConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE account SET account_hn=?,salary=? WHERE account_id=?");
			preparedStatement.setString(1, account.getBankAccount()
					.getAccountHolderName());
			preparedStatement.setBoolean(2, account.isSalary());
			preparedStatement.setInt(3, account.getBankAccount()
					.getAccountNumber());
			int count = preparedStatement.executeUpdate();
			if (count != 0)
				result = true;
			DBUtil.commit();
		}
		return result;

	}

	@Override
	public SavingsAccount deleteAccount(int accountNumber)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException {
		SavingsAccountDAO savingsAccountDao = new SavingsAccountDAOImpl();
		if (savingsAccountDao.getAccountById(accountNumber).getBankAccount()
				.getAccountNumber() == accountNumber) {
			Connection connection = DBUtil.getConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE  FROM account WHERE account_id=?");
			preparedStatement.setInt(1, accountNumber);
			preparedStatement.execute();
			DBUtil.commit();
		}

		return null;
	}

	@Override
	public double checkAccountBalance(int accountNumber)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException {

		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT account_bal FROM account WHERE account_id=?");
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			double accountBalance = resultSet.getDouble(1);

			return accountBalance;
		}
		return 0;

	}

	@Override
	public SavingsAccount searchAccount(int accountNumber)
			throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT * FROM account WHERE account_id=?");
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		SavingsAccount savingsAccount = null;
		if (resultSet.next()) {
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			return savingsAccount;
		}
		DBUtil.commit();
		return null;
	}

	@Override
	public List<SavingsAccount> searchAccountByHolderName(String holderName)
			throws ClassNotFoundException, SQLException {
		List<SavingsAccount> savingsAccounts = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT * FROM account WHERE account_hn=?");
		preparedStatement.setString(1, holderName);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			savingsAccounts.add(savingsAccount);

		}
		DBUtil.commit();
		return savingsAccounts;
	}
	@Override
	public List<SavingsAccount> sortByAccountHolderName() throws ClassNotFoundException, SQLException {
		List<SavingsAccount> savingsAccount=new ArrayList<>();
		Connection connection=DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM account ORDER BY account_hn");
		while(resultSet.next())
		{
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccountList = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			savingsAccount.add(savingsAccountList);
		}
		DBUtil.commit();
		return savingsAccount;
	}
	@Override
	public List<SavingsAccount> sortByAccountHolderNameInDescendingOrder() throws ClassNotFoundException, SQLException {
		List<SavingsAccount> savingsAccountList =new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		Statement statement=connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM account ORDER BY account_hn DESC");
		while(resultSet.next())
		{
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			savingsAccountList.add(savingsAccount);
		}
		DBUtil.commit();
		return savingsAccountList;
	}
	@Override
	public List<SavingsAccount> sortByAccountBalance() throws ClassNotFoundException, SQLException {
		
		List<SavingsAccount> savingsAccountList =new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		Statement statement=connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM account ORDER BY account_bal");
		while(resultSet.next())
		{
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			savingsAccountList.add(savingsAccount);
		}return savingsAccountList;
	}
	@Override
	public List<SavingsAccount> sortByBalanceRange(int minimumBalance,
			int maximumBalance) throws ClassNotFoundException, SQLException {
		List<SavingsAccount> savingsAccountList =new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatementQuery = connection.prepareStatement("SELECT * FROM account WHERE account_bal BETWEEN ? and ? ORDER BY account_bal");
		preparedStatementQuery.setDouble(1, minimumBalance);
		preparedStatementQuery.setDouble(2, maximumBalance);
		ResultSet resultSet = preparedStatementQuery.executeQuery();
		while(resultSet.next())
		{
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			savingsAccountList.add(savingsAccount);
		}
		return savingsAccountList;
	}
	@Override
	public List<SavingsAccount> sortByBalanceRangeInDescendingOrder(
			int minimumBalanceValue, int maximumBalanceValue) throws ClassNotFoundException, SQLException {
		List<SavingsAccount> savingsAccountList =new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatementQuery = connection.prepareStatement("SELECT * FROM account WHERE account_bal BETWEEN ? and ? ORDER BY account_bal DESC");
		preparedStatementQuery.setDouble(1, minimumBalanceValue);
		preparedStatementQuery.setDouble(2, maximumBalanceValue);
		ResultSet resultSet = preparedStatementQuery.executeQuery();
		while(resultSet.next())
		{
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			savingsAccountList.add(savingsAccount);
		}
		DBUtil.commit();
		return savingsAccountList;
	}
	@Override
	public void commit() throws SQLException {
		DBUtil.commit();
	}

	

	

	

	

	

}
