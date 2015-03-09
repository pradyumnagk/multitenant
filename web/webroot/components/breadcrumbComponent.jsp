<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${breadcrumbItems}" var="breadcrumbItem" varStatus="status">
	<c:if test="${status.first == false}">
		&nbsp; /
	</c:if>
	<a href="<%= request.getContextPath() %>${breadcrumbItem.url}">${breadcrumbItem.displayName}</a>
</c:forEach>
