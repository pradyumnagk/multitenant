<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head></head>
<body>
<table>
	<tr>
		<td>
			<c:choose>
				<c:when test="${orderId != null}">
					<div class="content"><spring:message code="checkout.success" arguments="${orderId}"/></div>
				</c:when>
				<c:otherwise>
					<div class="content" style="color: red;"><spring:message code="checkout.failure" arguments="${error}"/></div>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td align="right"><input type="button" class="defaultSubmit toCheckoutSubmit" value="<spring:message code="checkout.continue"/>" onClick="window.location.href='${contextPath}/'"/></td>
	</tr>
</table>
</body>
</html>