<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cmsext" uri="/WEB-INF/tld/cmsexttags.tld"%>
<div>
	<a href="${contextPath}/view/product/${product.catalogId}/${product.pathAsString}" title="${product.name}"> 
		<img src="${product.thumbnailURL}" /><br />
	</a> 
	<cmsext:strip message="${product.name}" /><br />
	<c:if test="${product.moreThanOnePrice}">
		<spring:message code="product.price.from" />
	</c:if> 
	${product.price.formattedPrice}
</div>