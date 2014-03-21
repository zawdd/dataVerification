<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"	src="./js/jquery.min.js"></script>
<script type="text/javascript"	src="./js/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="./js/jquery-ui-datepicker.js"></script> 
<script type="text/javascript" src="./js/bootstrap.min.js"></script> 
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css" /> 
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" /> 
<style type="text/css">
	body { padding-bottom: 60px; }
	
</style>
<script>


$(function(){ 
    $("#begtime").datepicker(); 
    $("#endtime").datepicker(); 
});

function getSchedule(){
   if($("#begtime").val() == "" || $("#stationid").val() == "" || $("#endtime").val() == ""){
	   alert("start time, end time or station id is empty!");
	   return ;
   }
   $("#summary").empty();
   $("tr:has(td)").remove();
   $.getJSON("data.json?stationid="+$("#stationid").val()+"&begtime="+
		  $("#begtime").val()+"&endtime="+$("#endtime").val(),function(result){
    	$.each(result, function(i, field){
    		 $("#result").append("<tr>"+
    				 "<td>"+i+"</td>"+
    				 "<td>"+field.schedule_id+"</td>"+
    				 "<td>"+field.station_id+"</td>"+
    				 "<td>"+field.station_name+"</td>"+
    				 "<td>"+field.program_id+"</td>"+
    				 "<td>"+field.program_name+"</td>"+
    				 "<td>"+field.start_time+"</td>"+
    				 "<td>"+field.end_time+"</td>"+
    				 "<td>"+field.country+"</td>"+
    				 "<td>"+field.state+"</td>"+
    				 "</tr>");
   	 	});
	});
   $.get("check.json?stationid="+$("#stationid").val()+"&begtime="+
			  $("#begtime").val()+"&endtime="+$("#endtime").val(), function(result){
	    $("#summary").append(result);
	});
}

function getPrograms(){
	if($("#stationid").val() == ""){
		   alert("station id is empty!");
		   return ;
	 }
	 $("tr:has(td)").remove();
	 $.getJSON("getprograms.json?stationid="+$("#stationid").val(),function(result){
    	$.each(result, function(i, field){
    		 $("#programresult").append("<tr>"+
    				 "<td>"+i+"</td>"+
    				 "<td>"+field.stationID+"</td>"+
    				 "<td>"+field.programID+"</td>"+
    				 "<td>"+field.programTitle+"</td>"+
    				 "<td>"+field.language+"</td>"+
    				 "<td>"+field.category+"</td>"+
    				 "<td>"+field.describe+"</td>"+
    				 "</tr>");
   	 	});
	});
}
function getStations(){
	if($("#stationid").val() == ""){
		   alert("station id is empty!");
		   return ;
	 }
	 $("tr:has(td)").remove();
	 $.getJSON("getstations.json?stationid="+$("#stationid").val(),function(result){
 	$.each(result, function(i, field){
 		 $("#stationresult").append("<tr>"+
 				 "<td>"+i+"</td>"+
 				 "<td>"+field.stationID+"</td>"+
 				 "<td>"+field.stationName+"</td>"+
 				 "<td>"+field.lcountry+"</td>"+
 				 "<td>"+field.state+"</td>"+
 				 "<td>"+field.city+"</td>"+
 				 "</tr>");
	 	});
	});	
}
function getHeadends(){
	if($("#stationid").val() == ""){
		   alert("station id is empty!");
		   return ;
	 }
	 $("tr:has(td)").remove();
	 $.getJSON("getheadends.json?stationid="+$("#stationid").val(),function(result){
	$.each(result, function(i, field){
		 $("#headendresult").append("<tr>"+
				 "<td>"+i+"</td>"+
				 "<td>"+field.stationID+"</td>"+
				 "<td>"+field.headendID+"</td>"+
				 "<td>"+field.headendName+"</td>"+
				 "<td>"+field.state+"</td>"+
				 "<td>"+field.location+"</td>"+
				 "</tr>");
	 	});
	});		
}
function getLineups(){
	if($("#stationid").val() == ""){
		   alert("station id is empty!");
		   return ;
	 }
	 $("tr:has(td)").remove();
	 $.getJSON("getlineups.json?stationid="+$("#stationid").val(),function(result){
	$.each(result, function(i, field){
		 $("#lineupresult").append("<tr>"+
				 "<td>"+i+"</td>"+
				 "<td>"+field.stationID+"</td>"+
				 "<td>"+field.lineupID+"</td>"+
				 "<td>"+field.headendID+"</td>"+
				 "</tr>");
	 	});
	});		
}

function hideAllTables(){
	$("#result").hide();
	$("#programresult").hide();
	$("#lineupresult").hide();
	$("#stationresult").hide();
	$("#headendresult").hide();
}

$(document).ready(function(){
	
	hideAllTables();
	
	$("#submit").click(function(){
		if($("#tableselect").val() == "schedule"){
			$("#result").show();
			getSchedule();
		}
		if($("#tableselect").val() == "program"){
			$("#programresult").show();
			getPrograms();
		}
		if($("#tableselect").val() == "station"){
			$("#stationresult").show();
			getStations();
		}
		if($("#tableselect").val() == "headend"){
			$("#headendresult").show();
			getHeadends();
		}
		if($("#tableselect").val() == "lineup"){
			$("#lineupresult").show();
			getLineups();
		}
			   
	});
	
	$("#tableselect").change(function(){
		hideAllTables();
		$("#summary").empty();
		$("#summary").append("table change to " + $("#tableselect").val());
	});
	
	
});	
</script>
<title>dataVerify</title>

</head>
<body>
<ul class="nav nav-tabs">
  <li class="active"><a href="dataverify.html">Datebase</a></li>
  <li><a href="xmltv.html">Xmltv</a></li>
</ul>
table<select id="tableselect">
<option value = "schedule">schedule</option>
<option value = "program">program</option>
<option value = "station">station</option>
<option value = "headend">headend</option>
<option value = "lineup">lineup</option>
</select>

stationid:<input type="text" id="stationid" name="stationid"/>
begtime:<input type="text" id="begtime" name="begtime"/>
endtime:<input type="text" id="endtime" name="endtime" />
<button id="submit" >submit</button>

<table class="table table-hover table-condensed" id="result">
<tr>
<th>index</th>
<th>schedule_id</th>
<th>station_id</th>
<th>station_name</th>
<th>program_id</th>
<th>program_name</th>
<th>start_time</th>
<th>end_time</th>
<th>country</th>
<th>state</th>
</tr>
</table>
<table class="table table-hover table-condensed" id="programresult">
<tr>
<th>index</th>
<th>station_id</th>
<th>program_id</th>
<th>program_title</th>
<th>language</th>
<th>category</th>
<th>describe</th>
</tr>
</table>
<table class="table table-hover table-condensed" id="stationresult">
<tr>
<th>index</th>
<th>station_id</th>
<th>station_name</th>
<th>country</th>
<th>state</th>
<th>city</th>
</tr>
</table>
<table class="table table-hover table-condensed" id="lineupresult">
<tr>
<th>index</th>
<th>station_id</th>
<th>lineup_id</th>
<th>headend_name</th>
</tr>
</table>
<table class="table table-hover table-condensed" id="headendresult">
<tr>
<th>index</th>
<th>station_id</th>
<th>headend_id</th>
<th>headend_name</th>
<th>state</th>
<th>location</th>
</tr>
</table>
<div class="alert alert-info navbar-fixed-bottom" id="summary">

</div>
</body>
</html>
