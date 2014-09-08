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
		<div><a href="" target="_blank" ><img src="${_imagesPath}/product/p1.jpg"/></a></div>
		<div><a href="" target="_blank" ><img src="${_imagesPath}/product/p2.jpg"/></a></div>
		<div><a href="" target="_blank" ><img src="${_imagesPath}/product/p3.png"/></a></div>
	</div>
   </div>
   <c:forEach items="${advList}" var="curAdv">
   <div class="xy_adv_list" style="margin-top:15px;">
    <div class="xy_adv_tip" >
     <div class="xy_adv_tip_title">
     	<a href="">东方明珠</a>
     </div>
     <div class="xy_adv_tip_content">${curAdv }--
     【常州 奥阳华美达】2天1晚双人自由行 中华恐龙园+3大景点门票，1加仑全部奉上!仅698元，享原价1544元常州奥阳华美达大酒店高级双床房1晚+精美自助早餐2份+常州恐龙园门票2张+首日赠送大林寺景区门票2张+白龙
     </div>
    </div>
    <div class="xy_product_info" >
    
   <div class="xy_left_pic">
   	<img alt="" src="${_imagesPath}/product/p1_left.jpg" width="562" height="357">
   </div>
   
    <div class="xy_right_advr_box">
   
	 </div>
    </div>
			     
    </div>
    
   </c:forEach>

<script type="text/javascript" src="${_jsPath }/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${_jsPath }/plugin/jquery.kinMaxShow1.0.min.js"></script>
<script type="text/javascript" src="${_jsPath }/pages/index.js"></script>
<script type="text/javascript">
	$(function(){
		$("#kinMaxShow").kinMaxShow({height:445});
	});
</script>
	<%@ include file="/WEB-INF/pages/include/foot.jsp"%>
</body>
</html>