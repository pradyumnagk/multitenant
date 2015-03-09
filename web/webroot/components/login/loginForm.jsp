<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<c:if test="${not loggedIn}">
		<c:set var="loginStyle" value="none"/>
		<spring:hasBindErrors name="LoginInfo"><c:set var="loginStyle" value="block"/></spring:hasBindErrors>
		<a id="page_login_link" href="#" onclick="$('#loginForm').toggle()"><spring:message code="login.login"/></a>
		<div class="smallLayer" id="loginForm" style="display:<c:out value="${loginStyle}"/>;">
			<form:form method="post" commandName="LoginInfo" >
				<ul>
					<li><form:input cssClass="defaultTextInput" path="login" cssErrorClass="invalidTextInput" /></li>
					<li><form:password cssClass="defaultTextInput" path="password" cssErrorClass="invalidTextInput"/></li>
					<spring:hasBindErrors name="LoginInfo">
						<c:forEach var="error" items="${errors.globalErrors}">
    						<li style="text-align: center; font-weight: bold; color: black"><spring:message code="${error.defaultMessage}"/></li>
    					</c:forEach>
	   			</spring:hasBindErrors>
	   				<li><button class="defaultSubmit LoginSubmit" type="submit" name="LoginController"><spring:message code="login.login"/></button></li>
					<li><div class="registerButton"><spring:message code="register.user"/></div>
						<script type="text/javascript">
						  jQuery(document).ready(function(){
						    $(".registerButton").click(function () {
						    	$('#loginForm').toggle();
							    $.floatbox({
							    	ajax: {
							    		url: "<%= request.getContextPath() %>/view/RegistrationController",
							    		params: "",
										before: "<p><img src='<%= request.getContextPath() %>/stylesheets/images/loader.gif' /></p>",
										finish: function () {
							      }
						        },
						        fade: true
							    });
								});
						  });
						</script>
						
					</li>
				</ul>
			</form:form>
		</div>
	</c:if>			
