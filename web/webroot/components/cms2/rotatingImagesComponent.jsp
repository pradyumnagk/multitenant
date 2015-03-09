<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${banners != null && fn:length(banners) > 0}">
	<div class="bigTeaser">
		<script type="text/javascript">
			$(function() {
			    $('.simpleSlideShow').cycle({
					fx: '${effect}',
					timeout: ${timeout} 
				});
			});
		</script>
		<div class="simpleSlideShow slideShow"><c:forEach items="${banners}" var="banner">
			<c:if test="${banner.valid}">
				<c:choose>
					<c:when test="${banner.validLink}">
						<a href="<c:if test="${banner.contentPage}"><%= request.getContextPath() %></c:if>${banner.imageLink}"
							<c:if test="${banner.external}"> target="_blank"</c:if>><img src="${banner.imageURL}" /></a>
					</c:when>
					<c:otherwise>
						<img src="${banner.imageURL}" />
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:forEach>
		</div>
	</div>
	<!--/bigTeaser-->
</c:if>