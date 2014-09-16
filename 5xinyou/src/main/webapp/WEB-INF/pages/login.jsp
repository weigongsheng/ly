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
	 
<div class="loginBody" >
	
    <div class="xingyou_logo_nav">
        <div class="xingyou_logo"><a href="${_contextPath}"><img src="${_imagesPath}/logo.png"></a></div>
        <div class="xingyou_slogan">
        	<img alt="" src="${_imagesPath}/slogan.png">
        </div>
        <div class="xingyou_server">
           <ul>
            	<li><s:if test="#session['SPRING_SECURITY_CONTEXT'].authentication.principal  !=null">您好
            	&nbsp;${session['SPRING_SECURITY_CONTEXT'].authentication.principal.username }
                <li><a href="${_ctxPath}/j_spring_security_logout">退出</a></li>
            	</s:if><s:else>
            	<a href="${_ctxPath}/login.htm">登录</a>
                <li><a href="">注册</a></li>
            	</s:else></li>
                <li  ><a href=""><img src="${_imagesPath}/phone.png"  ></a> <a href="" style="font-size: 16px;">400-8222-1457</a></li>
            </ul>
        </div>
    </div>
 
	 
	<div class="span-div"></div> 
<div class="login">
	 <div class="inptCont">
	  <div style="float: left;width: 200px;height: 300px"></div>
				<div class="login_bd">
					<div class="loginTip">请登录</div>
					<div class="tip"> 
					  <span class="welCome">欢迎来到5星游！</span><span><a href="" class="regist" >注册</a></span>
					</div>
					<div class="inputBody ">
					<form action="" method="post" id="login_form">
						<div class="inputDiv logreg-text input-userName">
							<input type="text" name="username"
								class="real-input"  placeholder="请输入账号"
								id="account" autocomplete="off" />
								<!-- <span id="accountTip"></span> -->
						</div>
						<div class="inputDiv logreg-text input-pwd">
							 
							 <input
								type="password" name="password" autocomplete="off"
								class="real-input" id="password" placeholder="请输入密码" />
								<!-- <span id="passwordTip"></span> -->
							 
						</div>
						<div style="display: none; " id="codeDiv" class="inputDiv">
							 <div class="vc_cont" >
								<input type="text" class="input-vc" data-default="请输入验证码"
									name="jCaptchaResponse" id="authCode" autocomplete="off" />
									 
									<img class="yzmImg"  onclick="nextValidateCode('imgLogin');" 
									style="width: 105px; height:42px;cursor: pointer; margin-top: 0;"
									alt="验证码" id="imgLogin" src="${_ctxPath}/validationCode.htm">
									 
							 </div>
							 
						</div>
						<div class="quick-link" >
							 <input id="remember" checked="checked"
							name="remeberName" type="checkbox" class="memoryUserName" />记住用户名 
						</div>
						<div class="loginBtn_bd">
							<a href="javascript:" title="登录" id="loginBtn" class="loginBtn">登录</a> 
						</div>
						<input type="hidden" name="opertNum" value="${opertNum}"
							id="opertNum"> <br>
						 
					</form>
						
					</div>
								 <div class="quick-link" style="text-align: center;margin-bottom: 30px;">
						 <a style='color: black;'
							href="${_ctxPath}/user/user-findPassWord${_requestPath}"
							title="忘记密码了">忘记密码了</a>|<a style='color: #c5192f;'
							href="${_ctxPath}/user/user-findPassWord${_requestPath}"
							title="忘记密码了">免费注册</a>
						 </div>
				</div>
				<form id="true_form" action="${_ctxPath}/j_spring_security_check" style="display: none;" method="post">
					<input type="text" id="tname" name="j_username">
					<input name="j_password" id="tpassword" type="password">
					<input type="checkbox" id="remberMe" name="_spring_security_remember_me">
				</form>


			</div>
			<div style="clear: both"></div>
		</div>
		<!--登陆 end-->
			 </div>
	 <div class="copyright"  >
	  <spring:message code="copyright"></spring:message>
	 </div>
<script type="text/javascript" src="${_jsPath }/jquery/jquery-1.8.1.js"></script>
<script type="text/javascript"	src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js"></script>
<script type="text/javascript"	src="${_jsPath }/plugin/plugin.js"></script>
<script type="text/javascript" src="${_jsPath }/pages/login.js"></script>
</body>
</html>