<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>五星旅游</title>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<link rel="stylesheet" type="text/css" href="${_cssPath}/pages/index.css"  />
</head>
</head>
<body>
	<%@ include file="/WEB-INF/pages/include/head.jsp"%>

	<div class="kinMaxWrapper" style="margin:0 auto;width:960px;">
	<div id="kinMaxShow" class="xinoyou_lunbo" style="height:auto;">
		<c:forEach items="${slideAdvs}" var="sitem">
		<div><a href="${_ctxPath}${sitem.targetUrl}" target="_blank" ><img class="lunbo" src="${_imagesPath}${sitem.picUrl}"/></a></div>
		</c:forEach>
	</div> 
   </div>
   <c:forEach items="${advList}" var="adv" varStatus="pIndex">
   <div class="xy_adv_list" style="margin-top:15px;">
    <div class="xy_adv_tip" >
     <div class="xy_adv_tip_title">
     	<a href="${_ctxPath}/p/product-detail.htm?proId=${adv.product.id}">${adv.product.name}</a>
     </div>
     <div class="xy_adv_tip_content">
     ${adv.product.recommendation}
     </div>
    </div>
    <div class="xy_product_info" >
    
   <div class="xy_left_pic">
   <a href="${_ctxPath}/p/product-detail.htm?proId=${adv.product.id}">
   	<img alt="" src="${_imagesPath}${adv.product.imgUrl}" width="562" height="357">
   </a>
   </div>
   
    <div class="xy_right_advr_box">
   	<div class="xy_r_pro_headbox">
   	<div class="xy_r_pro_headleft">
   		<a href="${_ctxPath}/p/product-detail.htm?proId=${adv.product.id}"  >
   		<span class="xy_r_pro_head1"><span class="rmb">￥</span>${adv.product.price} </span>  
   		</a>
   	</div>
   	<div class="xy_r_pro_headright">
   		<a href="${_ctxPath}/p/product-detail.htm?proId=${adv.product.id}"  >
   		<span class="xy_r_pro_head2"><spring:message code="ui.link.viewdetail"/></span>
   		</a>
   	</div>
   	</div>
		        <input name="leftTime"  type="hidden" value="${adv.product.leftTime/1000}"></input>
   
		        <ul class="jialun_left_pro_left_ula">
		            <li class="jialun_left_pro_left_a_li"><spring:message code="ui.label.mPrice"/></li>
		            <li class="jialun_left_pro_left_a_li"><spring:message code="ui.label.discount"/></li>
		            <li class="jialun_left_pro_left_a_li"><spring:message code="ui.label.saved"/></li>
		        </ul>
		        <ul class="jialun_left_pro_left_ulb">
		            <li class="jialun_left_pro_left_b_li" style=" text-decoration: line-through;">￥${adv.product.orignalPrice}</li>
		            <li class="jialun_left_pro_left_b_li" style="color:#C03;">${adv.product.discount}</li>
		            <li class="jialun_left_pro_left_b_li">￥${adv.product.orignalPrice - adv.product.price}</li>
		        </ul>
		        <ul id="contdown_html_0" class="jialun_left_pro_left_ulc">
		        <li class="jialun_left_pro_left_c_li"><img src="${_imagesPath}/pages/clock.png"  > </li>
		            <li id="contdown_Day_0" class="jialun_left_pro_left_c_li ltDay"></li>
		            <li class="jialun_left_pro_left_c2_li"><spring:message code="ui.label.day"/></li>
		            <li id="contdown_Houcre_0" class="jialun_left_pro_left_c_li ltHour"></li>
		            <li class="jialun_left_pro_left_c2_li"><spring:message code="ui.label.hour"/></li>
		            <li id="contdown_Me_0" class="jialun_left_pro_left_c_li ltMi"></li>
		            <li class="jialun_left_pro_left_c2_li"><spring:message code="ui.label.short.minute"/></li>
		            <li id="contdown_Min_0" class="jialun_left_pro_left_c_li ltSec"></li>
		            <li class="jialun_left_pro_left_c2_li"><spring:message code="ui.label.short.second"/></li>
		        </ul>
		        <ul class="jialun_left_pro_left_uld">
		            <li class="jialun_left_pro_left_d_li"><span style=" font-size:22px; font-weight:bold; color:#f60; ">308</span>人已购买<br>数量有限，下手要快哦！</li>
		        </ul>
		    
	 </div>
    </div>
			     
    </div>
    
   </c:forEach>

<script type="text/javascript" src="${_jsPath }/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${_jsPath }/plugin/jquery.kinMaxShow1.0.min.js"></script>
<script type="text/javascript" src="${_jsPath }/pages/index.js"></script>
<script type="text/javascript">
	$(function(){
		$("#kinMaxShow").kinMaxShow({height:360});
	});
</script>
	<%@ include file="/WEB-INF/pages/include/foot.jsp"%>
	<script type="text/javascript" src="${_jsPath }/pages/product/product.js"></script>
</body>
</html>