package model;

import java.util.Arrays;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.PositionBean;
import databeans.TransactionBean;

public class TransactionDAO extends GenericDAO<TransactionBean> {

	public TransactionDAO(String tableName, ConnectionPool pool)
			throws DAOException {
		super(TransactionBean.class, tableName, pool);
	}

	public TransactionBean[] getTransactions(int customerId)
			throws RollbackException {
		TransactionBean[] transactions = match(MatchArg.equals("customer_id",
				customerId));
		return transactions;

	}
	public TransactionBean[] getPendingTransactions()
			throws RollbackException {
		TransactionBean[] transactions = match(MatchArg.equals("execute_date", null));
		Arrays.sort(transactions);
		return transactions;

	}

	public TransactionBean[] getPendingBuy(int customerId)
			throws RollbackException {
		TransactionBean[] transactions = match(
				MatchArg.equals("customer_id", customerId),
				MatchArg.equals("execute_date", null),
				MatchArg.or(MatchArg.equals("transaction_type", 0),
						MatchArg.equals("transaction_type", 2)));
		return transactions;

	}
	
	public TransactionBean[] getPendingCheck()
			throws RollbackException {
		TransactionBean[] transactions = match(
				MatchArg.equals("execute_date", null),
				MatchArg.equals("transaction_type", 2));
		return transactions;

	}

	public boolean checkEnoughCash(int customerId, long cash, long amount)
			throws RollbackException {

		TransactionBean[] transactions = match(MatchArg.and(
				MatchArg.equals("customer_id", customerId),
				MatchArg.equals("execute_date", null),
				MatchArg.or(MatchArg.equals("transaction_type", 0),
						MatchArg.equals("transaction_type", 2))));
		cash -= amount;
		if (cash < 0)
			return false;
		for (TransactionBean tran : transactions) {
			cash -= tran.getAmount();
			if (cash < 0)
				return false;
		}
		return true;
	}

	public boolean checkEnoughShare(int customerId, int fundId, long share, long amount)
			throws RollbackException {

		TransactionBean[] transactions = match(MatchArg.and(
				MatchArg.equals("customer_id", customerId),
				MatchArg.equals("fund_id", fundId),
				MatchArg.equals("execute_date", null),
				MatchArg.or(MatchArg.equals("transaction_type", 1))));
		share -= amount;
		if (share < 0)
			return false;
		for (TransactionBean tran : transactions) {
			share -= tran.getShares();
			if (share < 0)
				return false;
		}

		return true;
	}

	public void createBuyTransaction(TransactionBean transaction) {
		// TODO Auto-generated method stub
		try {
			Transaction.begin();
			transaction.setTransaction_type(0);
			createAutoIncrement(transaction);
			Transaction.commit();

		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}

	}

	public void createSellTransaction(TransactionBean transaction) {
		// TODO Auto-generated method stub
		try {
			Transaction.begin();
			transaction.setTransaction_type(1);
			createAutoIncrement(transaction);
			Transaction.commit();

		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
	
	public void createDepositTransaction(TransactionBean transaction) {
		// TODO Auto-generated method stub
		try {
			Transaction.begin();
			transaction.setTransaction_type(3);
			createAutoIncrement(transaction);
			Transaction.commit();

		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
}
