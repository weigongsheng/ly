<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>注册页面</title>
<jsp:include page="/WEB-INF/pages/include/common.jsp"></jsp:include>
<!--page css-->
<link rel="stylesheet" href="${_cssPath}/pages/login.css" />
</head>
<body>
	 <%@include file="/WEB-INF/pages/include/miniHead.jsp"  %>
<div class="login">
	 <div class="inptCont">
	  <div style="float: left;width: 200px;height: 300px"></div>
				<div class="login_bd">
					<div class="loginTip">请注册</div>
					<div class="tip"> 
					  <span class="welCome">已有5星游账号！</span><span><a href="${_ctxPath}/login.htm" class="regist" >登录</a></span>
					</div>
					<div class="inputBody " style="margin-top: 20px;">
					<form action="" id="register_form">
				 
					
						<div class="inputDiv logreg-text input-userName" style="position: relative;" >
							<input type="text" class="real-input" placeholder="请输入邮箱或手机号"
								name="username" autocomplete="off" id="mail" />
							<span id="mailTip" ></span>
						</div>
				 
						<div class="inputDiv logreg-text input-pwd">
							<input type="password" name="password" placeholder="请输入密码"
								class="real-input" id="password" autocomplete="off" /><span id="passwordTip"></span>
						</div>
						 
						<div class="inputDiv logreg-text input-pwd">
							 <input type="password" class="real-input" id="psw" placeholder="再次确认密码" autocomplete="off" /><span
								id="pswTip"></span>
						</div>
					 
						<div class="inputDiv">
						 <div class="vc_cont" >
							<input type="text" class="input-vc" name="jCaptchaResponse" placeholder="请输入验证码" id="authCode" autocomplete="off" /> 
							 
							<img alt="验证码" class="yzmImg"
									 onclick="nextValidateCode('imgLogin');" 
									style="width: 105px; height:42px;cursor: pointer; margin-top: 0;"
								 id="imgLogin"
								src="${_ctxPath}/validationCode${_requestPath}"> <span id="authCodetTip"></span>
							</div>
						</div>
						<div class="quick-link" >
						<input type="checkbox" checked="checked" id="cheboxTK" /> 我已阅读并接受</label>
						 <a style='color: #c5192f;' href="${_ctxPath}/user/serviceTerms${_requestPath}" target="_blank" title="5星游服务条款">《5星游服务条款》</a><span id="cheboxTKTip"></span>
						</div>
						<div class="loginBtn_bd" style="margin-top: 15px;">
							<a href="javascript:" title="立即注册" id="registerBtn" class="loginBtn">立即注册</a>
						</div>
					</form>
				 
				</div>
			</div>
			 
			<form id="true_form" action="${_ctxPath}/j_spring_security_check" style="display: none;" method="post">
				<input type="text" id="tname" name="j_username"> <input name="j_password" id="tpassword" type="text"> <input
					type="checkbox" id="remberMe" name="_spring_security_remember_me">
			</form>
		</div>
		<!--登陆 end-->
	</div>
	<div class="copyright">
		<spring:message code="copyright"></spring:message>
	</div>
	<script type="text/javascript" src="${_jsPath }/jquery/jquery-1.8.1.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/plugin.js"></script>
	<script type="text/javascript" src="${_jsPath }/pages/regest.js"></script>
</body>
</html>