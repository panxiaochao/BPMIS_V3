// JavaScript Document



var neonLogin = neonLogin || {};
(function($, window, undefined){  //闭包
    "use strict"; // 严格模式   
    $.fn.pxc = function() {     
      // 开始吧！ 	    
    };  
	
	$(document).ready(function()
	{
		//初始化
		init();
		//
		neonLogin.$bg = $("#bg");
		neonLogin.$tiploading = $(".tiploading");
		neonLogin.$comrie = $(".comrie");
		neonLogin.$progress_bar_text_success = $("#progress-bar-text-success");
		neonLogin.$progress_bar = $("#login-progressbar div");
		neonLogin.$progress_bar_text = $("#progress-bar-text");
	});
	
    // Functions
	$.extend(neonLogin, {
		setPercentage: function(pct, callback)
		{
			pct = parseInt(pct / 100 * 100, 10) + '%';								
			neonLogin.$progress_bar_text.html(pct);
			neonLogin.$progress_bar.width(pct);
			
			var o = {
				pct: parseInt(neonLogin.$progress_bar.width() / neonLogin.$progress_bar.parent().width() * 100, 10)
			};
			
			TweenMax.to(o, .7, {
				pct: parseInt(pct, 10),
				roundProps: ["pct"],
				ease: Sine.easeOut,
				onUpdate: function()
				{
					neonLogin.$progress_bar_text.html(o.pct + '%');
				},
				onComplete: callback
			});			
		},
		
		resetProgressBar: function(display_errors)
		{						
			neonLogin.$progress_bar_text.html('0%');
			neonLogin.$progress_bar.width(0);			
			//TweenMax.set(neonLogin.$bg, {css: {opacity: 0}});				
			setTimeout(function()
			{
				TweenMax.to(neonLogin.$bg, .6, {css: {opacity: 0}, onComplete: function()
				{
					neonLogin.$bg.attr('style', '');
				}});
								
				if(display_errors){
					// do something
				}
				
			}, 650);
		}
	});
	
	var progress_text_up = function(value, mins){
		
	}; 
	
	var init = function(){
		$("body").append("<div id=\"bg\"></div>");
	};
})( jQuery, window); 