<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<c:out value="${webRoot}" />">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>BPMIS V 3.0 全新起航</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="js/bpmis.login.js"></script>
<script type="text/javascript" src="js/jquery.ajaxCall.js"></script>
<script src="plugin/gsap/main-gsap.js"></script>
<!-- 飘云
<script src="js/cloud.js" type="text/javascript"></script>
-->
<!--nice validator-->
<link rel="stylesheet" href="plugin/nice-validator/jquery.validator.css" />
<script type="text/javascript" src="plugin/nice-validator/jquery.validator.js"></script>
<script type="text/javascript" src="plugin/nice-validator/local/zh_CN.js"></script>
<script language="javascript">
$(function(){
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
	$(window).resize(function(){  
    	$('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
    })  
});  
</script>
<style>
* {
	font-size:9pt;
	border:0;
	margin:0;
	padding:0;
}
span {
	margin: 0;
	padding: 0;
	display: block;
}
</style>
</head>

<body style="background-color:#1c77ac; background-image:url(images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">
<div id="mainBody">
  <div id="cloud1" class="cloud"></div>
  <div id="cloud2" class="cloud"></div>
</div>
<div class="logintop"> <span>欢迎登录后台管理界面平台，BPMIS V 3.0 全新起航，等待你的加入！</span>
  <ul>
    <li><a href="#">帮助</a></li>
    <li><a href="#">关于</a></li>
  </ul>
</div>
<div class="loginbody"> <span class="systemlogo"></span>
  <div class="loginbox">
    <form id="loginForm" action="" method="post">
      <ul>
        <li>
          <input type="text" class="loginuser" name="username" id="username" value="panxiaochao" />
        </li>
        <li>
          <input type="password" class="loginpwd" name="pwd" id="pwd" value="123456" />
        </li>
        <li>
          <input name="" type="submit" class="loginbtn" value="登录"   />
          <label>
            <input name="" type="checkbox" value="" id="" checked="checked" />
            记住密码</label>
          <label><a href="#">忘记密码？</a></label>
        </li>
      </ul>
    </form>
  </div>
</div>
<div class="loginbm">Copyright © 2013-2014 BPMIS_CMS_INFO - Powered By <a href="#"  >Panxiaochao</a> V 3.0 All Rights Reserved.&nbsp;&nbsp;&nbsp;版权所有，仅供学习交流，勿用于任何商业用途！</div>

<!--loading-->
<div class="tiploading">
  <div class="tiptopone"><span>登录验证中</span></div>
  <div class="tiprightone w100">
    <div class="w95" style="padding:10px;">
      <div id="login-progressbar" class="login-progressbar">
        <div style="width: 2%;"></div>
      </div>
      <div class="login-text"><span id="progress-bar-text" style="display:inline;">0%</span>&nbsp;&nbsp;<span id="progress-bar-text-success" style="display:inline;">验证中……</span></div>
    </div>
  </div>
</div>

<!--comrie-->
<div class="comrie">
  <div class="tiptopone"><span>提示信息</span></div>
  <div class="tiprightone w100">
    <div class="w95">
      <div class="tipinfoone"><span><img src="images/ticon.png" /></span>
        <div class="tiprightone">
          <p>您的用户名名或密码不正确，请重新登录！</p>
          <cite>您的登录次数还有5次。</cite> </div>
      </div>
      <div class="tipbtnone">
        <input name="" type="button"  class="sure" value="关闭" onclick="$('.comrie').hide();" />
      </div>
    </div>
  </div>
</div>
<script language="javascript">
$(document).ready(function(){
	//验证初始化
	$('#loginForm').validator({ 
		theme: 'yellow_top',
		stopOnError:true,
		timely: 2,
		fields: {
			"username": {
				rule: "用户名:required"
			},					
			"pwd": {
				rule: "required;length[3~];password;strength"
			}					
		},
		//验证成功
		valid: function(form) {
			//do something...
			neonLogin.$bg.fadeIn(200);
			neonLogin.$tiploading.fadeIn(100);			
			setTimeout(function(){
				var random_pct = 25 + Math.round(Math.random() * 30);
				neonLogin.setPercentage(40+ random_pct);
				// Send data to the server
				var url = "login/checkuserlogin.do";
				_ajaxCall.post(url, $(form).serialize(), function(data){				
					neonLogin.setPercentage(100);
					neonLogin.$progress_bar_text_success.html("验证完成！");
					// We will give some time for the animation to finish, then execute the following procedures	
					setTimeout(function(){							
						// If login is invalid, we store the 
						if(data.success == true)
						{
							neonLogin.$progress_bar_text_success.html("正在跳转中……");
							// Redirect to login page
							setTimeout(function(){
								window.location.href='login/login.do';															
							}, 400);						
						} else {
							neonLogin.resetProgressBar(true);
							neonLogin.$comrie.show().find("p").html(data.msg);
							neonLogin.$tiploading.fadeOut(100);
							
						}
					},1000);
					
					//$("#bg").fadeOut(100);				
					//$(".tiploading").fadeOut(200);				
				
				}, "json", false, {});
			},650);				
		},
		//验证失败
		invalid: function(form) {

		}
	});		
});
</script>
</body>
</html>
