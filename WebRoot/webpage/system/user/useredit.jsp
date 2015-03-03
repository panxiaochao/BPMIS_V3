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

var submitForm = function($dialog, $grid, $pjq) {
	var url = "user/update.do";		
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
	$(".formborder").css("min-height",clientHeight - 68);
	$(".noresize").css("resize","none");
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
.vocation{float:left;}
</style>
</head>

<body>
<div class="place"> <span>位置：</span>
  <ul class="placeul">
    <li>编辑用户</li>
  </ul>
</div>
<!--header-->
<div class="formbody formborder" >
  <form method="post" name="submitform" id="submitform">
  	<input type="hidden" name="id" value="${requestScope.pojo.id}" />
  	<input type="hidden" name="pwd" value="${requestScope.pojo.pwd}" />
  	<input type="hidden" name="bpmisid" value="${requestScope.pojo.bpmisid}" />
  	<input type="hidden" name="regtime" value="${requestScope.pojo.regtime}" />
    <ul class="forminfo" style="padding-left:0;">
      <li>
        <label>账号<b>*</b></label>
        <input name="account" type="text" class="dfinput" value="${requestScope.pojo.account}"  style="width:300px;"/>
      </li>
      <li>
        <label>姓名<b>*</b></label>
        <input name="username" type="text" class="dfinput" value="${requestScope.pojo.username}"  style="width:300px;"/>
      </li>     
      <li>
        <label>性别<b>*</b></label>
        <label><input name="sex" type="radio" value="0" <c:if test="${requestScope.pojo.sex==0}"> checked="checked" </c:if> /> 男
        <input name="sex" type="radio" value="1" <c:if test="${requestScope.pojo.sex==1}"> checked="checked" </c:if> /> 女</label>
      </li>
      <li>
        <label>邮箱<b>*</b></label>
        <input name="email" type="text" class="dfinput" value="${requestScope.pojo.email}"  style="width:300px;"/>
      </li>
      <li>
        <label>备注<b>*</b></label>
        <textarea  name="remark" class="dfborder noresize"  style="width:300px;height:100px;">${requestScope.pojo.remark}</textarea>
      </li>
    </ul>
  </form>
</div>
</body>
</html>
