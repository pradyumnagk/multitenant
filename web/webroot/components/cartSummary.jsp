<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${cartSummary.enabled}">
<div class="cart" onmousemove="this.className='cart cartMouseOver'" onmouseout="this.className='cart'" onclick="window.location.href='${contextPath}/view/content/cart?from=${contextPath}${currentURL}'">
	<div style="text-transform:uppercase;color:gray;font-weight:bold;padding-bottom:5px">
		${cartSummary.cartTitle}
	</div>
	<c:if test="${not empty cartSummary.cartImage}">
	<div class="cartTop">
		<img src="${cartSummary.cartImage.downloadURL}" />
	</div>
	</c:if>
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
	<div style="font-size:10px;color:gray;padding-bottom:5px">
		${cartSummary.suplementaryText}
	</div>
</div>
</c:if>