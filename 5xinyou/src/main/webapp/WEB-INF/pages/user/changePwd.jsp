<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <div id="userInfo">
	<div class="left">原密码</div>
	<div class="right">${user.email}
		<input type="password" name="origPwd" value="" />
	</div>
	<div class="left">新密码</div>
	<div class="right ">
		<input type="password" name="newPwd" value="" />
	</div>
	<div class="left">确认密码</div>
	<div class="right ">
		<input type="password" name="confirmPwd" value="" />
	</div>
	 
 
	<div class="right">
	<input class="saveBtn" type="button" value="保存设置" />
	</div>
</div>