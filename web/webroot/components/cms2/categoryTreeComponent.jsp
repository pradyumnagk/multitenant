<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:choose>
	<c:when test="${isFacet}">
		<c:choose>
			<c:when test="${allFacetsEmpty}">
				<div class="specialBox facet">
					<spring:message code="categorytree.allFacetsEmpty"/>
				</div>
			</c:when>
			<c:otherwise>
				<c:forEach items="${facets}" var="facet">
					<c:if test="${facet.facetsAvailable}">
						<div class="specialBox facet">
							<div class="specialBoxesHeadline"><spring:message code="facet.${facet.name}" text="${facet.name}"/></div>
							<div class="specialBoxContent">
								<c:forEach items="${facet.facets}" var="facetvalue">
									<div class="facetElement<c:if test="${facetvalue.selected}"> categoryTreeElementActive</c:if>">
										<c:choose>
											<c:when test="${facetvalue.selected}"><a href="<%= request.getContextPath() %>/view/content/search?f=${facet.name}&v=${facetvalue.code}&m=rem" class="facetElementLink">${facetvalue.name}</a> (${facetvalue.count})</c:when>
											<c:otherwise><a href="<%= request.getContextPath() %>/view/content/search?f=${facet.name}&v=${facetvalue.code}&m=add" class="facetElementLink">${facetvalue.name}</a> (${facetvalue.count})</c:otherwise>
										</c:choose>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:if test="${backLinkName != null}">
			<div class="categoryTreeNavigation">
				<div class="categoryTreeBack">
					<a href="<%= request.getContextPath() %>${backLinkLink}">« <spring:message code="categorytree.backto"/> <span class="accentuation">${backLinkName}</span></a>
				</div>
			</div> <!--/categoryTreeNavigation-->
		</c:if>
		<div class="categoryTreeNavigation">
			<div class="categoryTreeHeadline">
				<c:choose>
					<c:when test="${treeHeadline != null}">${treeHeadline}</c:when>
					<c:otherwise><spring:message code="categorytree.categories"/></c:otherwise>
				</c:choose>
			</div>
			<div class="categoryTree">
				<div class="categoryTreeContent">
					<c:forEach items="${categories}" var="category">
						<div class="categoryTreeElement<c:if test="${selectedCategory.code == category.code}"> categoryTreeElementActive</c:if>">
							<a href="<%= request.getContextPath() %>/view/category/${category.catalogId}/${category.pathAsString}" class="categoryTreeElementLink">${category.name}</a>
						</div>
					</c:forEach>
				</div>
			</div> <!--/categoryTree-->
		</div> <!--/categoryTreeNavigation-->
	</c:otherwise>
</c:choose>