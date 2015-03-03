// jquery ajaxCall by panxiaochao 2014.7.7

var _ajaxCall = _ajaxCall || {};
(function($, window, undefined){  //闭包
    "use strict"; // 严格模式
	
	var defaults = {		   
	    loading: "",
		modal: "",
		emptytext: ""    
	}; 	
	$.extend(_ajaxCall, {
		post: function(url, params, callback, datatype, isAsync, parm) {
			var opts = jQuery.extend({}, defaults, parm);
			$.ajax({
				type: "POST",
				url: url,
				data: params ? params : {},	
				dataType: datatype,
				async: isAsync ? true : false,
				cache: false,
				beforeSend: function(XMLHttpRequest){
					
				},
				success: function(data) {
					if(typeof(callback) == "function")  callback(data);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown){
					//请求出错处理
					alert("解析出错！\r\n"+errorThrown);	
				}
			});
		},
		get: function(url, params, callback, datatype, isAsync, parm) {
			var opts = jQuery.extend({}, defaults, parm);
			$.ajax({
				type: "GET",
				url: url,
				data: params ? params : {},	
				dataType: datatype,
				async: isAsync ? true : false,
				cache: false,
				beforeSend: function(XMLHttpRequest){
					
				},
				success: function(data) {
					if(typeof(callback) == "function")  callback(data);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown){
					//请求出错处理
					alert("解析出错！\r\n"+errorThrown);	
				}
			});
		}
	});
	
})( jQuery, window); 