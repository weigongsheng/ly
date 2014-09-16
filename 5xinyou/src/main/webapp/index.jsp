<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		<div><a href="" target="_blank" ><img class="lunbo" src="${_imagesPath}/product/p001.jpg"/></a></div>
		<div><a href="" target="_blank" ><img class="lunbo" src="${_imagesPath}/product/p002.jpg"/></a></div>
		<div><a href="" target="_blank" ><img class="lunbo" src="${_imagesPath}/product/p003.jpg"/></a></div>
	</div> 
   </div>
   <c:forEach items="${advList}" var="curAdv" varStatus="pIndex">
   <div class="xy_adv_list" style="margin-top:15px;">
    <div class="xy_adv_tip" >
     <div class="xy_adv_tip_title">
     	<a href="">东方明珠</a>
     </div>
     <div class="xy_adv_tip_content">
     【常州 奥阳华美达】2天1晚双人自由行 中华恐龙园+3大景点门票，1加仑全部奉上!仅698元，享原价1544元常州奥阳华美达大酒店高级双床房1晚+精美自助早餐2份+常州恐龙园门票2张+首日赠送大林寺景区门票2张+白龙
     </div>
    </div>
    <div class="xy_product_info" >
    
   <div class="xy_left_pic">
   	<img alt="" src="${_imagesPath}/product/pleft_00${pIndex.index}.jpg" width="562" height="357">
   </div>
   
    <div class="xy_right_advr_box">
   	<div class="xy_r_pro_headbox">
   	<div class="xy_r_pro_headleft">
   		<a href=""  >
   		<span class="xy_r_pro_head1"><span class="rmb">￥</span>1988 </span>  
   		</a>
   	</div>
   	<div class="xy_r_pro_headright">
   		<a href=""  >
   		<span class="xy_r_pro_head2">查看详情</span>
   		</a>
   	</div>
   	</div>
   
		        <ul class="jialun_left_pro_left_ula">
		            <li class="jialun_left_pro_left_a_li">市场价</li>
		            <li class="jialun_left_pro_left_a_li">折扣</li>
		            <li class="jialun_left_pro_left_a_li">您节省</li>
		        </ul>
		        <ul class="jialun_left_pro_left_ulb">
		            <li class="jialun_left_pro_left_b_li" style=" text-decoration: line-through;">￥1544</li>
		            <li class="jialun_left_pro_left_b_li" style="color:#C03;">4.5</li>
		            <li class="jialun_left_pro_left_b_li">￥846</li>
		        </ul>
		        <ul id="contdown_html_0" class="jialun_left_pro_left_ulc">
		        <li class="jialun_left_pro_left_c_li"><img src="${_imagesPath}/pages/clock.png"  > </li>
		            <li id="contdown_Day_0" class="jialun_left_pro_left_c_li">113</li>
		            <li class="jialun_left_pro_left_c2_li">天</li>
		            <li id="contdown_Houcre_0" class="jialun_left_pro_left_c_li">23</li>
		            <li class="jialun_left_pro_left_c2_li">时</li>
		            <li id="contdown_Me_0" class="jialun_left_pro_left_c_li">00</li>
		            <li class="jialun_left_pro_left_c2_li">分</li>
		            <li id="contdown_Min_0" class="jialun_left_pro_left_c_li">36</li>
		            <li class="jialun_left_pro_left_c2_li">秒</li>
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
</body>
</html>