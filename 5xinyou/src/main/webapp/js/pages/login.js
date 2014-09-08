/**
 * Creator: yipin
 * Date created: 13-2-27 上午10:19
 * Contact: QQ 77642304
 * Describe: 入库管理 列表查询页
 */
 // 定义一个全局类
    var yipin = yipin || {};
 // 显示提示
    yipin.showIcon = {
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
    yipin.remeberName = function(name,value){
    	var index = $("#remember").attr("checked");
    	if(index=='checked'){//如果选中了
    		//创建cookie
    		this.setCookie(name,value);
    	}else{//如果没选中 删除cookie
    		this.delCookie(name);
    	}
    };
    yipin.setCookie = function(name,value){
    	this.ajaxCookie("POST",_ctxPath+'/cookies'+_requestPath, {"cookieName":name,"index":"add","userName":value});
    };
    yipin.delCookie = function(name){
    	this.ajaxCookie("POST",_ctxPath+'/cookies'+_requestPath, {"cookieName":name,"index":"del"});
    };
    yipin.ajaxCookie = function(method,url,info){
    	$.ajax({
			type: method,
			url:url,
			data: info,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				alert(XMLHttpRequest.status);
			},
			success:function(data){
				alert(data);
			},
			dataType:'json'
		});
    };
$(function () {
    // 密码框默认文字
    yipin.psw = function () {
    	//记住密码 清空默认值
    	if($.trim($("#password").val())!=""){
    		$("#pwdStr").hide();
    	}
    	var timer=setInterval(function(){
    		if($.trim($("#password").val())!=""){
        		$("#pwdStr").hide();
        	}
    	},1000)
       $("#password").focus(function(){
        	   $("#pwdStr").hide();
			   $(this).removeClass("input-error");
			   $(this).addClass("input-focus").removeClass("input-default");
           });
           $("#password").blur(function(){
        	   if($(this).val()==""){// if($.trim($(this).val())=="")
            	   $("#pwdStr").show(); 
				   $(this).removeClass("input-focus").addClass("input-default");
        	   }
			   $(this).removeClass("input-focus");
           });
    };
    yipin.getCookie = function(name){
    	 var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    	 if(arr=document.cookie.match(reg))
    	        return unescape(arr[2]);
    	 else
    	        return null;
    };
	// 验证
    yipin.checkCode = function () {
       $.formValidator.initConfig({
				validatorGroup: '1',
				formID: 'login_form',
				theme: 'yto',
				errorFocus: false
				//submitAfterAjaxPrompt : '有数据正在异步验证，请稍等...'
			});
			
			// 用户名
			$("#account").
				formValidator({validatorGroup:'1', tipID: 'accountTip', onShow: '', onFocus: '', onCorrect: ''})
                .functionValidator({
                    fun: function(val, el) {
                      var val=val.replace(/\s+/g,"");
                      $("#account").val(val);
                        if(val.length<=0||val==$("#account").attr("data-default")){
                            return '请输入你的账号';
                        }
                    }
                });
				
			// 密码
			$("#password").
				formValidator({validatorGroup:'1', tipID: 'passwordTip', onShow: '', onFocus: '', onCorrect: ''})
                .functionValidator({
                    fun: function(val,el) {
                    	var val=val.replace(/\s+/g,"");
                        $("#password").val(val);
                        if(val.length<=0||val==$("#password").attr("data-default")){
                            return '密码不能为空';
                        }
                    }
                })/*.
				regexValidator({regExp: 'password', dataType: 'enum', onError: "密码格式错误"})*/;
			$("#authCode").formValidator({validatorGroup:'1', tipID: 'authCodetTip', onShow: '', onFocus: '', onCorrect: ''})
			.functionValidator({
                    fun: function(val, el) {
                    	if(opertNum>3){
                    		var val=val.replace(/\s+/g,"");
                            $("#authCode").val(val);
                            if(val.length<=0||val==$("#authCode").attr("data-default")){
                                return '验证码不能为空';
                            }
                    	}
                    }
                })
    };
    // 启动配置
    yipin.init = (function () {
		setTimeout(function(){$(".login .relative").css("position","relative");$("#imgLogin").show();},1000);
		if(yipin.getCookie("yipinusername")!=null&&yipin.getCookie("yipinusername")!=undefined&&yipin.getCookie("yipinusername")!=''){
	    	var yipinusername =str_decode(yipin.getCookie("yipinusername"));//登录名
	    	if(yipinusername!=null){//记住用户名
	    		var reg = new RegExp('"',"g");  
	        	var username = yipinusername.replace(reg, "");
	    		$("#account").val(decodeURIComponent(username));
	    	}
		}
		yipin.psw();
		yipin.checkCode();
       $("#account").input();
       $("#authCode").input();
    })();

});

//登录
	$("#account,#password,#authCode").on("keyup", function(e) {
		var code = (e.keyCode ? e.keyCode : e.which);
		if (code == 13) {
			$("#loginBtn").trigger('click');
		}
	});

	//点击输入验证码
	function nextValidateCode(id) {
		$("#" + id).attr(
				"src",
				_ctxPath + '/validationCode' + _requestPath + '?date='
						+ new Date().getTime());
		return false;
	}
	function showMsg(id, msg, codeId, status) {
		opertNum++;
		if (opertNum > 3) {
			$("#opertNum").val(opertNum);
			$("#codeDiv").show();
		}
		returnMsg(id, msg, codeId, status);
	}
	function returnMsg(id, msg, codeId, status) {
		if (status == '3') {
			//$("#authCode").focus();
			yipin.showIcon.error($("#" + id), msg);
		} else {
			yipin.showIcon.error($("#" + id), msg);
		}
		$("#authCode").val("");
		nextValidateCode(codeId);
	}

	function utf16to8(str) {
		var out, i, len, c;

		out = "";
		len = str.length;
		for (i = 0; i < len; i++) {
			c = str.charCodeAt(i);
			if ((c >= 0x0001) && (c <= 0x007F)) {
				out += str.charAt(i);
			} else if (c > 0x07FF) {
				out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
				out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
				out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
			} else {
				out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
				out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
			}
		}
		return out;
	}
	function utf8to16(str) {
		var out, i, len, c;
		var char2, char3;

		out = "";
		len = str.length;
		i = 0;
		while (i < len) {
			c = str.charCodeAt(i++);
			switch (c >> 4) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				// 0xxxxxxx
				out += str.charAt(i - 1);
				break;
			case 12:
			case 13:
				// 110x xxxx   10xx xxxx
				char2 = str.charCodeAt(i++);
				out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));
				break;
			case 14:
				// 1110 xxxx  10xx xxxx  10xx xxxx
				char2 = str.charCodeAt(i++);
				char3 = str.charCodeAt(i++);
				out += String.fromCharCode(((c & 0x0F) << 12)
						| ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));
				break;
			}
		}

		return out;
	}

	var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	var base64DecodeChars = new Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62,
			-1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1,
			-1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
			15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1,
			26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42,
			43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);

	function base64encode(str) {
		var out, i, len;
		var c1, c2, c3;

		len = str.length;
		i = 0;
		out = "";
		while (i < len) {
			c1 = str.charCodeAt(i++) & 0xff;
			if (i == len) {
				out += base64EncodeChars.charAt(c1 >> 2);
				out += base64EncodeChars.charAt((c1 & 0x3) << 4);
				out += "==";
				break;
			}
			c2 = str.charCodeAt(i++);
			if (i == len) {
				out += base64EncodeChars.charAt(c1 >> 2);
				out += base64EncodeChars.charAt(((c1 & 0x3) << 4)
						| ((c2 & 0xF0) >> 4));
				out += base64EncodeChars.charAt((c2 & 0xF) << 2);
				out += "=";
				break;
			}
			c3 = str.charCodeAt(i++);
			out += base64EncodeChars.charAt(c1 >> 2);
			out += base64EncodeChars.charAt(((c1 & 0x3) << 4)
					| ((c2 & 0xF0) >> 4));
			out += base64EncodeChars.charAt(((c2 & 0xF) << 2)
					| ((c3 & 0xC0) >> 6));
			out += base64EncodeChars.charAt(c3 & 0x3F);
		}
		return out;
	}
	function base64decode(str) {
		var c1, c2, c3, c4;
		var i, len, out;
		len = str.length;
		i = 0;
		out = "";
		while (i < len) {
			/* c1 */
			do {
				c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
			} while (i < len && c1 == -1);
			if (c1 == -1)
				break;

			/* c2 */
			do {
				c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
			} while (i < len && c2 == -1);
			if (c2 == -1)
				break;

			out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));

			/* c3 */
			do {
				c3 = str.charCodeAt(i++) & 0xff;
				if (c3 == 61)
					return out;
				c3 = base64DecodeChars[c3];
			} while (i < len && c3 == -1);
			if (c3 == -1)
				break;
			out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));
			/* c4 */
			do {
				c4 = str.charCodeAt(i++) & 0xff;
				if (c4 == 61)
					return out;
				c4 = base64DecodeChars[c4];
			} while (i < len && c4 == -1);
			if (c4 == -1)
				break;
			out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
		}
		return out;
	}
	//js_base64 加密
	function str_encode(str) {
		return base64encode(utf16to8(str));
	}
	//js_base64 解密
	function str_decode(str) {
		return utf8to16(base64decode(str));
	}

	var opertNum = $("#opertNum").val();//获得当前错误标记
	$(function() {
		if (!opertNum) {//
			$('#opertNum').val(1);
			opertNum = 1;
		}
		//登陆
		$("#loginBtn").click(
				function() {
					//验证表单输入合法性
					var result = $.formValidator.pageIsValid('1'); // 组号必须用带引号
					if (result) {
						//if(true){	
						var username = str_encode($("#account").val());
						var password = str_encode($("#password").val());
						var jCaptchaResponse = "";
						if ($("#codeDiv").is(":visible")) {
							jCaptchaResponse = $("#authCode").val();
						} else {
							jCaptchaResponse = "";
						}
						var login_form = $('#login_form');
						$.ajax({
							type : 'POST',
							url : _ctxPath + '/user/user-checkUserName'
									+ _requestPath,
							data : {
								"username" : username,
								"password" : password,
								"jCaptchaResponse" : jCaptchaResponse,
								"remeberName" : $("#remember").attr("checked"),
								"opertNum" : $("#opertNum").val()
							},
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
							},
							success : function(data) {
								switch (data.info) {
								//1 账号被禁用被禁用 、用户的账号重复 3 验证码不正确 5 密码不正确、用户的邮箱不正确 7注册异常 8  禁止登陆
								case '0':
									showMsg("accountTip", "用户不存在", "imgLogin",
											data.info);
									break;
								case '1':
									showMsg("accountTip", "用户被禁用", "imgLogin",
											data.info);
									break;
								case '3':
									showMsg("authCodetTip", "验证码不正确",
											"imgLogin", data.info);
									break;
								case '5':
									showMsg("accountTip", "账号或密码不正确 ",
											"imgLogin", data.info);
									break;
								case '7':
									showMsg("accountTip", "登陆异常", "imgLogin",
											data.info);
									break;
								case '8':
									showMsg("accountTip", "用户禁止登陆", "imgLogin",
											data.info);
									break;
								case '9':
									showMsg("accountTip", "非买家身份禁止登陆",
											"imgLogin", data.info);
									break;
								case '10':
									showMsg("accountTip", "用户名格式错误",
											"imgLogin", data.info);
									break;
								case '11':
									showMsg("accountTip", "密码格式错误", "imgLogin",
											data.info);
									break;
								case '6':
									//yipin.remeberName("yipinusername",$('#account').val());
									//$('#remberMe').attr("checked",$("#remember").attr("checked"));
									$('#tname').val($('#account').val());
									$('#tpassword').val($('#password').val());
									var true_from = $('#true_form');
									true_from.submit();
									break;//表示成功
								}
							},
							dataType : 'json'
						});
					}
				});

	})


