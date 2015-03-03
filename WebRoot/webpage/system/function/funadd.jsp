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
<!--easyui-->
<link rel="stylesheet" type="text/css" href="plugin/easyui/themes/metro-blue/easyui.css">
<link rel="stylesheet" type="text/css" href="plugin/easyui/themes/icon.css">
<script type="text/javascript" src="plugin/easyui/jquery.easyui.min.js"></script>
<!--jqselect -->
<script src="plugin/jqselect/js/standalone/selectize.js"></script>
<link rel="stylesheet" href="plugin/jqselect/css/selectize.default.css">
<script type="text/javascript">

var submitForm = function($dialog, $grid, $pjq) {
	var url = "function/save.do";		
	//alert($("#submitform").serialize());
	_ajaxCall.post(url, $("#submitform").serialize(), function(data){				
		if(data.success){
			$pjq.messager.alert('提示', data.msg, 'info');
			$grid.treegrid('load');
			$dialog.dialog('destroy');
		}else{
			$pjq.messager.alert('提示', data.msg, 'error');
			$dialog.dialog('destroy');
		}			
	}, "json", false, {});	
};	

//fun  -  select
var fun_select = function(){
	var options = [];
	_ajaxCall.get("function/getParent.do", {}, function(data){						
		$.each(data, function(i, n){
	    	options.push({
		        id: n.id,
		        title: n.nodename
		    });
	    });			
	}, "json", false, {});	
	return options;
}

$(document).ready(function(e) {
	var clientHeight = document.documentElement.clientHeight;
	$(".formborder").css("min-height",clientHeight - 28);
	$(".noresize").css("resize","none");

	//fun
	var funoptions = fun_select();	
	
	var $funsel = $('#fun-sel').selectize({
		create: false,
		valueField: 'id', // 值的字段
		labelField: 'title', // label 绑定字段
		searchField: 'title', //搜索字段
		options: funoptions,
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
<!--header-->
<div class="formbody formborder" >
  <form method="post" name="submitform" id="submitform">
    <ul class="forminfo" style="padding-left:0;">
      <li>
        <label>菜单名<b>*</b></label>
        <input name="nodename" type="text" class="dfinput" value=""  style="width:300px;"/>
      </li>
      <li>
        <label>菜单路径<b>*</b></label>
        <input name="nodeurl" type="text" class="dfinput" value=""  style="width:300px;"/>
      </li>
      <li>
        <label>图片名<b>*</b></label>
        <input name="iconname" type="text" class="dfinput" value=""  style="width:300px;"/>
      </li>
      <li>
        <label>上级菜单<b>*</b></label>
        <div class="vocation">
          <select class="" id="fun-sel" name="parentid" placeholder="请选择菜单……" style="width:300px;"></select>
        </div>
      </li>
      
      <li>
        <label>是否启用<b>*</b></label>
        <label><input name="nodeview" type="radio" value="1" checked="checked"/> 是</label>
        <label><input name="nodeview" type="radio" value="0" /> 否</label>
      </li>
      <li>
        <label>菜单值<b>*</b></label>
        <input name="nodeorder" type="text" class="dfinput" value=""  style="width:100px;"/>
      </li>
      <li>
        <label>备注<b>*</b></label>
        <textarea  name="remark" class="dfborder noresize"  style="width:300px;height:80px;"></textarea>
      </li>
    </ul>
  </form>
</div>
</body>
</html>
