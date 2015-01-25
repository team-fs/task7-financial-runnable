<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<h2>Create Fund</h2>
<jsp:include page="error-list.jsp" />
<table class="table table-striped">
	<thead>
		<tr>
			<th>Fund Name</th>
			<th>Ticket</th>
			<th>Operation</th>
			
		</tr>
	</thead>
	
		   
       
		   <form action="createFund.do" method="POST">
         
        <tr>
			
			<td><input type="text" name="fundName" /></td>
			<td><input type="text" name="ticket" /></td>
			<td><input type="submit" class="btn btn-success" value="createFund" /></td>
		</tr>
   			
        </form>
		

	
</table>
<jsp:include page="template-bottom.jsp" />