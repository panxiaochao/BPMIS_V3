<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<c:out value="${webRoot}" />">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/select.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="js/select-ui.min.js"></script>
<script type="text/javascript" src="js/jquery.ajaxCall.js"></script>
<script type="text/javascript">

var submitForm = function($dialog, $grid, $pjq) {
	var url = "system/savelog.do";		
	//alert($("#submitform").serialize());
	_ajaxCall.post(url, $("#submitform").serialize(), function(data){				
		if(data.success){
			$pjq.messager.alert('提示', data.msg, 'info');
			$grid.datagrid('load');
			$dialog.dialog('destroy');
		}else{
			$pjq.messager.alert('提示', data.msg, 'error');
			$dialog.dialog('destroy');
		}			
	}, "json", false, {});
	
	
	
};	
$(document).ready(function(e) {
	var clientHeight = document.documentElement.clientHeight;
	$(".formborder").css("min-height",clientHeight - 28);
});
</script>
<style>
* {
	border:0;
	margin:0;
	padding:0;
}
body {
	min-width:0;
}
</style>
</head>

<body>
<!--header-->
<div class="formbody formborder" >
  <form method="post" name="submitform" id="submitform">
    <ul class="forminfo" style="padding-left:0;">
      <li>
        <label>日志内容<b>*</b></label>
        <input name="logcontent" type="text" class="dfinput" value="日志内容"  style="width:300px;"/>
      </li>
    </ul>
  </form>
</div>
</body>
</html>
