<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${catalogs != null && fn:length(catalogs) > 0}">
<c:choose>
	<c:when test="${contentSlot.currentPosition == 'toplinks'}">
		<ul>
			<c:forEach items="${catalogs}" var="catalog">
				<li><a href="${contextPath}/view/catalog/${catalog.id}">${catalog.name}</a></li>
			</c:forEach>
		</ul>
	</c:when>
	<c:when test="${contentSlot.currentPosition == 'navigation'}">
		<div class="categoryTreeNavigation">
			<div class="categoryTreeHeadline"><spring:message code="navigation.shops.headline"/></div>
			<div class="categoryTree">
				<div class="categoryTreeContent">
					<c:forEach items="${catalogs}" var="catalog">
						<div class="categoryTreeElement">
							<a href="${contextPath}/view/catalog/${catalog.id}" class="categoryTreeElementLink">${catalog.name}</a>
						</div>
					</c:forEach>
				</div>
			</div> <!--/categoryTree-->
		</div> <!--/categoryTreeNavigation-->
	</c:when>
	<c:otherwise>
	<center>
		<div class="bottomCatalogList">
			<a href="${contextPath}"><img src="<%= request.getContextPath() %>/stylesheets/images/${site.uid}_logo_small.gif" /></a>
			<c:forEach items="${catalogs}" var="catalog">
				<a href="${contextPath}/view/catalog/${catalog.id}"><img src="<%= request.getContextPath() %>/stylesheets/images/${catalog.id}_logo_small.gif" /></a>
			</c:forEach>
		</div>
	</center>
	</c:otherwise>
</c:choose>
</c:if>