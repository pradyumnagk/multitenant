<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:choose>
	<c:when test="${contentSlot.currentPosition == 'main'}">
		<div class="recentCampaigns clearfix">
			<c:if test="${banner.valid}">
				<div class="recentCampaign">
					<c:choose>
						<c:when test="${banner.validLink}"><a href="<c:if test="${banner.contentPage}"><%= request.getContextPath() %></c:if>${banner.imageLink}"<c:if test="${banner.external}"> target="_blank"</c:if>><img src="${banner.imageURL}" class="recentCampaignImage"/></a></c:when>
						<c:otherwise><img src="${banner.imageURL}" class="recentCampaignImage"/></c:otherwise>
					</c:choose>
					<c:if test="${banner.headlineAvailable}">
						<h3 class="recentCampaignHeadline">
							<c:choose>
								<c:when test="${banner.validLink}"><a href="<c:if test="${banner.contentPage}"><%= request.getContextPath() %></c:if>${banner.imageLink}"<c:if test="${banner.external}"> target="_blank"</c:if>>${banner.headline}</a></c:when>
								<c:otherwise>${banner.headline}</c:otherwise>
							</c:choose>
						</h3>
					</c:if>
					<c:if test="${banner.contentAvailable}">
						<div class="recentCampaignText">${banner.content}</div>
					</c:if>
				</div> <!--/recentCampaign-->
			</c:if>
		</div>
	</c:when>
	<c:otherwise>
		<c:if test="${banner.valid}">
			<div class="specialBox">
				<c:if test="${banner.headlineAvailable}"><div class="specialBoxesHeadline">${banner.headline}</div></c:if>
				<div class="specialBoxContent">
					<c:choose>
						<c:when test="${banner.validLink}"><a href="<c:if test="${banner.contentPage}"><%= request.getContextPath() %></c:if>${banner.imageLink}"<c:if test="${banner.external}"> target="_blank"</c:if>><img src="${banner.imageURL}" class="specialBoxImage"/></a></c:when>
						<c:otherwise><img src="${banner.imageURL}" class="specialBoxImage"/></c:otherwise>
					</c:choose>
					<c:if test="${banner.contentAvailable}">
						<h2 class="specialBoxHeadline">
							<c:choose>
								<c:when test="${banner.validLink}"><a href="<c:if test="${banner.contentPage}"><%= request.getContextPath() %></c:if>${banner.imageLink}"<c:if test="${banner.external}"> target="_blank"</c:if>>${banner.content}</a></c:when>
								<c:otherwise>${banner.content}</c:otherwise>
							</c:choose>
						</h2>
					</c:if>
				</div>
			</div> <!--/specialBox-->
		</c:if>
	</c:otherwise>
</c:choose>