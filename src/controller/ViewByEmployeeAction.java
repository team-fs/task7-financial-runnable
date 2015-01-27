package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.PositionDAO;
import model.PriceDAO;
import model.TransactionDAO;
import model.Model;
import model.CustomerDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.EmployeeBean;
import databeans.PositionBean;
import databeans.TransactionBean;
import formbeans.CustomerForm;

public class ViewByEmployeeAction extends Action{
	private FormBeanFactory<CustomerForm> formBeanFactory = FormBeanFactory.getInstance( CustomerForm.class);

	private TransactionDAO transacDAO;
	private CustomerDAO cusDAO;
	private PositionDAO posDAO;
	private PriceDAO priceDAO;

	public ViewByEmployeeAction (Model model) {
		transacDAO = model.getTransactionDAO();
		cusDAO = model.getCustomerDAO();
        posDAO = model.getPositionDAO();
        priceDAO = model.getPriceDAO();
	}

	public String getName() {
		return "viewByEmployee.do";
	}
	
	public String perform(HttpServletRequest request) {
		EmployeeBean right = (EmployeeBean) request.getSession(false).getAttribute("employee");
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		if (right == null) {
			errors.add("You don't have the employee right to view the account!");
			return "error.jsp";
		} else {
			try {
				//List the all the customer in the dropdown menu.
				request.setAttribute("customerList", cusDAO.getCustomer());
				//Get the specific customer from jsp.
				
				CustomerForm form = formBeanFactory.create(request);
				errors.addAll(form.getValidationErrors());
				if (errors.size() != 0) {
					return "viewAccountByEmp.jsp";
				}
				String username = form.getUsername();
				System.out.println(username);
				if (username == null) {
					errors.add("A customer should be specified!");
					return "error.jsp";
				} else {
					CustomerBean user = cusDAO.read(username);
					int cusId = user.getCustomerId();
					TransactionBean[] transactions = transacDAO.getTransactions(cusId);
					
					request.setAttribute("user", user);
					request.setAttribute("transaction", transactions[transactions.length-1]); //Return the last trading day.

					//Return the fund information.
					PositionBean[] positions = posDAO.getPositions(cusId);
					List<String> priceList = new ArrayList<String>();
					for (int i = 0; i<positions.length; i++) {
						int fund_id = positions[i].getFund_id();
						long price = priceDAO.getLatestPrice(fund_id);
						double totalPrice = price * positions[i].getShares();
						
						DecimalFormat df = new DecimalFormat("#,###.00");
						priceList.add(df.format(totalPrice));
					}
					//System.out.println();
					request.setAttribute("positions", positions);
					request.setAttribute("priceList", priceList);
                    return "viewAccountByEmp.jsp";
				}
			} catch (RollbackException e) {
				errors.add(e.getMessage());
				return "error.jsp";
			} catch (FormBeanException e) {
				errors.add(e.getMessage());
				return "error.jsp";
			}
				
		}
	}
}

