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
</script>

	</head>


	<body>

		<div class="place">
			<span>位置：</span>
			<ul class="placeul">
				<li>
					<a href="login/index.do">首页</a>
				</li>
			</ul>
		</div>

		<div class="mainindex">


			<div class="welinfo">
				<span><img src="images/sun.png" alt="天气" />
				</span>
				<b><c:out value="${requestScope.user.username}" />，晚上好，欢迎使用信息管理系统</b>&nbsp;(管理员：panxiaochao602@qq.com)
				<a href="javascript:void(0);">帐号设置</a>
			</div>

			<div class="welinfo">
				<span><img src="images/time.png" alt="时间" />
				</span>
				<i>您上次登录的时间：<c:out value="${requestScope.user.lasttime}" /></i> （不是您登录的？
				<a href="javascript:void(0);">请点这里</a>）
			</div>

			<div class="xline"></div>

			<ul class="iconlist">
				<li>
					<img src="images/ico03.png" />
					<p>
						<a href="javascript:void(0);">数据统计</a>
					</p>
				</li>
				<li>
					<img src="images/ico06.png" />
					<p>
						<a href="javascript:void(0);">查询</a>
					</p>
				</li>
				<!--  
				<li>
					<img src="images/ico01.png" />
					<p>
						<a href="javascript:void(0);">管理设置</a>
					</p>
				</li>
				
			
				<li>
					<img src="images/ico02.png" />
					<p>
						<a href="javascript:void(0);">发布文章</a>
					</p>
				</li>
				
				<li>
					<img src="images/ico04.png" />
					<p>
						<a href="javascript:void(0);">文件上传</a>
					</p>
				</li>
				<li>
					<img src="images/ico05.png" />
					<p>
						<a href="javascript:void(0);">目录管理</a>
					</p>
				</li>
				
				-->
			</ul>
			<!--  
			<div class="ibox">
				<a class="ibtn"><img src="images/iadd.png" />添加新的快捷功能</a>
			</div>
			-->
			<div class="xline"></div>
			<div class="box"></div>
		</div>
	</body>
</html>
