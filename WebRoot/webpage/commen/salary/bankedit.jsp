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
<!--jqselect -->
<script src="plugin/jqselect/js/standalone/selectize.js"></script>
<link rel="stylesheet" href="plugin/jqselect/css/selectize.default.css">
<script type="text/javascript">

var submitForm = function($dialog, $grid, $pjq) {
	var url = "salary/updatebank.do";		
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
	
	var control = $funsel[0].selectize;
	control.setValue(['${requestScope.pojo.banktype}']);
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
    <li>编辑银行卡</li>
  </ul>
</div>
<!--header-->
<div class="formbody formborder" >
  <form method="post" name="submitform" id="submitform">
    <input type="hidden" name="id" value="${requestScope.pojo.id}" />
    <ul class="forminfo" style="padding-left:0;">
      <li>
        <label>银行卡名<b>*</b></label>
        <input name="bankname" type="text" class="dfinput" value="${requestScope.pojo.bankname}"  style="width:300px;"/>
      </li>
      <li>
        <label>归属<b>*</b></label>
        <div class="vocation">
          <select class="" id="sal-sel" name="banktype" style="width:300px;">
			<option value="PSBC">中国邮政储蓄银行</option>   
			<option value="CMB">招商银行</option>  
			<option value="HZSMK">杭州市民卡</option> 
			<option value="FLK">福利卡</option>
			<option value="BOC">中国银行</option>
			<option value="ICBC">中国工商银行</option>   
			<option value="CCB">中国建设银行</option> 
			<option value="ABC">中国农业银行</option> 
			<option value="OTHER">其他</option>
          </select>
        </div>
      </li> 
	  <li>
        <label>初始化金额<b>*</b></label>
        <input name="money" type="text" class="dfinput" value="${requestScope.total}"  style="width:300px;"/>
      </li>            
    </ul>
  </form>
</div>
</body>
</html>
