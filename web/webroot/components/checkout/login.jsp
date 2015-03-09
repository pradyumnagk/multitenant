<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head></head>
<body>
<form:form method="post" commandName="CheckoutItemInfo" >
<table>
	<tr>
		<th colspan="2" align="left"><spring:message code="checkout.login"/></th>
	</tr>
	<tr><td colspan="2">&nbsp;</td></tr>
	<tr>
		<td><spring:message code="register.login"/>:</td>
		<td><form:input cssClass="defaultTextInput" path="login" cssErrorClass="invalidTextInput" /></td>
	</tr>
	<tr>
		<td><spring:message code="register.password"/>:</td>
		<td><form:password cssClass="defaultTextInput" path="password" cssErrorClass="invalidTextInput"/></td>
	</tr>
	<tr>
		<td colspan="2"><div class="registerButton"><spring:message code="register.user"/></div></td>
	</tr>
	<spring:hasBindErrors name="CheckoutItemInfo">
		<c:forEach var="error" items="${errors.globalErrors}">
			<tr>
				<td colspan="2" style="font-weight: bold; color: red"><spring:message code="${error.defaultMessage}"/></td>
			</tr>
		</c:forEach>
	</spring:hasBindErrors>
	<tr>
		<td colspan="2" align="right"><button class="defaultSubmit LoginSubmit" type="submit" name="_target1"><spring:message code="login.login"/></button></td>
	</tr>
</table>
</form:form>
</body>
</html>