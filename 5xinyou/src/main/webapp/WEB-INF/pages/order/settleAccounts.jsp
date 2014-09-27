<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>五星旅游</title>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<link rel="stylesheet" href="${_cssPath}/pages/order/order.css" />
</head>
</head>
<body>
	<%@ include file="/WEB-INF/pages/include/head.jsp"%>
<div id="settleAccountBody" class="pageWidth grayBorder">
<div id="settleTip" class="settleContent">购买仅需3步</div>
<div id="settltFlow" class="settleContent">
<div id="step1">1.提交订单</div>
<div id="step2">2.选择支付方式</div>
<div id="step3">3.购买成功</div>
</div>
<table id="proTable" class="proTable settleContent grayBorder"> 
	<thead>
	<tr height="50px" style="background-color: #e5e5e5">
		<td width="477px"  style="padding-left: 50px">产品名称</td>
		<td>数量</td>
		<td>价格</td>
		<td>总价</td>
		</tr>
	</thead>
	<tbody>
		<tr >
		<td>【常州 奥阳华美达】2天1晚双人自由行 中华恐龙园+3大景点门票，1加仑全部奉上!仅698元，享原价1544元常州奥阳华美达大酒店高级双床房1晚+精美自助早餐2份+常州恐龙园门票2张+首日赠送大林寺景区门票2张+白龙</td>
		<td><span class="countOpt optMinus">-</span> <input id="proCount" name="count"  class="count_input" value="1" /><span class="countOpt optAdd">+</span></td>
		<td>￥<span id="price" >199.00</span></td>
		<td>￥<span class="totalMoney">199.00</span></td>
		 </tr>
		<tr>
		<td></td>
		<td></td>
		<td colspan="2" style="text-align: right;"> 应付金额：￥<span class="readMoneyM totalMoney">199.00</span></td>
		</tr>
	</tbody>
</table>
 <table class="proTable settleContent grayBorder">
 <thead>
	<tr height="50">
		<td colspan="3" style="padding-left: 50px">您的手机 </td>
	 
		</tr>
	</thead>
 	<tbody>
 		<tr>
		<td colspan="3">首次购买请先绑定手机号码（绑定后我们将5星游消费验证码发送至该手机，凭此码去商家预定消费</td>
 		 </tr>
 		<tr>
 		<td width="289" style="text-align: right;">将消费验证码发送到手机：</td>
 		<td width="250"><input type="text" class="xyInput"></td>
 		<td></td>
 		 </tr>
 		 <tr>
		<td style="text-align: right;">请输入激活码：</td>
		<td ><input type="text" class="xyInput"></td>
		<td ><input type="button" class="grayBorder getValidate" value="获取验证码"></td>
 		</tr>
 		<tr>
 		<td></td>
 		<td><input type="checkbox"  name="" class="grayBorder useCoupon"/>&nbsp;使用优惠券</td>
 		<td></td>
 		</tr>
 	</tbody>
 </table>
 <div id="submitdiv" class="settleContent" style="text-align: center;">
 	<input type="button" class="settleSubmit" value="确认订单"/>
 </div>
 
</div>

<%@ include file="/WEB-INF/pages/include/foot.jsp"%>
</body>
<script type="text/javascript" src="${_jsPath }/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript">
function gCC(){
	var c = $("#proCount").val();
	if(!c){
		return 0;
	}
	if(c<0){
		return 0;
	}
	return c;
}

function calT(){
	var r = $("#proCount").val() * $("#price").html();
	$(".totalMoney").html(r.toFixed(2));
}

$(function(){
	$(".optMinus").on('click',function(){
		var c = gCC();
		if(c>0){
			c = c-1;
		 $("#proCount").val(c);
		 calT();
		}
	});
	$(".optAdd").on('click',function(){
		var c = gCC();
		++c ;
		$("#proCount").val(c);
		calT();
	});
	
	
});


</script>

</html>