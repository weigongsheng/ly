<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>5星游-用户中心</title>
<jsp:include page="/WEB-INF/pages/include/common.jsp"></jsp:include>
<!--page css-->
<link rel="stylesheet" href="${_cssPath}/pages/userCenter.css" />
</head>
<body>
<div id="top"> </div>
	 <%@ include file="/WEB-INF/pages/include/head.jsp"%>
<div class="navigator">
<div class="navBody">会员中心 &gt; 账户 &gt; <span>基本信息</span></div>
</div>
<div id="centerContent">
<div class="menu" >
 <ul>
 	 <li class="top order selected">订单</li>
 	 <li class="sub ">全部</li>
 	 <li class="sub">已付款</li>
 	 <li class="sub">未付款</li>
 	 <li class="sub">已完成</li>
 	 <li class="top account">账户</li>
 	 <li class="sub">基本信息</li>
 	 <li class="sub">修改头像</li>
 	 <li class="sub">修改密码</li>
 	 <li class="top coupon">优惠券</li>
 	 <li class="sub">未使用</li>
 	 <li class="sub">已使用</li>
 </ul>
</div>
<div id="content">

</div>
</div>
<div class="footSplit">
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