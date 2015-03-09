<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cmsext" uri="/WEB-INF/tld/cmsexttags.tld" %>
<script type="text/javascript">
$(function() {
    $('ul.scrollable_${compUid}').jcarousel({
    	wrap: 'circular',
    	visible: 3,
    	scroll: ${scroll}
    });


    if ($.browser.safari) {
    	// Workaround for webkit based browsers and bug in jcarousel
    	window.alert = function() { return;};
    }        
});

</script>
	<c:choose>
		<c:when test="${hasCategories}">
			<script type="text/javascript">
				$(function() {
					$(".panes").tabs(); 
				});
			</script>
			<div style="clear: both;" class="panes productCarousel">
				<ul class="productcarouselHeader">
					<c:forEach items="${categories}" var="ctg">
						<li><a href="#fragment-${ctg.key}">${ctg.value}</a></li>
					</c:forEach>
				</ul>
				<div class="panes">
					<c:forEach items="${products}" var="prod">
						<div id="fragment-${prod.key}" class="pane_content">
							<ul class="scrollable_${compUid} jcarousel-skin-tango">
								<c:forEach items="${prod.value}" var="product">
									<li>
										<%@ include file="productScrollable.jsp" %>
									</li>
								</c:forEach>
							</ul>
						</div>
					</c:forEach>
				</div>				
			</div>
		</c:when>
		<c:when test="${hasProducts}">
			<div style="clear: both;" class="productCarousel">
				<ul class="scrollable_${compUid} jcarousel-skin-tango">
					<c:forEach items="${products}" var="product">
						<li>
							<%@ include file="productScrollable.jsp" %>
						</li>
					</c:forEach>
				</ul>
			</div>		
		</c:when>
	</c:choose>
	
