var xingyou = xingyou || {};
//显示提示
xingyou.showIcon = {
    correct:function (el, text) {
        el.show().html('<span class="onError-txt">' + text + '</span>');
    },
    error:function (el, text) {
        el.show().html('<span class="onError-txt">' + text + '</span>');
    },
    show:function (el, text) {
        el.show().html('<span class="onError-txt">' + text + '</span>');
    }
};
$(function () {
    // 定义一个全局类
    
	
    // 密码框默认文字
    xingyou.psw = function () {
    	//记住密码
    	if($.trim($("#password").val())!=""){
    		$("#passwordStr").hide();
    	}
		$("#mail").focus(function(){
            $("#focusTip").show();
    	   $("#usernameStr").hide();
		   $(this).removeClass("input-error");
		   $(this).addClass("input-focus").removeClass("input-default");
       });
       $("#mail").blur(function(){
    	   if($(this).val()==""){
        	   $("#usernameStr").show(); 
			   $(this).removeClass("input-focus").addClass("input-default");
    	   }
           $("#focusTip").hide();
		   $(this).removeClass("input-focus");
       });
       $("#password").focus(function(){
    	   $("#passwordStr").hide();
		   $(this).removeClass("input-error");
		   $(this).addClass("input-focus").removeClass("input-default");
       });
       $("#password").blur(function(){
    	   if($(this).val()==""){
        	   $("#passwordStr").show(); 
			   $(this).removeClass("input-focus").addClass("input-default");
    	   }
		   $(this).removeClass("input-focus");
       });
	   
	   $("#psw").focus(function(){
    	   $("#pswStr").hide();
		   $(this).removeClass("input-error");
		   $(this).addClass("input-focus").removeClass("input-default");
       });
       $("#psw").blur(function(){
    	   if($(this).val()==""){
        	   $("#pswStr").show(); 
			   $(this).removeClass("input-focus").addClass("input-default");
    	   }
		   $(this).removeClass("input-focus");
       });
    };
	// 验证
    xingyou.checkCode = function () {
       $.formValidator.initConfig({
				validatorGroup: '1',
				formID: 'register_form',
				theme: 'yto',
				errorFocus: false
				//submitAfterAjaxPrompt : '有数据正在异步验证，请稍等...'
			});
			
			//邮箱
			$("#mail").formValidator({
			validatorGroup:'1',
            "onShow":'',
            "onFocus":'',
            "onCorrect":''
			}).functionValidator({
                    fun: function(val, el) {
                    	  var val=val.replace(/\s+/g,"");
                          $("#mail").val(val);
                        if(val.length<=0||val==$("#mail").attr("data-default")){
                            return '用户名不能为空或空格';
                        }else{
                        	var email = '^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$';
                        	var t =/@+/;
                        	if(t.test(val)){//如果含有@就是邮箱
                        		if(!val.match(email)){//验证是否为邮箱
                            		return '邮箱格式不正确';
                            	}
                        	}else{
				               if(!/^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/i.test(val)){
									return '手机号码格式不正确';
								}					
							}
                        }
                        
                    }
                })
				
			// 密码
			$("#password").
				formValidator({validatorGroup:'1', tipID: 'passwordTip', onShow: '', onFocus: '', onCorrect: ''})
                .functionValidator({
                    fun: function(val, el) {
                    	var val=val.replace(/\s+/g,"");
                        $("#password").val(val);
                        if(val.length<=0||val==$("#password").attr("data-default")){
                        	$("#password").val("");
                            return '密码不能为空';
                        }
                    }
                }).inputValidator({
                    "min":6,
                    "max":15,
                    "onErrorMin":'请输入6-15位数字、字母或符号组合',
                    "onErrorMax":'请输入6-15位数字、字母或符号组合'
                });
			//验证码
			$("#authCode").
				formValidator({validatorGroup:'1', tipID: 'authCodetTip', onShow: '', onFocus: '', onCorrect: ''})
                .functionValidator({
                    fun: function(val, el) {
                    	var val=val.replace(/\s+/g,"");
                        $("#authCode").val(val);
                        if(val.length<=0||val==$("#authCode").attr("data-default")){
                            return '验证码不能为空';
                        }
                    }
                })
				
			$("#psw").formValidator({validatorGroup:'1',onShow:"",onFocus:"",onCorrect:""})
			.inputValidator({min:1,onError:"确认密码不能为空"})
			.compareValidator({desID:"password",operateor:"=",onError:"两次密码输入不一致，请重新输入"});
    };
    xingyou.Form=function(){
		$("#loginBtn").on("click",function(){
			var index=0;
			//验证表单
            var result = $.formValidator.pageIsValid('1');
			if(!$("#cheboxTK").prop("checked")){
				$("#cheboxTKTip").html("请接受条款");
				$("#cheboxTKTip").show();
				index++;
			}
			if(result&&index<=0){
				$("#login_form").submit();
			}
		});
	}
    // 启动配置
    xingyou.init = (function () {
	  setTimeout(function(){$(".login .relative").css("position","relative");$("#imgLogin").show();},1000);
		xingyou.psw();
		xingyou.checkCode();
       $("#mail").input();
       $("#authCode").input();
    })();

});


//登录
$("#mail,#password,#psw,#authCode").on("keyup",function(e){
    var code = (e.keyCode ? e.keyCode : e.which);
    if (code == 13) {
        $("#registerBtn").trigger('click');
    }
});
function returnMsg(id,msg,codeId,status){
	$("#authCode").val("");
	switch(status){
		//1 账号异常 2 账号重复 5验证码失误 6注册成功
		case '1':
			xingyou.showIcon.error($("#"+id),msg);
			break;
		case '2':
			xingyou.showIcon.error($("#"+id),msg);
			break;
		case '3':
			xingyou.showIcon.error($("#"+id),msg);
			break;
		case '4':
			xingyou.showIcon.error($("#"+id),msg);
			break;
		case '5':
			//$("#authCode").focus();
			xingyou.showIcon.error($("#"+id),msg);
		case '7':
			//$("#authCode").focus();
			xingyou.showIcon.error($("#"+id),msg);
		case '8':
			//$("#authCode").focus();
			xingyou.showIcon.error($("#"+id),msg);
			break;
	}
	nextValidateCode(codeId);
}
//点击输入验证码
function nextValidateCode(id) {
	$("#"+id).attr("src",_ctxPath+'/validationCode'+_requestPath+'?date='+new Date().getTime());
	return false;
}
//base 64位加密
function utf16to8(str) {
    var out, i, len, c;

    out = "";
    len = str.length;
    for(i = 0; i < len; i++) {
        c = str.charCodeAt(i);
        if ((c >= 0x0001) && (c <= 0x007F)) {
            out += str.charAt(i);
        } else if (c > 0x07FF) {
            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
            out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));
            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
        } else {
            out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));
            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
        }
    }
    return out;
}
var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
function base64encode(str) {
    var out, i, len;
    var c1, c2, c3;

    len = str.length;
    i = 0;
    out = "";
    while(i < len) {
        c1 = str.charCodeAt(i++) & 0xff;
        if(i == len)
        {
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt((c1 & 0x3) << 4);
            out += "==";
            break;
        }
        c2 = str.charCodeAt(i++);
        if(i == len)
        {
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
            out += base64EncodeChars.charAt((c2 & 0xF) << 2);
            out += "=";
            break;
        }
        c3 = str.charCodeAt(i++);
        out += base64EncodeChars.charAt(c1 >> 2);
        out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
        out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >>6));
        out += base64EncodeChars.charAt(c3 & 0x3F);
    }
    return out;
}
//js_base64 加密
function str_encode(str){
    return base64encode(utf16to8(str));
}
$(function(){
	$("#registerBtn").click(function(){
		var register_form = $("#register_form");
		var index = 0;
		var result = $.formValidator.pageIsValid('1');
			if(!$("#cheboxTK").prop("checked")){
				$("#cheboxTKTip").html("请接受条款");
				$("#cheboxTKTip").show();
				index++;
			}
		if(result&&index<=0){
		//	if(true){
			var username=str_encode($("#mail").val());
			var password=str_encode($("#password").val());
			var jCaptchaResponse=$("#authCode").val();
			//验证表单合法性
			$.ajax({
				type: 'POST',
				url:_ctxPath+'/register-email'+_requestPath,
				data: {
					"username":username,
					"password":password,
					"jCaptchaResponse":jCaptchaResponse
				},
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					
				},
				success:function(data){
					switch(data.info){
						//1 账号异常 2 账号重复 5验证码失误 6注册成功
						case '1':
							returnMsg("mailTip","抱歉,注册失败,请重新注册!","imgLogin",data.info);
							break;
						case '2':
							returnMsg("mailTip","用户名已经注册!","imgLogin",data.info);
							break;
						case '3':
							returnMsg("mailTip","输入正确的邮箱!","imgLogin",data.info);
							break;
						case '4':
							returnMsg("mailTip","该邮箱已经被使用!","imgLogin",data.info);
							break;
						case '5':
							returnMsg("authCodetTip","验证码错误!","imgLogin",data.info);
							break;
						case '6':
							$('#tname').val( $('#mail').val());
							$('#tpassword').val($('#password').val());
							var true_from =$('#true_form');
							true_from.submit();
							break;//表示成功
						case '7':
							returnMsg("mailTip","用户名格式错误!","imgLogin",data.info);
							break;
						case '8':
							returnMsg("mailTip","密码格式错误!","imgLogin",data.info);
							break;
					}
				},
				dataType:'json'
			});
		}
	});
})