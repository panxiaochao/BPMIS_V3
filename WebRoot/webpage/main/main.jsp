<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<base href="<c:out value="${webRoot}" />">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息管理界面</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<!--easyui-->
<link rel="stylesheet" type="text/css" href="plugin/easyui/themes/metro-gray/easyui.css">
<link rel="stylesheet" type="text/css" href="plugin/easyui/themes/icon.css">
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="js/jquery.exteasyui.js"></script>
<script type="text/javascript" src="plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery.ajaxCall.js"></script>
<style>
#footer{
	padding-top:6px;
	font-size: 95%;
	text-align: center;	
}
</style>

</head>

<body id="index_layout" class="easyui-layout">
<!--header-->
<div id="navhead" data-options="region:'north'" border="false" style="height:88px;"> 
	<iframe src="" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" class="topFrame" title="topFrame" frameborder="0" style="width:100%;height:100%;"></iframe> 
</div>

<!--left-->
<div id="west" data-options="region:'west',split:false" border="false" style="width:207px;">
	<iframe src="" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame" class="leftFrame" title="leftFrame" frameborder="0" style="width:100%;height:100%;"></iframe>
</div>
<!--主体-->
<div data-options="region:'center'" border="false"  >
  <iframe frameborder="0"  src="" name="rightFrame" id="rightFrame" class="rightFrame" style="width:100%;height:100%;"></iframe>
</div>
<!--footer-->
<div data-options="region:'south'"  style="height:30px;">
  <div id="footer"> Copyright © 2013-2014 BPMIS_CMS_INFO - Powered By Panxiaochao V 3.0 All Rights Reserved.   版权所有，仅供学习交流，勿用于任何商业用途！ </div>
</div>
<script type="text/javascript">

$(function() {
	/*_ajaxCall.post("system/deletelog.do", {id:"1"}, function(data){				
				alert(data);				
	}, "json", false, {});*/
	var $index = $("#index_layout");
	var $topframr = $index.find("iframe").get(0);
	var $leftframr = $index.find("iframe").get(1);
	var $rightframr = $index.find("iframe").get(2);

	//console.log($leftframr);

	document.getElementById("leftFrame").onload=function(){     
		//$leftframr.contentWindow.to();
	};

	
	init();
});

var $topFrame = $('#topFrame'); 
var $leftFrame = $('#leftFrame');
var $rightFrame = $('#rightFrame');

//left 切换
function tourl(str){
	$leftFrame.attr("src","login/mainleft.do?funid="+str); 
	console.log($rightFrame.attr("src"));
	$rightFrame.attr("src","login/index.do"); 
}
// reaload
function reload(str){
	$leftFrame.attr("src","webpage/main/templeft.jsp");
	$rightFrame.attr("src","login/index.do"); 
}
// init
function init(){
	$topFrame.attr("src","webpage/main/top.jsp");  
	$leftFrame.attr("src","webpage/main/templeft.jsp");  
	$rightFrame.attr("src","login/index.do"); 
}
</script>
</body>
</html>
