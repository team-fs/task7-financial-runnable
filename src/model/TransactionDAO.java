package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.PositionBean;
import databeans.TransactionBean;

public class TransactionDAO extends GenericDAO<TransactionBean>{
	
	public TransactionDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(TransactionBean.class, tableName, pool);
	}
	
	public TransactionBean[] getTransactions (int customerId) {
	    try {
	    	Transaction.begin();
	    	
	    	TransactionBean[] transactions;
	    	transactions=match(MatchArg.equals("customer_id", customerId));
	    	//Need to sort??
	    	Transaction.commit();
			return transactions;
	    } catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
	    }
	    return null;
	}
	public boolean checkEnoughCash (int customerId, long cash, long amount) {
		long sum = 0;
    	
		try {
	    	Transaction.begin();
	    	
	    	TransactionBean[] transactions;
	    	transactions=match(MatchArg.and(MatchArg.notEquals("execute_date",null), MatchArg.or(MatchArg.equals("transaction_type", 1), MatchArg.equals("transaction_type", 3))));
	    	//Need to sort??
	    	Transaction.commit();
	    	cash -= amount;
	    	if (cash < 0 ) return false;
	    	for(TransactionBean tran:transactions){
        		cash -= tran.getAmount();
        		if (cash < 0 ) return false;
        	}
	     } catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
	    }
	    return true;
	}

	public void createBuyTransaction(TransactionBean transaction) {
		// TODO Auto-generated method stub
		 try {
		    	Transaction.begin();
		    	transaction.setTransaction_type(0);;
		    	createAutoIncrement(transaction);
		    	Transaction.commit();
				
		    } catch (RollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (Transaction.isActive()) Transaction.rollback();
		    }
		    
	}

	public void createSellTransaction(TransactionBean transaction) {
		// TODO Auto-generated method stub
		 try {
		    	Transaction.begin();
		    	transaction.setTransaction_type(1);;
		    	createAutoIncrement(transaction);
		    	Transaction.commit();
				
		    } catch (RollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (Transaction.isActive()) Transaction.rollback();
		    }
	}
}
