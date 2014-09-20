<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="userInfo">
	<div class="left">邮箱地址</div>
	<div class="right">${user.email}
		<a class="" href="">激活邮箱</a> <a class="" href="">激活 </a>
	</div>
	<div class="left">用户名</div>
	<div class="right ">
		<input type="text" name="userName" value="${user.username }" />
	</div>
	<div class="left">所在城市</div>
	<div class="right">
		<select   name="province">
			<option></option>
			<option>安徽</option>
			<option>江苏</option>
			<option>内蒙古</option>
		</select> 
		<select name="city">
			<option></option>
			<option>上海</option>
			<option>呼和浩特</option>
		</select>
	</div>
	<div class="left">出生年月日</div>
	<div class="right">
		<select name="yuear">
			<option></option>
			<option>1980</option>
		</select> 
		<select name="month">
		<option></option>
			<option>12</option>
		</select> 
		<select name="day">
		<option></option>
			<option>01</option>
		</select>
	</div>
	<div class="left">性别</div>
	<div class="right">
		<select name="sexuality">
			<option value="1">男</option>
			<option value="2">女</option>
		</select>
	</div>
	<div class="contSplit"></div>
	<div class="right">
	<input class="saveBtn" type="button" value="保存设置" />
	</div>
</div>
