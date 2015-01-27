package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomFundBean;
import databeans.CustomerBean;
import databeans.FundBean;
import databeans.PriceBean;
import databeans.RawPriceBean;
import databeans.TransactionBean;
import model.CustomerDAO;
import model.Model;
import model.PriceDAO;
import model.TransactionDAO;
import model.FundDAO;
import formbeans.BuyForm;
import formbeans.CreateFundForm;
import formbeans.CusRegisterForm;

public class TransitionAction extends Action {
	private FundDAO fundDAO;
	private PriceDAO priceDAO;

	public TransitionAction(Model model) {
		fundDAO = model.getFundDAO();
		priceDAO = model.getPriceDAO();
	}

	public String getName() {
		return "transition.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		List<String> success = new ArrayList<String>();
		request.setAttribute("success", success);

		try {
			HttpSession session = request.getSession(false);
			FundBean[] funds = fundDAO.getFundList();
			CustomFundBean[] cfbs = new CustomFundBean[funds.length];
			for (int i = 0; i < cfbs.length; i++) {
				CustomFundBean cfb = new CustomFundBean(funds[i]);
				cfb.setLastPrice(priceDAO.getLatestPrice(funds[i].getId()));
				cfbs[i] = cfb;
			}
			session.setAttribute("fundList", cfbs);
			if (request.getParameter("count") == null) {
				session.setAttribute("count", cfbs.length);
				return "transition.jsp";
			}
			RawPriceBean[] rawPrices = getPrices(request);
			setPriceAttributes(rawPrices, request);

			errors.addAll(getValidationErrors(rawPrices));
			if (errors.size() != 0) {
				return "transition.jsp";
			}
			DateFormat format = new SimpleDateFormat("mm/dd/yyyy", Locale.ENGLISH);
			Date d = format.parse((String)request.getParameter("date"));
			int count = Integer.parseInt((String) request.getParameter("count"));
			PriceBean price;
			for (int i=0; i<count; i++){
				price = new PriceBean(rawPrices[i].getFund_id(), d, Long.parseLong(rawPrices[i].getPrice()));
				priceDAO.create(price);
			}
			success.add("You have successfully created prices for transaction day " + (String)request.getParameter("date"));
			

			return "transition.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "transition.jsp";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return "transition.jsp";
		}
	}

	private List<String> getValidationErrors(RawPriceBean[] rawPrices) {
		// TODO Auto-generated method stub
		List<String> errors = new ArrayList<String>();

		for (RawPriceBean rpb : rawPrices) {
			if (rpb == null || rpb.getPrice() == null || rpb.getPrice().trim().length() == 0) {
				errors.add("Please enter the price for fund with id "
						+ rpb.getFund_id());
			}
		}
		return errors;
	}

	private void setPriceAttributes(RawPriceBean[] rawPrices,
			HttpServletRequest request) {
		int count = Integer.parseInt((String) request.getParameter("count"));
		for (int i = 0; i < count; i++) {
			request.setAttribute("price" + (i+1), rawPrices[i].getPrice());
		}

	}

	private RawPriceBean[] getPrices(HttpServletRequest request) {
		// TODO Auto-generated method stub
		int count = Integer.parseInt((String) request.getParameter("count"));
		RawPriceBean[] result = new RawPriceBean[count];
		for (int i = 0; i < count; i++) {
			result[i] = new RawPriceBean(i + 1,
					(String) request.getParameter("price" + (i + 1)));
			}
		return result;
	}

}
