<jsp:include page="template-top.jsp" />
<h2>Create a new Customer</h2>
<jsp:include page="error-list.jsp" />

<form method="post" class="form-horizontal" role="form" action="cusRegister.do">
	<div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">Username</label>
		<div class="col-sm-10">
			<input type="username" class="form-control" id="inputEmail3" name="username" value="${username }">
		</div>
    </div>
    
    <div class="form-group">
		<label for="inputPassword3" class="col-sm-2 control-label">Password</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" id="inputPassword3" name="password" value="${password }">
			</div>
	</div>
	<div class="form-group">
		 <label for="inputPassword3" class="col-sm-2 control-label">Confirm Password</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" id="inputPassword3" name="confirm" value="${confirm }">
			</div>
	</div>
    
    <div class="form-group">
     	<label for="inputEmail3" class="col-sm-2 control-label">First Name</label>
		<div class="col-sm-10">
			<input type="firstName" class="form-control" id="inputEmail3" name="firstName" value="${firstName }">
		</div>
    </div>
    
    <div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">Last Name</label>
		<div class="col-sm-10">
			<input type="lastName" class="form-control" id="inputEmail3" name="lastName" value="${lastName }">
		</div>
    </div>
    
    <div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">Address Line 1</label>
		<div class="col-sm-10">
			<input type="address" class="form-control" id="inputEmail3" name="addrL1" value="${addrL1 }">
		</div>
    </div>
    
    <div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">Address Line 2</label>
		<div class="col-sm-10">
			<input type="address" class="form-control" id="inputEmail3" name="addrL2" value="${addrL2 }">
		</div>
    </div>
    
    <div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">City</label>
			<input type="city" class="form-control" id="inputEmail3" name="city" value="${city }">
		<label for="inputEmail3" class="col-sm-2 control-label">State</label>
			<input type="state" class="form-control" id="inputEmail3" name="state" value="${state }">
		<label for="inputEmail3" class="col-sm-2 control-label">Zip</label>
			<input type="zip" class="form-control" id="inputEmail3" name="zip" value="${zip }">
    </div>
    
	<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<div class="checkbox">
					<label><input type="checkbox"> Remember me</label>
				</div>
			</div>
	</div>
	<div class="col-sm-offset-2 col-sm-10">
		 <button type="button" class="btn btn-default" >Cancel</button> 
		 <input type="submit" class="btn btn-success" name="button" value="Login"> 
	</div>
				
</form>		

<jsp:include page="template-bottom.jsp" /> 