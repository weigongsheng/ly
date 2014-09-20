/**
 * 
 */

function showContent(url,curObj){
	$(".menu li").removeClass("selected");
	$(curObj).addClass("selected");
	
	$.ajax({
		url:url,
		error:function (XMLHttpRequest, textStatus, errorThrown) {
			alert("连接错误");
		},
		success:function(data){
			 $("#content").html(data);
		},
		dataType:'html'
	});
}