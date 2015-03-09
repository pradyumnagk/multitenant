<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head> 
       <script type="text/javascript"> 
       $(document).ready(function() { 
    	    var options = { 
    	        target:'#output', 
    	    }; 
    	    $('#myForm').ajaxForm(options); 
    	}); 
    </script> 
</head>
<body>
<div id="output">
	<div class="floatBoxContent">
		<form:form id="myForm" method="post" commandName="RegistrationInfo" >
		<table>
			<tr>
				<th colspan="3" align="left"><spring:message code="register.user"/></th>
			</tr>
			<tr><td colspan="3">&nbsp;</td></tr>
			<tr>
				<th colspan="3" align="left"><spring:message code="registration.login.data"/></th>
			</tr>
			<tr>
				<td><spring:message code="register.login"/>:</td>
				<td><form:input cssClass="defaultTextInput" path="login" cssErrorClass="invalidTextInput" cssStyle="width:300px;" /></td>
				<td>&nbsp;<form:errors path="login" cssStyle="color:red;"/></td>
			</tr>
			<tr>
				<td><spring:message code="register.password"/>:</td>
				<td><form:password showPassword="true" cssClass="defaultTextInput" path="password" cssErrorClass="invalidTextInput" cssStyle="width:300px;" /></td>
				<td>&nbsp;<form:errors path="password" cssStyle="color:red;"/></td>
			</tr>
			<tr>
				<td><spring:message code="register.password2"/>:</td>
				<td><form:password showPassword="true" cssClass="defaultTextInput" path="password2" cssErrorClass="invalidTextInput" cssStyle="width:300px;" /></td>
				<td>&nbsp;<form:errors path="password2" cssStyle="color:red;"/></td>
			</tr>
			<tr><td colspan="3">&nbsp;</td></tr>
			<tr>
				<th colspan="3" align="left"><spring:message code="registration.address.data"/></th>
			</tr>
			<tr>
				<td><spring:message code="register.firstname"/>:</td>
				<td><form:input cssClass="defaultTextInput" path="firstname" cssErrorClass="invalidTextInput" cssStyle="width:300px;" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><spring:message code="register.lastname"/>:</td>
				<td><form:input cssClass="defaultTextInput" path="lastname" cssErrorClass="invalidTextInput" cssStyle="width:300px;" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><spring:message code="register.mail"/>:</td>
				<td><form:input cssClass="defaultTextInput" path="mail" cssErrorClass="invalidTextInput" cssStyle="width:300px;" /></td>
				<td>&nbsp;<form:errors path="mail" cssStyle="color:red;"/></td>
			</tr>
			<tr>
				<td><spring:message code="register.company"/>:</td>
				<td><form:input cssClass="defaultTextInput" path="company" cssErrorClass="invalidTextInput" cssStyle="width:300px;" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><spring:message code="register.street_number"/>:</td>
				<td><form:input cssClass="defaultTextInput" path="street" cssErrorClass="invalidTextInput" cssStyle="width:250px;" /> <form:input cssClass="defaultTextInput" path="streetnumber" cssStyle="width:40px;" cssErrorClass="invalidTextInput" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><spring:message code="register.zip_city"/>:</td>
				<td><form:input cssClass="defaultTextInput" path="zip" cssErrorClass="invalidTextInput" cssStyle="width:70px;" /> <form:input cssClass="defaultTextInput" path="city" cssStyle="width:220px;" cssErrorClass="invalidTextInput" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><spring:message code="register.country"/>:</td>
				<td><form:select path="country" cssStyle="width:300px;" cssErrorClass="invalidTextInput">
						<option value=""><spring:message code="register.choose.country"/></option>
						<form:options items="${countries}" itemValue="isocode" itemLabel="name"/>
					</form:select>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3" style="color:red"><spring:hasBindErrors name="RegistrationInfo"><spring:message code="register.required.message"/></spring:hasBindErrors>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="right"><button class="defaultSubmit registerSubmit" name="RegistrationController" type="submit"><spring:message code="register.user"/></button></td>
				<td>&nbsp;</td>
			</tr>
			
		</table>
		
		</form:form>
	</div>
</div>
</body>