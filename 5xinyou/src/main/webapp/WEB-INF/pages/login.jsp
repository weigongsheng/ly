<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登录页面</title>
<jsp:include page="/WEB-INF/pages/include/common.jsp"></jsp:include>
<!--page css-->
<link rel="stylesheet" href="${_cssPath}/pages/login.css" />

</head>
<body>
	<%@ include file="/WEB-INF/pages/include/head.jsp"%>
	<!--内容部分 start-->
	<div class="content">
		<!--登陆 start-->
		<div class="login cf">
			<div class="login_left fn_left">
				<h2>
					登录-五星游<span>立即开始品质之旅</span>
				</h2>
				<div class="login_bd">
					<form action="" method="post" id="login_form">
						<p class="f14">账号：</p>
						<div class="relative">
							<input type="text" name="username"
								class="input-text input-default" data-default="请输入账号"
								id="account" autocomplete="off" /><span id="accountTip"></span>
						</div>
						<p class="f14">密码：</p>
						<div class="relative">
							<label for="password" class="labelPsw" id="pwdStr">请输入密码</label><input
								type="password" name="password" autocomplete="off"
								class="input-text" id="password" data-default="请输入密码" /><span
								id="passwordTip"></span>
						</div>
						<div style="display: none;" id="codeDiv">
							<p class="f14">验证码</p>
							<div class="relative">
								<input type="text" class="input-text" data-default="请输入验证码"
									name="jCaptchaResponse" id="authCode" autocomplete="off" /><span
									class="yzmImg"><img style="width: 129px; height: 38px;"
									alt="验证码" id="imgLogin" src="${_ctxPath}/validationCode.htm"></span><a
									onclick="nextValidateCode('imgLogin');" class="yzmchange">看不清<br />换一张
								</a><span id="authCodetTip"></span>
							</div>
						</div>
						<div class="loginBtn_bd">
							<a href="javascript:" title="登录" id="loginBtn" class="loginBtn">登录</a><a
								href="${_ctxPath}/show-register${_requestPath}?index=login"
								title="注册" id="registerBtn" class="registerBtn">注册</a>
						</div>
						<input type="hidden" name="opertNum" value="${opertNum}"
							id="opertNum"> <br>
						<label><input id="remember" checked="checked"
							name="remeberName" type="checkbox" class="memoryUserName" />记住用户名</label><span
							class="long_line">|</span><a style='color: red'
							href="${_ctxPath}/user/user-findPassWord${_requestPath}"
							title="忘记密码了">忘记密码了？</a>
					</form>
				</div>
				<form id="true_form" action="${_ctxPath}/j_spring_security_check"
					style="display: none;" method="post">
					<input type="text" id="tname" name="j_username"> <input
						name="j_password" id="tpassword" type="password"> <input
						type="checkbox" id="remberMe" name="_spring_security_remember_me">
				</form>

			</div>
			<%-- <div class="login_right fn_right">
				<a href="javascript:"><img src="${_imagesPath}/register/banner.jpg" alt="广告" /></a>
			</div> --%>
		</div>
		<!--登陆 end-->
	</div>
	<!--内容部分 end-->
	<%@include file="/WEB-INF/pages/include/foot.jsp"%>
<script type="text/javascript" src="${_jsPath }/jquery/jquery-1.8.1.js"></script>
<script type="text/javascript"	src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js"></script>
<script type="text/javascript"	src="${_jsPath }/plugin/plugin.js"></script>
<script type="text/javascript" src="${_jsPath }/pages/login.js"></script>
</body>
</html>