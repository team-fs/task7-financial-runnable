package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import formbeans.ChangePwdForm;

public class ChangePwdAction extends Action {
	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory.getInstance(ChangePwdForm.class);
	private CustomerDAO customerDAO;
	
	public ChangePwdAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}
	
	public String getName() {
		return "cusChangePwd.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			CustomerBean customer = (CustomerBean) request.getSession().getAttribute("customer");
			ChangePwdForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			if(!form.isPresent()) {
				return "cusChangePwd.jsp";
			}
			
			errors.addAll(form.getValidationErrors());
			if(errors.size()>0) {
				return "cusChangePwd.jsp";
			}
			
			if (form.getNewPassword() == null || form.getNewPassword().length() == 0) {
				errors.add("New Password is required");
				return "cusChangePwd.jsp";
			}
			
			if (form.getConfirmPassword() == null || form.getConfirmPassword().length() == 0) {
				errors.add("Confirm Pwd is required");
				return "cusChangePwd.jsp";
			}
			
			
			if (!form.getNewPassword().equals(form.getConfirmPassword())) {
				errors.add("Passwords do not match");
				return "cusChangePwd.jsp";
			}
			
			customer.setPassword(form.getNewPassword());
			customerDAO.update(customer);
			
			request.setAttribute("message", "Password was changed successfully");
		//	return "cusChangePwd.jsp";
			return "success.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch(FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}

}
