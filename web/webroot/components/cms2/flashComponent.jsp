<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
$(function() {

	var params = ${params}
	
	swfobject.embedSWF(
			"${banner.imageURL}", 
			"${banner.flashElementId}", 
			"${banner.width}", 
			"${banner.height}", 
			"9.0.0", 
			"/flash/expressInstall.swf", 
			null, 
			params
			);
});
</script>
<div class="specialBoxContent flashElement">
	<c:choose>
		<c:when test="${banner.validLink}">
				<a style='display: block; position: absolute; width: ${banner.width}px; height: ${banner.height}px; background: white; filter:alpha(opacity=0); opacity:0.01;' 
					href="<c:if test="${banner.contentPage}"><%= request.getContextPath() %></c:if>${banner.imageLink}" <c:if test="${banner.external}"> target="_blank"</c:if>></a> 
				<div style="cursor: pointer;" class="specialBoxImage" id="${banner.flashElementId}"></div> 
		</c:when>
		<c:otherwise>
			<span class="specialBoxImage" id="${banner.flashElementId}"></span>
		</c:otherwise>
	</c:choose> 
</div>
