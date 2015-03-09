<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head></head>
<body>
<form:form method="post" commandName="CheckoutItemInfo" >
<table>
	<tr>
		<th colspan="2" align="left"><spring:message code="checkout.delivery.address"/></th>
	</tr>
	<tr><td colspan="2">&nbsp;</td></tr>
	<tr>
		<td><spring:message code="checkout.firstname"/>:</td>
		<td><form:input cssClass="defaultTextInput" path="firstname" cssErrorClass="invalidTextInput" cssStyle="width:250px;"/></td>
	</tr>
	<tr>
		<td><spring:message code="checkout.lastname"/>:</td>
		<td><form:input cssClass="defaultTextInput" path="lastname" cssErrorClass="invalidTextInput" cssStyle="width:250px;"/></td>
	</tr>
	<tr>
		<td><spring:message code="checkout.address.1"/>:</td>
		<td><form:input cssClass="defaultTextInput" path="streetname" cssErrorClass="invalidTextInput" cssStyle="width:183px;"/> <form:input cssClass="defaultTextInput" path="streetnumber" cssErrorClass="invalidTextInput" cssStyle="width:50px;"/></td>
	</tr>
	<tr>
		<td><spring:message code="checkout.address.2"/>:</td>
		<td><form:input cssClass="defaultTextInput" path="postalcode" cssErrorClass="invalidTextInput" cssStyle="width:50px;" /> <form:input cssClass="defaultTextInput" path="city" cssErrorClass="invalidTextInput" cssStyle="width:183px;"/></td>
	</tr>
	<tr><td colspan="2">&nbsp;</td></tr>
	<tr>
		<th colspan="2" align="left"><spring:message code="checkout.payment"/></th>
	</tr>
	<tr>
		<td><spring:message code="checkout.accountnumber"/>:</td>
		<td><form:input cssClass="defaultTextInput" path="accountNumber" cssErrorClass="invalidTextInput" cssStyle="width:250px;"/></td>
	</tr>
	<tr>
		<td><spring:message code="checkout.bank"/>:</td>
		<td><form:input cssClass="defaultTextInput" path="bank" cssErrorClass="invalidTextInput" cssStyle="width:250px;"/></td>
	</tr>
	<tr>
		<td><spring:message code="checkout.bank.id.number"/>:</td>
		<td><form:input cssClass="defaultTextInput" path="bankIDNumber" cssErrorClass="invalidTextInput" cssStyle="width:250px;"/></td>
	</tr>
	<tr>
		<td><spring:message code="checkout.bank.owner"/>:</td>
		<td><form:input cssClass="defaultTextInput" path="baOwner" cssErrorClass="invalidTextInput" cssStyle="width:250px;"/></td>
	</tr>
	<tr>
		<td colspan="2" align="right"><button class="defaultSubmit LoginSubmit" type="submit" name="_finish"><spring:message code="checkout.docheckout"/></button></td>
	</tr>
</table>
</form:form>
</body>
</html>