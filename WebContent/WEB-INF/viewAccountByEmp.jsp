<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

		
		
		<h2> Account Information </h2>
            <table class="table table-striped ">
				<form action="createFund.do" method="POST">
				<tbody>
					<tr>
						<td>
							Name:
						</td>
						<td>
							${employee.firstname},   ${employee.lastname}
						
						</td>
						
					</tr>
					
				
				</tbody>
				</form>
			</table>


	

<jsp:include page="template-bottom.jsp" />
