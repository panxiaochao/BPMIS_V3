<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="<%=path%>/css/style.css" rel="stylesheet" type="text/css" />
<!--easyui-->
<link rel="stylesheet" type="text/css" href="<%=path%>/plugin/easyui/themes/metro-gray/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/plugin/easyui/themes/icon.css">
<script type="text/javascript" src="<%=path%>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.ajaxCall.js"></script>
<script type="text/javascript" src="<%=path%>/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugin/time/WdatePicker.js"></script>
<!--highcharts-->
<script src="<%=path%>/plugin/highcharts/js/highcharts.js"></script>
<script src="<%=path%>/plugin/highcharts/js/themes/grid-light.js"></script>
<script type="text/javascript">
var chart;
var opt;
$(function() {
	//var colors = Highcharts.getOptions().colors
	opt = {
		credits: {
			enabled: false
		},
		chart: {
			type: 'line',
			renderTo:'container',
			height: $(window).height()-100
			//zoomType: 'x' //放大功能			
		},
		yAxis: {							
			title: {
				enabled: false
			}
		},
		tooltip: {
			/*formatter:function(){
				return ''+ this.point.category +'<br/><b>'+this.series.name+'：'+this.y + ' 元</b>';  
			}*/
			useHTML: true,
            headerFormat: '<b><a href="javascript:void(0)" onclick="Hidetail(\'{point.key}\',\'{series.name}\')">{point.key}</a></b><table>',
            pointFormat: '<tr><td style="color: {series.color}">{series.name}: </td>' +
                '<td style="text-align: right"><b>{point.y} 元</b></td></tr>',
            footerFormat: '</table>',
			crosshairs: true
		},
		legend: { 
			layout: 'horizontal', 
			borderWidth: 0
		},
		plotOptions: {
			line: {
				dataLabels: {
					enabled: true
				}
			}
		},
		exporting: {
			enabled: false
		}	
	};
	//chart = new Highcharts.Chart(opt);
	//format
	getNowFormatDate();
});
function Hsearch() {
	var url = "<%=path%>/hicharts/hibarline.do";	
	//alert($("#submitform").serialize());	
	var start = document.getElementById("dp1").value, start_1 = start.split('-')[0], start_2 = start.split('-')[1];
	var end = document.getElementById("dp2").value, end_1 = end.split('-')[0], end_2 = end.split('-')[1];
	
	if(start==""||end=="")
		$.messager.alert('注意','起始日期或者截止日期不能为空','warning');		
	else{
		if(end_1==start_1){
			if(end_2>start_2){
				$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				_ajaxCall.post(url, {starttime:start, endtime: end}, function(data){	
					var categ = data.xAxis;			
					$.extend(opt, {title: {text: data.title }});
					$.extend(opt, {xAxis: {categories: categ.split(';') }});			
					$.extend(opt, {series: data.list});
					console.log(opt);
					chart = new Highcharts.Chart(opt);
					$.messager.progress('close');
				}, "json", false, {});
			}
			else
				$.messager.alert('注意','截止日期应大于起始日期','warning');	 
		}else if(end_1>start_1){
			if(end_2<=start_2){
				$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				_ajaxCall.post(url, {starttime: start,endtime: end }, function(data){	
					var categ = data.xAxis;			
					$.extend(opt, {title: {text: data.title }});
					$.extend(opt, {xAxis: {categories: categ.split(';') }});			
					$.extend(opt, {series: data.list});
					chart = new Highcharts.Chart(opt);
					$.messager.progress('close');
				}, "json", false, {});
			}				
			else
				$.messager.alert('注意','日期范围请在一年之内','warning');		 
		}else
			$.messager.alert('注意','日期范围请在一年之内','warning');	
	}
	
}

function Hidetail(time, type) {
	var url, trade;
	if(type=="收入")
		trade = "in";
	else
		trade = "out";
	url = "<%=path%>/webpage/commen/salary/salary_fcheck.jsp?time="+time+"&type="+trade;
	var dialog = parent.ext.modalDialog({
		title : time+" 详情",		
		url: url,
		width:1154, 
		height: 500
			
	});
}
</script>
</head>

<body id="index_layout" class="easyui-layout">
<!--header-->
<div id="navhead" data-options="region:'north'" border="false" style="height:40px;">
  <div class="place"> <span>位置：</span>
    <ul class="placeul">
      <li><a href="#">首页</a></li>
      <li>数据统计</li>
    </ul>
  </div>
</div>
<!--主体-->
<div style="margin-top:40px; width:100%;">
  <div class="easyui-panel" style="padding:5px; margin:5px 0;"> 开始日期:
    <input style="width:100px" onClick="WdatePicker({dateFmt:'yyyy-MM'})" id="dp1" />
    &nbsp;|&nbsp;截止日期:
    <input style="width:100px" onClick="WdatePicker({dateFmt:'yyyy-MM'})" id="dp2" />
    <!--&nbsp;|&nbsp;支付类别:
    <select class="easyui-combobox" panelHeight="auto" style="width:100px" id="">
      <option value="java">Java</option>
      <option value="c">C</option>
      <option value="basic">Basic</option>
      <option value="perl">Perl</option>
      <option value="python">Python</option>
    </select>-->
    &nbsp;|&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="Hsearch()">搜索&nbsp;</a> </div>
</div>

<div id="container" style=" width:99%; margin:0 auto;"></div>
<script> 
function getNowFormatDate() 
{ 
	var date = new Date(); 
	var CurrentDay = ""; 
	var CurrentM = ""; 
	//初始化时间 
	var y= date.getFullYear();//ie火狐下都可以 
	var m= date.getMonth()+1; 
	var d = date.getDate(); 

	document.getElementById("dp2").value=y + "-" + ((m) < 10 ? ("0" + (m)) : (m));
}

</script>
</body>
</html>
