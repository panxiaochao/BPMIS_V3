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
<!-- time  -->
<script type="text/javascript" src="plugin/time/WdatePicker.js"></script>
<script type="text/javascript" src="plugin/time/lang/zh-cn.js"></script>
<link rel="stylesheet" href="plugin/time/skin/twoer/datepicker.css" type="text/css"></link>
<script type="text/javascript" src="plugin/time/calendar.js"></script>
<!--jqselect -->
<script src="plugin/jqselect/js/standalone/selectize.js"></script>
<link rel="stylesheet" href="plugin/jqselect/css/selectize.default.css">
<script type="text/javascript">

var submitForm = function($dialog, $grid, $pjq) {
	var url = "salary/savesort.do";		
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
	
	var $funsel = $('#sal-sel').selectize({
		create: false,
		dropdownParent: 'body'			
	});	
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
    <li>添加账单类别</li>
  </ul>
</div>
<!--header-->
<div class="formbody formborder" >
  <form method="post" name="submitform" id="submitform">
    <ul class="forminfo" style="padding-left:0;">
      <li>
        <label>类别名<b>*</b></label>
        <input name="sortname" type="text" class="dfinput" value=""  style="width:300px;"/>
      </li>
      <li>
        <label>类别方式<b>*</b></label>
        <div class="vocation">
          <select class="" id="sal-sel" name="sorttype" style="width:300px;">
			<option value="in" selected>收入</option>
			<option value="out">支出</option>       
          </select>
        </div>
      </li>          
    </ul>
  </form>
</div>
</body>
</html>
