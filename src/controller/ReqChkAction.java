package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.FundBean;
import databeans.PositionOfUser;
import databeans.TransactionBean;
import model.CustomerDAO;
import model.FundDAO;
import model.Model;
import model.TransactionDAO;
import formbeans.RequestCheckForm;

public class ReqChkAction extends Action {
	private FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory.getInstance(RequestCheckForm.class);
	private TransactionDAO transactionDAO;
	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	
	public ReqChkAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
	}
	
	public String getName() {
		return "requestCheck.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        List<String> success = new ArrayList<String>();
        request.setAttribute("success",success);
		
		try {
			
			CustomerBean customer = (CustomerBean) request.getSession(false).getAttribute("customer");
			HttpSession session = request.getSession();
	
			TransactionBean[] trans = transactionDAO.getPendingBuy(customer.getCustomerId());
	        TransactionBean tran = new TransactionBean();
	        PositionOfUser[] pous = new PositionOfUser[trans.length];
	        int id = 0;
			long pendingAmount = 0;
			FundBean fund = new FundBean();
			for (int i = 0; i<pous.length; i++){
				PositionOfUser pou = new PositionOfUser();
				tran = trans[i];
				id = tran.getFund_id();
				if ((fund=fundDAO.read(id))!=null){
					pou.setName(fund.getName());
					pou.setSymbol(fund.getSymbol());
				}
				else {
					pou.setName("Check Request");
					pou.setName("N/A");
				}
				pou.setAmount(tran.getAmount());
				pous[i] = pou;
				pendingAmount += tran.getAmount();
			}
			session.setAttribute("mFundList", pous);
			session.setAttribute("pendingAmount", pendingAmount);
			
	        if (errors.size() > 0) return "error.jsp";
			
	        request.getSession(true).setAttribute("customer", customer);
			
			return "requestCheck.jsp";
		} 
//		catch(RollbackException e) {
//			errors.add(e.getMessage());
//			return "requestCheck.jsp";
//		} 
//		catch(FormBeanException e) {
//			errors.add(e.getMessage());
//			return "requestCheck.jsp";
//		}
		catch(Exception e) {return "requestCheck.jsp";}
	}
	
    private String fixBadChars(String s) {
		if (s == null || s.length() == 0) return s;
		
		Pattern p = Pattern.compile("[<>\"&]");
        Matcher m = p.matcher(s);
        StringBuffer b = null;
        while (m.find()) {
            if (b == null) b = new StringBuffer();
            switch (s.charAt(m.start())) {
                case '<':  m.appendReplacement(b,"&lt;");
                           break;
                case '>':  m.appendReplacement(b,"&gt;");
                           break;
                case '&':  m.appendReplacement(b,"&amp;");
                		   break;
                case '"':  m.appendReplacement(b,"&quot;");
                           break;
                default:   m.appendReplacement(b,"&#"+((int)s.charAt(m.start()))+';');
            }
        }
        
        if (b == null) return s;
        m.appendTail(b);
        return b.toString();
    }

}
