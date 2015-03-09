<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${loggedIn}">
	<li><a href="${contextPath}/view/LogoutController?proceedLogout=true" ><spring:message code="top.link.logout"/></a></li>
</c:if>			
	
