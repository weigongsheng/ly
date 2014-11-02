<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>五星旅游</title>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<link rel="stylesheet" type="text/css" href="${_cssPath}/pages/index.css"  />
<link rel="stylesheet" type="text/css" href="${_cssPath}/pages/product/pdetail.css"  />
</head>
</head>
<body>
	<%@ include file="/WEB-INF/pages/include/head.jsp"%>
	  <div class="xy_adv_list" style="margin-top:15px;">
    <div class="xy_adv_tip" >
     <div class="xy_adv_tip_title">
     	<a href="">${product.name}</a>
     </div>
     <div class="xy_adv_tip_content">
     ${product.recommendation}
     </div>
    </div>
    <div class="xy_product_info" >
    
   <div class="xy_left_pic">
   	<a href="${_ctxPath}/p/product-detail.htm?proId=${product.id}">
   	<img alt="" src="${_imagesPath}${product.imgUrl}" width="562" height="357">
   </a>
   </div>
   
    <div class="xy_right_advr_box">
   	<div class="xy_r_pro_headbox">
   	<div class="xy_r_pro_headleft">
   		<a href="${_ctxPath}/order/order-settleAccounts.htm?proId=${product.id}"  >
   		<span class="xy_r_pro_head1"><span class="rmb">￥</span>${product.price} </span>  
   		</a>
   	</div>
   	<div class="xy_r_pro_headright" style="margin-top: 22px;">
   		<a href="${_ctxPath}/order/order-settleAccounts.htm"  >
   		 <img alt="" src="${_imagesPath}/pages/buyBtn.png">
   		</a>
   	</div>
   	</div>
    <input name="leftTime"  type="hidden" value="$product.leftTime/1000}"></input>
		        <ul class="jialun_left_pro_left_ula">
		            <li class="jialun_left_pro_left_a_li">市场价</li>
		            <li class="jialun_left_pro_left_a_li">折扣</li>
		            <li class="jialun_left_pro_left_a_li">您节省</li>
		        </ul>
		        <ul class="jialun_left_pro_left_ulb">
		            <li class="jialun_left_pro_left_b_li" style=" text-decoration: line-through;">￥${product.orignalPrice}</li>
		            <li class="jialun_left_pro_left_b_li" style="color:#C03;">${product.discount}</li>
		            <li class="jialun_left_pro_left_b_li">￥${product.orignalPrice - product.price}</li>
		        </ul>
		        <ul id="contdown_html_0" class="jialun_left_pro_left_ulc">
		        <li class="jialun_left_pro_left_c_li"><img src="${_imagesPath}/pages/clock.png"  > </li>
		            <li id="contdown_Day_0" class="jialun_left_pro_left_c_li"></li>
		            <li class="jialun_left_pro_left_c2_li">天</li>
		            <li id="contdown_Houcre_0" class="jialun_left_pro_left_c_li"></li>
		            <li class="jialun_left_pro_left_c2_li">时</li>
		            <li id="contdown_Me_0" class="jialun_left_pro_left_c_li"></li>
		            <li class="jialun_left_pro_left_c2_li">分</li>
		            <li id="contdown_Min_0" class="jialun_left_pro_left_c_li"></li>
		            <li class="jialun_left_pro_left_c2_li">秒</li>
		        </ul>
		        <ul class="jialun_left_pro_left_uld">
		            <li class="jialun_left_pro_left_d_li"><span style=" font-size:22px; font-weight:bold; color:#f60; ">308</span>人已购买<br>数量有限，下手要快哦！</li>
		        </ul>
		    
	 </div>
    </div>
			     
    </div>

<div class="pageWidth detailHead" ><span>本单详情</span></div>
<div class="pageWidth redSplit"></div>
<div class="spcTip tableBody" >【特别提醒】<br>
为避免造成您的损失，请在购买后尽早确预约您的行程。若因房满无法预订，请变更您的行程安排或联系客户进行退款，敬请谅解！
</div>
<div class="tableHead"><span class="headLeft">${product.title}</span> <span class="headRight">挂牌价：￥${product.listingPrice}</span> </div>
<div class="tableBody"> 
${product.description }
</div>
<div class="tableHead"><span class="headLeft simpleText">【注意事项】</span> </div>
<div class="tableBody">
${product.attentions }
</div>
<div class="tableHead pageWidth"><span class="headLeft">本单详情</span> </div>
<div class="imgBody pageWidth"> 
${product.details}
</div>

<%@ include file="/WEB-INF/pages/include/foot.jsp"%>
<script type="text/javascript" src="${_jsPath }/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${_jsPath }/pages/product/product.js"></script>
</body>
</html>