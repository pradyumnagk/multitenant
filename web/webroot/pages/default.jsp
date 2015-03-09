<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="/cms2lib/cmstags/cmstags.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cmsext" uri="/WEB-INF/tld/cmsexttags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@page import="de.hybris.platform.jalo.JaloSession"%>
<%@page import="de.hybris.platform.core.Registry"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de">
<head>
	<title>${site.name}</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="imagetoolbar" content="no"/>
	<link rel="icon" href="<%= request.getContextPath() %>/stylesheets/images/favicon.ico" type="image/ico"/>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/stylesheets/screen.css" rel="stylesheet"/>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/stylesheets/style00.css" rel="stylesheet"/>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/stylesheets/default.css" rel="stylesheet"/>
	<link type="text/css" href="<%= request.getContextPath() %>/stylesheets/smoothness/jquery-ui-1.7.2.custom.css" rel="stylesheet" />	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/stylesheets/productcarousel/skin.css" rel="stylesheet" />
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery.jcarousel.min.js"></script>		
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-ui-1.7.2.custom.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery.floatbox.1.0.7.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery.form.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery.cycle.all.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/swfobject.js"></script>
</head>
<cms:body>
<div id="default-template-main-container">
	<div id="default-template-header">
		<div id="default-template-header-icon"><img src="/multitenant/stylesheets/images/hwcatalog_logo_small.gif" /></div>
		<div id="default-template-header-msg"><spring:message code="default.tmpl.no.template.found" arguments="${templateName}"/></div>
		<br style="clear: both;"/>
	</div>
	<div>
		<c:forEach items="${contentSlots}" var="contentSlot">
			<div class="default-template-content-slot">
				<div class="default-template-content-slot-header">
					<spring:message code="default.tmpl.content.slot.info.header" arguments="${contentSlot.name}"/>				
				</div>
				<cms:slot contentSlot="${contentSlot.contentSlot}" var="comp">
					<div class="default-template-component">
						<cms:component component="${comp}"/>
					</div>
				</cms:slot>
			</div>
		</c:forEach>
	</div>
</div>
</cms:body>
</html>

