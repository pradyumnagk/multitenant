<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="cmsext" uri="/WEB-INF/tld/cmsexttags.tld" %>
<c:choose>
<c:when test="${productResult != null && fn:length(productResult.products) > 0}">
<div class="productListElement">
<c:if test="${! empty headline}"><div class="productlist"><b>${headline}</b></div></c:if>
<%-- PAGINATION --%>
<c:if test="${usePagination}">
	<div class="categoryNavigation">
		<div class="navigationHeadline">
			<c:if test="${productResult.numberOfResultsAvailable}">${productResult.numberOfResults}</c:if>
			<c:choose>
				<c:when test="${productResult.resultNameAvailable}"><spring:message code="productlist.results.in"/> "${productResult.resultName}"</c:when>
				<c:otherwise><spring:message code="productlist.results"/></c:otherwise>
			</c:choose>
		</div>
		<cmsext:pagination currentURL="${contextPath}${currentURL}" paginationId="${paginationId}" productResult="${productResult}"/>
		<div class="sortProducts">
			<spring:message code="productlist.sort"/>:
			<select class="sortSelect" style="width:150px;" onchange="self.location.href='<%= request.getContextPath() %>${currentURL}?pn_id=${paginationId}&pn_ok=' + this.options[this.selectedIndex].value;">
				<c:forEach items="${orderKeys}" var="key">
					<c:set var="up" value="${key}_up"/>
					<c:set var="down" value="${key}_down"/>
					<option value="${up}"<c:if test="${up == productResult.orderKey}"> selected</c:if>><spring:message code="productlist.sort.${key}" text="${key}"/> <spring:message code="productlist.sort.up"/></option>
					<option value="${down}"<c:if test="${down == productResult.orderKey}"> selected</c:if>><spring:message code="productlist.sort.${key}" text="${key}"/> <spring:message code="productlist.sort.down"/></option>
				</c:forEach>
			</select>
		</div>
		<div class="itemsPerPage">
			<span class="itemsPerPageInfo"><spring:message code="productlist.show"/>:</span>
			<c:forEach items="${paginationSizes}" var="key">
				<a class="itemsPerPageBox<c:if test="${key == productResult.pageSize || (productResult.pageSizeAll && key == 'all')}"> itemsPerPageBoxSelected</c:if>" href="<%= request.getContextPath() %>${currentURL}?pn_id=${paginationId}&pn_ps=${key}"><spring:message code="productlist.show.${key}" text="${key}"/></a>
			</c:forEach>
		</div>
	</div> <!--/categoryNavigation-->
</c:if>
<%-- END OF PAGINATION --%>
<%-- Product List View (in two differen layouts) --%>
<c:choose>
	<%-- THUMB View --%>
	<c:when test="${layoutId == 'thumbViewLayout'}">
		<div class="productBigGrid">
			<c:forEach items="${productResult.products}" var="product" varStatus="state">
				<div class="productBigGridItem">
					<div class="productBigGridItemImage">
						<a href="${contextPath}/view/product/${product.catalogId}/${product.pathAsString}">
							<img src="${product.pictureURL}"/>
						</a>
					</div>
					<div class="productBigGridItemBottom">
						<h2><a href="${contextPath}/view/product/${product.catalogId}/${product.pathAsString}" title="${product.name}"><cmsext:strip message="${product.name}"/></a></h2>
						<cmsext:error var="error" errorId="add2cart_${state.index}">
							<span style="color:red;"><spring:message code="${error.defaultMessage}" arguments="${error.arguments}"/></span>
						</cmsext:error><br/>
						<span class="itemNumber">#${product.code}</span>
						<c:if test="${product.reviewAvailable}">
							<div class="productBigGridReviews">
							<c:forEach begin="1" end="${product.averageRatingAsInt}">
								<img src="<%= request.getContextPath() %>/images/star.png" />
							</c:forEach>
								<br/>
								<spring:message code="product.review.avg.rating"/> <fmt:formatNumber value="${product.averageRating}" maxFractionDigits="1"/>  
								<br/>
								<a href="${contextPath}/view/product/${product.catalogId}/${product.pathAsString}#reviews"><spring:message code="product.reviews.show.all"/> »</a>
							</div>		
						</c:if>
					</div>
					<form:form action="${contextPath}/view/CartController" method="POST" >	
						<input type="hidden" name="id" value="add2cart_${state.index}"/>
						<input type="hidden" name="productCode" value="${product.code}"/>
						<input type="hidden" name="currentURL" value="${currentURL}"/>
						<input type="hidden" name="quickAdd" value="true"/>
						<div class="productBigGridAddToCart">
							<span class="bigPrice">
								<c:if test="${product.moreThanOnePrice}"><spring:message code="product.price.from"/></c:if> ${product.price.formattedPrice} 
							</span>
							<div class="productlistQuantity">
								<spring:message code="product.cart.quantity"/>: <input type="text" name="quantity" value="1" class="defaultTextInput"/>
							</div>			
							<button class="defaultSubmit AddToCartSubmit" type="submit" name="CartController"><spring:message code="product.cart.addtocart"/></button>
						</div>
					</form:form>
				</div> <!--/productBigGridItem-->
			</c:forEach>
		</div> <!--/productBigGrid-->
	</c:when>
	<%-- LIST View --%>
	<c:otherwise>
		<div class="productlist">
			<c:forEach items="${productResult.products}" var="product" varStatus="state">
				<div class="productlistItem">
					<div class="productlistItemImage">
						<a href="<%= request.getContextPath() %>/view/product/${product.catalogId}/${product.pathAsString}">
							<img src="${product.thumbnailURL}"/>
						</a>
					</div>
					<div class="productlistItemBottom">
						<h2>
							<a href="<%= request.getContextPath() %>/view/product/${product.catalogId}/${product.pathAsString}">${product.name}</a>
						</h2>
						<span class="itemNumber">#${product.code}</span>
						<c:if test="${product.reviewAvailable}">
							<div class="productlistReviews">
							<c:forEach begin="1" end="${product.averageRatingAsInt}">
								<img src="<%= request.getContextPath() %>/images/star.png" />
							</c:forEach>
								<br/>
								<spring:message code="product.review.avg.rating"/> <fmt:formatNumber value="${product.averageRating}" maxFractionDigits="1"/>  
								<br/>
								<a href="${contextPath}/view/product/${product.catalogId}/${product.pathAsString}#reviews"><spring:message code="product.reviews.show.all"/> »</a>
							</div>		
						</c:if>
					</div>
					<form:form action="${contextPath}/view/CartController" method="POST" >
						<input type="hidden" name="id" value="add2cart_${state.index}"/>
						<input type="hidden" name="productCode" value="${product.code}"/>	
						<input type="hidden" name="currentURL" value="${currentURL}"/>
						<input type="hidden" name="quickAdd" value="true"/>
						<div class="productlistAddToCart">
							<span class="bigPrice">
								<c:if test="${product.moreThanOnePrice}"><spring:message code="product.price.from"/></c:if> ${product.price.formattedPrice} 
							</span>
							<div class="productlistQuantity">
								<spring:message code="product.cart.quantity"/>:<input type="text" name="quantity" value="1" class="defaultTextInput"/>
							</div>
							<cmsext:error var="error" errorId="add2cart_${state.index}">
								<div style="color:red;"><spring:message code="${error.defaultMessage}" arguments="${error.arguments}"/></div>
							</cmsext:error><br/>
							<button class="defaultSubmit AddToCartSubmit" type="submit" name="CartController" ><spring:message code="product.cart.addtocart"/></button>
						</div>		
					</form:form>
				</div> <!--/productlistItem-->
			</c:forEach>
		</div> <!--/productlist-->
	</c:otherwise>
</c:choose>
</div>
</c:when>
<c:otherwise>
	<div class="content">
		<spring:message code="productlist.productNotFound"/>
	</div>
</c:otherwise>
</c:choose>