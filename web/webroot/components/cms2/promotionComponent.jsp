<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="promotionComponent" style="width:100%;clear:both;">
<h1>${promotionName}</h1>
<c:if test="${url != null}">
	<p><img src="${url}" title="${name}" alt="${name}"/></p>
</c:if>
<c:if test="${description != null}">
	<p>${description}</p>
</c:if>
</div>