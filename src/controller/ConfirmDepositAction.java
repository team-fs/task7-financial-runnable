package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.FundBean;
import databeans.PositionBean;
import databeans.PositionOfUser;
import databeans.TransactionBean;
import model.CustomerDAO;
import model.Model;
import model.PositionDAO;
import model.PriceDAO;
import model.TransactionDAO;
import model.FundDAO;
import formbeans.SellForm;

public class ConfirmDepositAction extends Action {
	private FormBeanFactory<SellForm> formBeanFactory = FormBeanFactory.getInstance(SellForm.class);
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private PositionDAO positionDAO;
	private PriceDAO priceDAO;
	public ConfirmDepositAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
		positionDAO = model.getPositionDAO();
		priceDAO = model.getPriceDAO();
	}
	
	public String getName() {
		return "confirmDeposit.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			SellForm form  = formBeanFactory.create(request);
			request.setAttribute("form", form);
			TransactionBean transaction = new TransactionBean();
			CustomerBean customer = customerDAO.read(form.getIdAsInt());
			transaction.setCustomer_id(form.getIdAsInt());
			transaction.setAmount(form.getAmountAsLong());
			//加一个判断语句：amount<cash
			transactionDAO.createDepositTransaction(transaction);
			customerDAO.updateCash(transaction.getCustomer_id(), transaction.getAmount());
			return "depositCheck.do";
		} catch(FormBeanException e) {
			errors.add(e.getMessage());
			return "depositCheck.jsp";
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "depositCheck.jsp";
		
	}
	

}
