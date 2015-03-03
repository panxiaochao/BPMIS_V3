<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<c:out value="${webRoot}" />">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="js/jquery.ajaxCall.js"></script>
<script type="text/javascript">
$(function(){		
	// 获取菜单
	initfunction();
	//顶部导航切换
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected")
		$(this).addClass("selected");
	})
})	
</script>
<style>
.h2top {
	margin-top:3px;
}
</style>
</head>

<body style="background:url(images/topbg.gif) repeat-x;">
<div class="topleft"> <a href="main.html" target="_parent"><img src="images/logo.png" title="系统首页" /></a> </div>
<ul class="nav" id="nav">
  <!--
    <li><a href="default.html" target="rightFrame" ><img src="images/icon01.png" title="工作台" /><h2>工作台</h2></a></li>
    <li><a href="imgtable.html" target="rightFrame"><img src="images/icon02.png" title="模型管理" /><h2>模型管理</h2></a></li>
    <li><a href="imglist.html"  target="rightFrame"><img src="images/icon03.png" title="模块设计" /><h2>模块设计</h2></a></li>
    <li><a href="tools.html"  target="rightFrame"><img src="images/icon04.png" title="常用工具" /><h2>常用工具</h2></a></li>
    <li><a href="computer.html" target="rightFrame"><img src="images/icon05.png" title="文件管理" /><h2>文件管理</h2></a></li>
    
  <li><a href="javascript:toptab('salary');" target="rightFrame" class="selected"><img src="images/salary.png" title="工资管理" />
    <h2 class="h2top">工资管理</h2>
    </a></li>
  <li><a href="javascript:toptab('sys');"  target="rightFrame" ><img src="images/icon06.png" title="系统设置" />
    <h2 class="h2top">系统设置</h2>
    </a></li>-->
</ul>
<div class="topright">
  <ul>
    <li><span><img src="images/help.png" title="帮助"  class="helpimg"/></span><a href="javascript:help();">帮助</a></li>
    <li><a href="javascript:about();">关于</a></li>
    <li><a href="javascript:loginout();" >退出</a></li>
  </ul>
  <div class="user"> <span id="account"></span> <i>消息</i> <b>0</b> </div>
</div>
<script type="text/javascript">
// function
function initfunction(){
	parent.$.messager.progress({
		title : '提示',
		text : '加载中....'
	});
	var $nav = $("#nav");
	_ajaxCall.get("login/loginfun.do", {}, function(data){				
		if(data.length!=0){
			var html = "";
			$("#account").html(data[0].pojo.account);
			$(data[0].list).each(function(i,n){
				if(n.nodelevel==2){//目录
					if(i==0)
						html = "<li><a href=\"javascript:toptab('"+n.id+"');\" class=\"selected\"><img src=\"images/"+n.iconname+"\" title=\""+n.nodename+"\" />"
    						+"<h2 class=\"h2top\">"+n.nodename+"</h2>"
    						+"</a></li>";
    				else
    					html = "<li><a href=\"javascript:toptab('"+n.id+"');\" ><img src=\"images/"+n.iconname+"\" title=\""+n.nodename+"\" />"
    						+"<h2 class=\"h2top\">"+n.nodename+"</h2>"
    						+"</a></li>";
    				$nav.append(html);
				}			       
        	});
		}
		else
			parent.$.messager.alert('提示', "没有数据！", 'info');
		parent.$.messager.progress('close');
	}, "json", false, {});
}
//help
function help(){
	parent.$.messager.alert('提示', "帮助", 'info');
}
//about
function about(){
	parent.$.messager.alert('提示', "关于", 'info');
}
//logingout
function loginout(){
	parent.$.messager.confirm('询问', '您确定要退出？', function(b) {
		if (b) {
			parent.$.messager.progress({
				title : '提示',
				text : '退出中....'
			});				
			parent.window.location.href='login/logout.do';
		}
	});
}
// toptab
function toptab(str){
	parent.tourl(str)
}
// reload
function reload(str){
	$("#nav").html("");
	initfunction();
	//顶部导航切换
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected")
		$(this).addClass("selected");
	})
	parent.reload();
}

</script>
</body>
</html>
