<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="cart" onmousemove="this.className='cart cartMouseOver'" onmouseout="this.className='cart'" onclick="window.location.href='${contextPath}/view/content/cart?from=${contextPath}${currentURL}'">
	<div class="cartTop">
		<img src="${contextPath}/images/cart.png" />
	</div>
	<div class="cartBottom">
		<c:out value="${cartData.totalQuantity}"/>
		<c:choose>
			<c:when test="${cartData.totalQuantity == 1}">
				<spring:message code="cart.product" />
			</c:when>
			<c:otherwise>
				<spring:message code="cart.products" />
			</c:otherwise>
		</c:choose>
		<br />${cartData.formattedTotalPrice}&nbsp;
	</div>
</div>