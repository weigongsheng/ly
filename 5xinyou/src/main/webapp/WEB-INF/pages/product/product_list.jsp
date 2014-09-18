<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>五星旅游</title>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<link rel="stylesheet" type="text/css" href="${_cssPath}/pages/index.css"  />
<link rel="stylesheet" type="text/css" href="${_cssPath}/pages/pl.css"  />
</head>
</head>
<body>
	<%@ include file="/WEB-INF/pages/include/head.jsp"%>
	<div id="plBody" >
	
	<div class="xy_class_box_cla_newprobox" id="searchdiv">
	<div class="xy_class_box_cla_box_newprobox">
		<div class="xy_newproduct_list_left_box">
	        <ul class="xy_class_box_cla_box_ul_new">
	        	<li class="xy_newproduct_list_left" style="width:60px;padding:3px 0;">国内游：</li>
	        </ul>
		</div>
        <div class="show_all">
        	<ul>
        	<c:forEach items="${allKind}" var="kind">
            	<li class="show_all_ul_li" ><a href="${_ctxPath}/p/product-list.htm?t=${kind.id}"
            	 class="${ t==kind.id ?'ptSelected':'pt' }" >${kind.kindLabel}(${kind.count})</a></li>
        	</c:forEach>
            </ul>
        </div>
	</div>
</div>
	
	
	<div id="xy_class_box_order" class="xy_class_box_cla_newprobox_sort">
	<div class="xy_class_box_cla_box_newprobox_sort">
        <div class="show_all_sort">
        	<ul class="clearfix sort_type">
            	<li class="active"><a href="">默认排序</a></li>
            	<li id="buynumorder"><a href="">销量</a><i class="down"></i></li>
            	<li id="sellpriceorder"><a href="">价格</a><i class="up"></i></li>
            	<li id="updatetimeorder"><a href="">折扣</a><i class="up"></i></li>
        	</ul>
        </div>
  </div>
</div>


	<div class="xy_group_con">
	
	<c:forEach items="${allProduct }" var="pro">
		<div class="xy_group_box">
    	<div class="xy_group_box_topcon">
	        <div class="xy_group_box_topcon_img">
	        	<a href="" target="_blank"><img src="${_imagesPath}/pages/product/${pro.imgUrl}" width="289" height="162" alt=""></a>
	        </div>
	        <div class="xy_group_box_text_blod"><a href="" target="_blank" title="">${pro.description }</a></div>
	        <div class="xy_group_box_buy_box">
	         	<ul>
	            	<li><a href="" target="_blank"><span class="xy_group_box_buy">&nbsp;￥${pro.price }</span></a></li>
	                <li class=" "> <div class="p_discount"><p>${pro.discount}折 </p></div></li>
	                <li class=" "><span class="p_buyer"> ${pro.buyers}人已购买  </span></li>
	            </ul>
	        </div>
	       
        </div>
    </div>
	</c:forEach>
    	 
        <!--自由行产品列表-->
</div>
	
	
	
	
	</div>
	
	
	

<%@ include file="/WEB-INF/pages/include/foot.jsp"%>
<script type="text/javascript" src="${_jsPath }/jquery/jquery-1.9.1.js"></script>
</body>
</html>