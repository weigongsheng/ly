<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/taglibs.jsp"%>
<div class="xingyou_logo"><a href="${_ctxPath}/"><img src="${_imagesPath}/logo.png"></a></div>
        <div class="xingyou_slogan">
        	<img alt=""  src="${_imagesPath}/slogan.png">
        </div>
        <div class="xingyou_server">
           <ul>
           <s:if 
            	test="#session['SPRING_SECURITY_CONTEXT'].authentication.principal  !=null">
            	<li style="white-space:nowrap;text-overflow:ellipsis; width: 120px;overflow: hidden;height: 26px"><spring:message code="ui.label.hi"></spring:message>
            	<a href="${_ctxPath}/user/userCenter-userCenter.htm" >${session['SPRING_SECURITY_CONTEXT'].authentication.principal.username }</a></li>
                <li><a href="${_ctxPath}/j_spring_security_logout"><spring:message code="ui.label.logout"/></a></li>
            	</s:if><s:else>
            	<li>
            	<a href="${_ctxPath}/login.htm"><spring:message code="ui.label.login"/></a>
            	</li>
                <li><a href="${_ctxPath}/regist.htm"><spring:message code="ui.label.regist"/></a></li>
            	</s:else> 
                <li  ><a href=""><img style="vertical-align: middle;" src="${_imagesPath}/phone.png"  ></a> <a href="" style="font-size: 16px;">400-8222-1457</a></li>
            </ul>
</div>