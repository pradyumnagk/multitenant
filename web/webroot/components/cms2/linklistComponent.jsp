<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cms" uri="/cms2lib/cmstags/cmstags.tld" %>

<c:if test="${links != null && fn:length(links) > 0}">
<c:choose>
	<c:when test="${contentSlot.currentPosition == 'toplinks'}">
		<div class="navigationComponent clearfix">
			<ul>
				<c:forEach items="${links}" var="link" varStatus="status">						
					<li><cms:component uid="${link}" evaluateRestriction="true"/></li>
				</c:forEach>
			</ul>
		</div> <!--/navigation-->
	</c:when>
	<c:otherwise>
		<c:set var="columnWidth"><fmt:formatNumber value="${100/columns}" maxFractionDigits="0"/></c:set>
		<div class="linklist" style="width:100%;clear:both;">
			<table width="100%">
				<tr>
					<td width="${columnWidth}%">
						<ul>
							<c:set var="currentColumn" value="1" scope="page" />
		
							<c:forEach items="${links}" var="link" varStatus="status">
								<c:if test="${status.index % linksPerColumn == 0 && status.index != 0}">
									<c:set var="currentColumn" scope="page" value="${currentColumn + 1}" />
										</ul>
									</td>
									<td>
										<ul>
								</c:if>
								<li><cms:component uid="${link}" evaluateRestriction="true"/></li>
							</c:forEach>
						</ul>
					</td>
				</tr>
			</table>
		</div> <!--/bigTeaser-->
	</c:otherwise>
</c:choose>
</c:if>