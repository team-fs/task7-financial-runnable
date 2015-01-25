package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateFundForm extends FormBean {
	private String fundName;
	private String ticket;
	
	public String getFundName() { return fundName; }
	public String getTicket() { return ticket; }
	
	public void setFundName(String s) {fundName = s;}
	public void setTicket(String l) { ticket = l;}
	
	

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		
		if((fundName == null) || (fundName.length() ==0) ) {
			errors.add("Fund name is required");
		}
		
		if((ticket == null) || (ticket.length() == 0 )) {
			errors.add("Ticket is required");
		}
		
		return errors;
	}

}
