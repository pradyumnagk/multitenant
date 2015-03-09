<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="languages" onclick="$(this).find('.smallLayer').toggle()">
	<a href="#"><spring:message code="language"/><img src="<%= request.getContextPath() %>/images/icon_globe.png" /></a>
	<div class="smallLayer" style="display:none">
		<ul>
			<c:forEach var="languageItem" items="${languages}">
				<li><a href="<%= request.getContextPath() %>${currentURL}?isoCode=${languageItem.isocode}">${languageItem.name}<img src="<%= request.getContextPath() %>/images/flags/${languageItem.isocode}.gif" /></a></li>
			</c:forEach>		
		</ul>
	</div>
</div>
