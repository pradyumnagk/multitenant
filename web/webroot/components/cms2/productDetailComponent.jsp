<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="cmsext" uri="/WEB-INF/tld/cmsexttags.tld" %>
<%@ taglib prefix="cms" uri="/cms2lib/cmstags/cmstags.tld" %>
<script type="text/javascript">
$(function() { //this function is executed when page loads and creates jQueryUI tabs object used for displaying product's reviews
	$("#reviewTabs").tabs();
	$("#reviewTabs").tabs('select', 0); //select first tab (default)
	if(location.href.match("#reviews")){
		$("#reviewTabs").tabs('select', 1);//select show all reviews tab
		}

	$("#detailTabs").tabs();
	$("#detailTabs").tabs('select', 0); //select first tab (default)
	if(location.href.match("#details")){
		$("#detailTabs").tabs('select', 1);//select show all details tab
		}
});
function switchImages(source){
	tmp = document.getElementById("mainImage").src;
	document.getElementById("mainImage").src=source.src;
	source.src = tmp;
}
</script>
<c:if test="${not empty productDetail}" >
<div class="productDetailContent">
	<div class="productDetailImage">
		<a href="#">
			<img src="${productDetail.pictureURL}" id="mainImage"/>
		</a>
		<div class="productDetailThumbs">
			<a href="#">
				<img src="${productDetail.thumbnailURL}" class="productThumbnail" onmouseover="switchImages(this);" onmouseout="switchImages(this);"/>
			</a>
			<a href="#">
				<img src="${productDetail.thumbnailURL}" class="productThumbnail" onmouseover="switchImages(this);" onmouseout="switchImages(this);"/>
			</a>
		</div>
	</div>
	<div class="productDetailContentText">
		<h2>
			<a href="#">
				<span class="productDetailBrand">${productDetail.name}</span>
			</a>
		</h2>
		<span class="itemNumber">#${productDetail.code}</span>
		<p>${productDetail.description}</p>
		<div onclick="$('.productMoreDetails').show()" class="showMoreDetails">
			<a class="accentuation"><spring:message code="product.details.show.all"/> �</a>
		</div>
		<div class="productDetailReviews">
			<c:forEach begin="1" end="${productDetail.averageRatingAsInt}">
				<img src="<%= request.getContextPath() %>/images/star.png" />
			</c:forEach>
			<c:if test="${productDetail.reviewAvailable}">
				<a href="#reviews" onclick="$('#reviewTabs').tabs('select', 1);"><spring:message code="product.reviews"/> �</a>
			</c:if>
		</div>
	</div>		
	<div class="productDetailAddToCart">
		<form:form action="${contextPath}/view/CartController" method="POST" >	
		<input type="hidden" name="id" value="add2cart"/>
		<input type="hidden" name="productCode" value="${productDetail.code}"/>
		<input type="hidden" name="currentURL" value="${currentURL}"/>
		<input type="hidden" name="quickAdd" value="true"/>
		<span class="bigPrice">
			<c:if test="${productDetail.moreThanOnePrice}"><spring:message code="product.price.from"/></c:if> ${productDetail.price.formattedPrice} 
		</span>
		<c:if test="${productDetail.moreThanOnePrice}">
			<table class="general">
				<thead>
					<tr>
						<th><spring:message code="product.price.quantity"/></th>
						<th><spring:message code="product.price.price"/></th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${productDetail.prices}" var="price" begin="0">
					<tr><td class="quantity">${price.quantity }</td><td class="price">${price.formattedPrice }</td></tr>
				</c:forEach>
				</tbody>										
			</table>
		</c:if>
		<table cellspacing="0" cellpadding="0" border="0" class="productDetailSelects">
			<tbody>
			<cmsext:error var="error" errorId="add2cart">
				<tr>
					<td colspan="2"><span style="color:red;"><spring:message code="${error.defaultMessage}" arguments="${error.arguments}"/></span></td>
				</tr>
			</cmsext:error>
			<tr>
				<td class="productDetailSelectsLeft">
					<spring:message code="product.quantity"/>:
				</td>
				<td class="productDetailSelectsRight">
					<input type="text" name="quantity" value="1" class="defaultTextInput quantityInput"/>
				</td>
			</tr>
		</tbody></table>
		<button class="defaultSubmit AddToCartSubmit" type="submit" name="CartController"><spring:message code="product.cart.addtocart"/></button>
		<button type="button" class="defaultSubmit defaultGreySubmit AddToWishlistSubmit"><spring:message code="product.wishlist.add"/></button>
		<div class="socialActions">
			<ul>
				<li><a href="#"><spring:message code="product.sendToFriend"/></a></li>
				<li><a href="#"><spring:message code="product.print"/></a></li>
				<li><a href="#"><spring:message code="product.share"/></a></li>
				<li><a href="#"><spring:message code="product.downloadMedia"/></a></li>
			</ul>
		</div>
		</form:form>
	</div> <!--/productDetailAddToCart-->
</div> <!--/productDetailContent-->

<div id="detailTabs" class="productMoreDetails" style="display:none">
	<ul class="productcarouselHeader">
		<li><a href="#detail-description"><span class="blackText"><spring:message code="product.details"/></span></a></li>
		<c:if test="${productDetail.dataSheetAvailable}">
			<li><a href="#detail-datasheet"><span class="blackText"><spring:message code="product.datasheet"/></span></a></li>
		</c:if>
		<c:if test="${productDetail.faqAvailable}">
			<li><a href="#detail-faqs"><span class="blackText"><spring:message code="product.faqs"/></span></a></li>
		</c:if>
	</ul>
	<div class="productMoreDetailsContent" id="detail-description">
		<p>
			<span class="bold"><spring:message code="product.product.description"/></span>
			${productDetail.description}
		</p>
		<p>
			<!-- <span class="bold"><spring:message code="product.additional.features"/> </span> -->
		</p>
	</div> <!--/productMoreDetailsContent-->
	<c:if test="${productDetail.dataSheetAvailable}">
		<div id="detail-datasheet">
			<p>
				datasheet<!-- insert product.datasheet here when available -->
			</p>
		</div><!-- /detail-datasheet content -->
	</c:if>
	<c:if test="${productDetail.faqAvailable}">	
		<div id="detail-faqs">
			<p>
				FAQs<!-- insert product.faqs here when available -->
			</p>
		</div><!-- /detail-faqs content -->
	</c:if>
</div><!--/productmoreDetails-->
						
<c:if test="${productDetail.reviewAvailable}">
	<a name="reviews"></a>
	<div id="reviewTabs" class="productReviews">
		<ul class="productcarouselHeader">
			<li><a href="#fragment-1"><span class="blackText"><spring:message code="product.productreviews"/></span></a></li>
			<li><a href="#fragment-2" id="allReviews"><span class="blackText"><spring:message code="product.reviews.show.all"/></span></a></li>
		</ul>
		<div id="fragment-1" class="productgrid" >
			<c:forEach var="review" items="${productDetail.reviews}" end="3">
				<div class="productGridItem">
					<div class="productReviewsItem">
						<c:forEach begin="1" end="${review.rating}">
							<img src="<%= request.getContextPath() %>/images/star.png" />
						</c:forEach>
						<p class="productReviewsText">
							<span class="bold">${review.headline}</span>
							<span class="grey">${review.user.name} <fmt:formatDate pattern="dd/MM/yy" value="${review.date}"/></span>
							${fn:substring(review.comment, 0, 250)}
						</p>
						<a href="#"><spring:message code="product.readMore"/> �</a>
					</div>
				</div> <!--/productlistItem-->
			</c:forEach>
		</div> <!--/productGrid-->
	   <div id="fragment-2">
	   	<c:forEach var="review" items="${productDetail.reviews}" begin="0" end="${productDetail.numberOfReviews}" >									
				<div class="productGridItem">
					<div class="productReviewsItem">
						<c:forEach begin="1" end="${review.rating}">
							<img src="<%= request.getContextPath() %>/images/star.png" />
						</c:forEach>
						<p class="productReviewsText">
							<span class="bold">${review.headline}</span>
							<span class="grey">${review.user.name} <fmt:formatDate pattern="dd/MM/yy" value="${review.date}"/></span>
							${fn:substring(review.comment, 0, 250)}
						</p>
						<a href="#"><spring:message code="product.readMore"/> �</a>
					</div>
				</div> <!--/productlistItem-->
			</c:forEach>
	   </div>
	</div> <!--/productReviews-->
</c:if>
</c:if>