<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="/cms2lib/cmstags/cmstags.tld" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de">
<head>
	<title>${site.name}</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="imagetoolbar" content="no"/>
	<link rel="icon" href="${contextPath}/stylesheets/images/favicon.ico" type="image/ico"/>
	<link rel="stylesheet" type="text/css" href="${contextPath}/stylesheets/screen.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/stylesheets/style00.css" />
	<!-- <script type="text/javascript" src="${contextPath}/js/jquery-1.3.2.min.js"></script> -->
	<script type="text/javascript" src="${contextPath}/js/jquery-ui-1.7.2.custom.min.js"></script>
	<!-- <script type="text/javascript" src="${contextPath}/js/jquery.slideshow.js"></script> -->
	<script type="text/javascript" src="${contextPath}/js/jquery.floatbox.1.0.7.min.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery.form.js"></script>
</head>
<body>
<div class="container">
	<div class="header">
		<div class="logo">
			<a href="${contextPath}"><img src="${contextPath}/stylesheets/images/${site.uid}_logo.gif" title="<spring:message code="homepage.name"/> | ${site.name}" alt="${site.name}" /></a>
		</div>
		<jsp:include page="/components/cart.jsp"/>
	</div> <!--/header-->
	
	<div class="subheader">
		<div class="mainnav">
			<!--  [toplinks content slot] -->
			<cms:slot uid="st_homepage_toplinks" position="toplinks" var="comp">
				<cms:component component="${comp}"/>
			</cms:slot>
		</div>
		<div class="metanav">
			<ul>
				<li><a href="#"><spring:message code="top.link.wishlist"/></a></li>
				<li>|</li>
				<li><a href="#"><spring:message code="top.link.help"/></a></li>
				<li>|</li>
				<li><a href="#"><spring:message code="top.link.account"/></a></li>
				<li>|</li>
				<li><jsp:include page="/view/LoginController"/></li>
			</ul>
		</div>
	</div> <!--/subheader--> 
	<div class="infonav">
		<div class="breadcrumb">
		</div> <!--/breadcrumb-->
		<div class="languages">
			<jsp:include page="/view/LanguagesController" />
		</div>
		<div class="currency">
			<jsp:include page="/view/CurrenciesController" />
		</div>
	</div> <!--/infonav-->
	<div class="center">
		<table cellpadding="0" cellspacing="0" width="100%" class="contentContainer">
			<tr>
				<td align="left" valign="top" class="contentContainer01" width="1px">
					<!--  [navigation content slot] -->
					<cms:slot uid="st_navigation" var="comp">
						<cms:component component="${comp}"/>
					</cms:slot>
				</td>
				<td align="left" valign="top" class="contentContainer02">
					<!--  [main content slot] -->
					<decorator:body />
				</td>
				<td align="left" valign="top" class="contentContainer03" width="1px">
					<!-- [banner content slot] -->
					<cms:slot uid="st_banner" var="comp">
						<cms:component component="${comp}"/>
					</cms:slot>
				</td>
			</tr>
		</table> <!--/contentContainer-->
	</div> <!--/center-->
	<div class="shopfooter">		
		<!-- [cms_image container] -->
		<cms:component uid="st_cataloglist" evaluateRestriction="true"/>
	</div> <!--/shopfooter-->
	<div class="footer">
		<!--  [cms_link_list component] | -->hybris Salesdemo &copy; since 1998
	</div> <!--/footer-->
</div> <!--/container-->
</body>
</html>

		

