<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/include/taglibs.jsp"%>
 
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>

 <div class="xingyou_logo_bg">
    <div class="xingyou_logo_nav">
         <%@ include file="/WEB-INF/pages/include/topHead.jsp" %>
        <div class="xingyou_nav">
            <ul class="xingyou_nav_ula">
            	            <li class="xingyou_nav_ula_li">
                    <a href="" class="nav_b"><spring:message code="ui.lable.newProduct"/> </a>
                                    </li>
                                <li class="xingyou_nav_ula_li">
                    <a href="${_ctxPath}/p/product-list.htm" class="nav_b"><spring:message code="ui.lable.easyHoliday"/></a>
                                    </li>
                			 
				<li class="xingyou_nav_ula_li">
                    <a href="" class="nav_b"><spring:message code="ui.lable.brandCenter"/></a>
                </li>
            </ul>
            <div class="xingyou_nav_ulb_search">
            	<div class="xingyou_nav_ulb_search_input">
                <input type="text" class="xingyou_nav_ulb_search_input" style="float: left; padding: 3px 6px; width: 190px; height: 22px; border: none; display: inline; color: rgb(209, 209, 209);" name="txt_stext" id="select_text" value="" x-webkit-speech="">
                </div>
                <div class="xingyou_nav_ulb_search_search" style="float:right;   padding:0; margin:0; border:none; display:inline;">
                <a href="javascript:void(0);" onclick="">
                <img src="${_imagesPath}/btn/search_btn.png" width="50" height="29" alt="我要搜">
                </a>
                </div>
            </div>
        </div>
    </div>
</div>
