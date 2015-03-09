<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cmsext" uri="/WEB-INF/tld/cmsexttags.tld" %>
<div class="cartView">
	<form:form action="${contextPath}/view/CartController" method="POST" >
		<input type="hidden" name="currentURL" value="${currentURL}"/>
		<input type="hidden" name="quickAdd" value="false"/>
		<input type="hidden" name="backURL" value="${backURL}"/>
		<c:choose>
			<c:when test="${fn:length(cartData.entries) > 0}">
				<table cellspacing="0" cellpadding="0" class="cartTableTop">
					<tbody>
						<tr>
							<td class="inCartHeadline">
								<div class="textPageHeadline">
									<spring:message code="cart.headline"/>
								</div>
							</td>
							<td class="inCartTotal">
								<input type="button" class="defaultSubmit toCheckoutSubmit" value="<spring:message code="cart.checkout"/>" onClick="window.location.href='${contextPath}/view/checkout'"/>
							</td>
						</tr>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
	<c:if test="${fn:length(cartData.entries) > 0}">		
		<table cellspacing="0" cellpadding="0" class="cartTable">
			<thead>
				<tr>
					<th class="inCartPic">&#160;</th>
					<th class="inCartNr"><spring:message code="cart.nr"/></th>
					<th class="inCartItem"><spring:message code="cart.name"/></th>
					<th class="inCartPrice"><spring:message code="cart.price"/></th>
					<th class="inCartQuantity"><spring:message code="cart.quantity"/></th>
					<th class="inCartTotal"><spring:message code="cart.total"/></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${cartData.entries}" var="entry">
				<tr class="cartItemRow cartItemLastRow">
					<td class="inCartPic"><div class="cartItemPic"><a href="${contextPath}/view/product/${entry.product.catalogId}/${entry.product.pathAsString}"><img title="product" src="${entry.product.thumbnailURL}"/></a></div></td>
					<td class="inCartNr">#${entry.product.code}</td>
					<td class="inCartItem">
						<span class="cartProductName">
							<a href="${contextPath}/view/product/${entry.product.catalogId}/${entry.product.pathAsString}"><cmsext:strip message="${entry.product.name}" length="35"/></a>
						</span>
						<cmsext:error var="error" errorId="entry_${entry.entryNumber}">
							<br/><span style="color:red;"><spring:message code="${error.defaultMessage}" arguments="${error.arguments}"/></span>
						</cmsext:error><br/>
					</td>
					<td class="inCartPrice">
						${entry.formattedBasePrice}
					</td>
					<td class="inCartQuantity">
						<input name="quantities[${entry.entryNumber}]" type="text" value="${entry.quantity}" class="defaultTextInput quantityTextInput"/>
						<input name="plusQuantityEntry" value="${entry.entryNumber}"  type="submit" class="cartQuantityInput quantityInputPlus" />
						<input name="minusQuantityEntry" value="${entry.entryNumber}" type="submit" class="cartQuantityInput quantityInputMinus" />
					</td>
					<td class="inCartTotal">
						<span class="inCartTotalPrice">${entry.formattedTotalPrice}</span>
						<br/>
						<a href="${contextPath}/view/CartController?delete=${entry.entryNumber}&backURL=${backURL}&currentURL=${currentURL}" class="smallText cartDeleteSubmit"><spring:message code="cart.delete"/> x</a>
						<a href="#" class="smallText cartCompareSubmit"><spring:message code="product.wishlist.add"/> »</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td class="inCartPic"> </td>
					<td class="inCartNr"> </td>
					<td class="inCartItem"> </td>
					<td class="inCartPrice"> </td>
					<td class="inCartQuantity">
						<input type="submit" name="CartController" class="defaultSubmit defaultGreySubmit refreshSubmit" value="<spring:message code="cart.refresh"/>"/>
					</td>
					<td class="inCartTotal">
						<div class="totalPriceContainer">
							<spring:message code="cart.total"/>:
							<span class="totalPrice">${cartData.formattedTotalPrice}</span>
						</div>
					</td>
				</tr>
			</tfoot>
		</table>
	</c:if>
		<table cellspacing="0" cellpadding="0" class="cartTableBottom">
			<tbody>
				<tr>
					<td colspan="4" class="inCartPic">
						<c:if test="${fn:length(cartData.entries) == 0}">
							<div style="padding-bottom:5px;"><b><spring:message code="cart.empty"/></b></div>		
						</c:if>
						<input type="button" value="« <spring:message code="cart.continue.shopping"/>" class="defaultSubmit defaultGreySubmit backSubmit" onClick="window.location.href='${backURL}'"/>
					</td>
					<c:if test="${fn:length(cartData.entries) > 0}">		
						<td class="inCartQuantity">
								 <!-- Promotional Stugg -->
						</td>
						<td class="inCartTotal">
							<input type="button" class="defaultSubmit toCheckoutSubmit" value="<spring:message code="cart.checkout"/>" onClick="window.location.href='${contextPath}/view/checkout'"/>
						</td>
					</c:if>
				</tr>
			</tbody>
		</table>
	</form:form>
</div> <!--/cartView-->
