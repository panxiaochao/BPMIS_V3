// jquery.exteasyui.js made by panxiaochao

var ext = ext || {};
(function($, window, undefined){  //闭包
    "use strict"; // 严格模式
    $.fn.pxc = function() {  
    
    };  
	
	$(document).ready(function(){

	});
	
	$.extend(ext, {
		modalDialog:function(option) {          
			var opts = $.extend({
				title : '&nbsp;',
				width : 640,
				height : 480,
				modal : true,
				draggable : false,
				onClose : function() {
					$(this).dialog('destroy');
				}
			}, option);
			opts.modal = true;// 强制此dialog为模式化，无视传递过来的modal参数
			if (option.url) {
				opts.content = '<iframe id="" src="'+option.url+'" allowTransparency="true" scrolling="auto" width="100%" height="100%" frameBorder="0" name=""></iframe>';
			}
			return $('<div></div>').dialog(opts);
		}    
	});
	
	
    
	

  
  
})( jQuery, window); 