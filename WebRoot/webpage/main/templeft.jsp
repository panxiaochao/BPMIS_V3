<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<c:out value="${webRoot}" />">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<!-- Main Stylesheet -->
<link rel="stylesheet" href="leftsider/css/leftmenu.css" type="text/css" media="screen" />

<!-- jQuery -->
<script type="text/javascript" src="leftsider/scripts/jquery-1.8.2.min.js"></script>

<!-- jQuery Configuration -->
<script type="text/javascript" src="leftsider/scripts/jquery.pxcleft.js"></script>
<script type="text/javascript">
$(function(){	
	//导航切换
	$(".navson li").click(function(){	
		$(".navson li.nav_active").removeClass("nav_active");
		$(".navson li a.current").removeClass("current");
		$(this).addClass("nav_active");
		$(this).children('a').addClass("current")
	});
})	
function to(){
	alert(1);
}
</script>
</head>

<body style="background:#f0f9fd;">
<div class="lefttop"><span></span>默认管理</div>
<div class="leftmenu">
  <div id="sidebar">
    <div id="sidebar-wrapper"> <!-- Sidebar with logo and menu --> 
      <!-- Logo (221px wide) --> 
      <!-- Sidebar Profile links -->
      <ul id="main-nav">
        <!-- Accordion Menu -->
        <li> <a href="#" class="nav-top-item current"> <!-- Add the class "current" to current menu item --> 
         默认管理</a>
          <ul class="navson">
            <li class="nav_active"><cite></cite><a href="login/index.do" target="rightFrame">首页</a><i></i></li>           
          </ul>
        </li>
      </ul>
      <!-- End #main-nav -->      
    </div>
  </div>
</div>
</body>
</html>
