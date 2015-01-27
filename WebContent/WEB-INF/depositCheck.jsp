<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<h2>Sell Fund</h2>

<table class="table table-striped">
	<thead>
		<tr>
			<th>Customer Username</th>
			<th>Check Amount</th>
			<th>Operation</th>
		</tr>
	</thead>
	
        <c:forEach var="item" items="${customerList}">
		   <form action="confirmDeposit.do" method="POST">
         
        <tr>
			<td>${ item.username }</td>
			<td><input type="text" name="amount"></td>
			<td><input type="submit" class="btn btn-success" value="Deposit" /></td>
		</tr>
		
		<input type="hidden" name="fundId" value="${ item.customerId }" />
        
		
		        			
        </form>
		
	</c:forEach>
	
</table>
<jsp:include page="template-bottom.jsp" /> 