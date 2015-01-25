<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<h2>Sell Fund</h2>
<table class="table table-striped">
	<thead>
		<tr>
			<th>Product</th>
			<th>Price</th>
			<th>Share</th>
			<th>Operation</th>
		</tr>
	</thead>
	
		   
        <c:forEach var="item" items="${posList}">
		   <form action="confirmbuy.do" method="POST">
         
        <tr>
			<td>${ item.name }</td>
			<td>${ item.price }</td>
			<td>${ item.shares }</td>
			<td><input type="submit" class="btn btn-success" value="Sell" /></td>
		</tr>
		
		<input type="hidden" name="fundId" value="${ item.id }" />
        
		
		        			
        </form>
		
	</c:forEach>
	
</table>
<jsp:include page="template-bottom.jsp" /> 