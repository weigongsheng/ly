var ds=24*3600;
	$(function(){
		$("input[name='leftTime']").each(function(){
			var ctrl = $(this);
			setInterval(function(){
				var lt = ctrl.val();
				if(lt && lt >0){
					lt = lt -1;
					ctrl.val(lt);
					var ls = lt;
					var second = Math.floor(ls%60);
					var minute = Math.floor((ls/60)%60);
					var hour = Math.floor((ls/3600)%24);
					var day = Math.floor(ls/ds);
					ctrl.parent().find(".ltDay").html(day);
					ctrl.parent().find(".ltHour").html(hour<10?"0"+hour:hour);
					ctrl.parent().find(".ltMi").html(minute<10?"0"+minute:minute);
					ctrl.parent().find(".ltSec").html(second<10?"0"+second:second);
				}
			},1000);
		});
	});