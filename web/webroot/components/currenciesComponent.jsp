<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="currency" onclick="$(this).find('.smallLayer').toggle()">
	<a href="#"><spring:message code="currency"/><img src="<%= request.getContextPath() %>/images/icon_currency.png" /></a>
	<div class="smallLayer" style="display:none">
		<ul>
			<c:forEach var="currencyItem" items="${currencies}">
			<li><a href="<%= request.getContextPath() %>${currentURL}?currencyIsoCode=${currencyItem.isocode}">${currencyItem.name}&nbsp;${currencyItem.symbol}</a></li>
			</c:forEach>
		</ul>
	</div>
</div>
