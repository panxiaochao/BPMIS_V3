<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<c:out value="${webRoot}" />">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script language="javascript">
$(function(){
	countSecond();
}); 
 
var x = 6;
function countSecond()
{
	x = x-1;
	document.getElementById("errortext").innerHTML=x + " 秒后返回首页";
	if(x == 0)
		parent.window.location.href='login/errorout.do';
	else
		setTimeout("countSecond()", 1000);
}
</script>
<style type="text/css">
.new-error {
	width: 300px;
	padding: 15px;
}
.new-reindex {
	width: 115px;
	height: 35px;
	font-size: 14px;
	font-weight: bold;
	color: #fff;
	background: #3c95c8;
	display: block;
	line-height: 35px;
	text-align: center;
	border-radius: 3px;
	margin-top: 20px;
}
</style>
</head>

<body >
<div class="new-error">
  <h2>权限已超时，请到首页重新登录！</h2>
  <p>看到这个提示，就自认倒霉吧!</p>
  <div class="new-reindex" id="errortext">5秒后返回首页</div>
</div>
</body>
</html>

