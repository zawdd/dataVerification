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
<script type="text/javascript" src="./js/jquery.fileDownload.js"></script> 
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

function displaydetail(id){
	$("#"+id).toggle();	
}

function hideAllTables(){
	$("#result").hide();
	$("#channelinfo").hide();
}

$(document).ready(function(){
	
	hideAllTables();
	$("#submit").click(function(){
	   $("#summary").empty();
	   $("tr:has(td)").remove();
	   hideAllTables();
	   $("#result").show();
	   if($("#begtime").val() == "" || $("#stationid").val() == "" || $("#endtime").val() == ""){
		   alert("start time, end time or station id is empty!");
		   return ;
	   }
	   $.getJSON("xmlverify.json?stationid="+$("#stationid").val()+"&begtime="+
			  $("#begtime").val()+"&endtime="+$("#endtime").val(),function(result){
	    	$.each(result, function(i, field){
	    		 $("#result").append("<tr>"+
	    				 "<td>"+i+"</td>"+
	    				 "<td>"+field.title+"</td>"+
	    				 "<td>"+field.Ericsson_ProgramId+"</td>"+
	    				 "<td>"+field.channel+"</td>"+
	    				 "<td>"+field.str_start_time+"</td>"+
	    				 "<td>"+field.str_end_time+"</td>"+
	    				 '<td><button type="button" class="btn btn-default " onclick=displaydetail("tr'+i+'")>'+
	    				    '<span class="caret"></span>'+
	    				    '</button></td>'+
	    				 "</tr>"+
	    				 "<tr id = 'tr"+i+"' style='display:none;'><td colspan='7'><xmp>"+field.other_content+"</xmp></td></tr>"
	    				 );
	   	 	});
		});
	   $.get("checkxml.json?stationid="+$("#stationid").val()+"&begtime="+
			  $("#begtime").val()+"&endtime="+$("#endtime").val(), function(result){
		    $("#summary").append(result);
		});
	});
	
	$("#fileselect").empty();

	$.getJSON("getkeys.json",function(result){
		$.each(result, function(i, field){
			 $("#fileselect").append("<option>"+field+"</option>");			 
		});
	});
	
	$.get("getcurrentkey.json", function(result){
		$("#fileselect").val(result);
	});
	
	$("#fileselect").change(function(){
		$.get("setkey.json?filename="+$("#fileselect").val(), function(result){
			$("#summary").empty();
			$("#summary").append("file change to " + result);
			//alert("change to "+result);
		});
	});
	
	$("#fileinfo").click(function(){
		$("tr:has(td)").remove();
		hideAllTables();
		$("#channelinfo").show();
	   $.getJSON("channelsummary.json",function(result){
	    	$.each(result, function(i, field){
	    		if (field.isCorrect == false){
	    			mytr = "<tr class='danger'>";
	    		}else{
	    			mytr = "<tr>";
	    		}
	    		 $("#channelinfo").append(mytr+
	    				 "<td>"+i+"</td>"+
	    				 "<td>"+field.id+"</td>"+
	    				 "<td>"+field.name+"</td>"+
	    				 "<td>"+field.description+"</td>"+
	    				 "<td>"+field.str_beg+"</td>"+
	    				 "<td>"+field.str_end+"</td>"+
	    				 "<td>"+field.prog_num+"</td>"+
	    				 "</tr>");
	   	 	});
		});
	});
	
	$("#export").click(function(){
		if($("#begtime").val() == "" || $("#stationid").val() == "" || $("#endtime").val() == ""){
			alert("start time, end time or station id is empty!");
			return ;
		}else{
			uri = "exportsearchresult.json?stationid="+$("#stationid").val()+"&begtime="+
			 		 $("#begtime").val()+"&endtime="+$("#endtime").val();
			window.location.href = uri;
		}
		
		//window.open(uri,'Download');
		
	   /* $.get("exportsearchresult.json?stationid="+$("#stationid").val()+"&begtime="+
				  $("#begtime").val()+"&endtime="+$("#endtime").val(),function(result){
		   
    	  //window.location.href = result;
    	  window.open(result,'Download');
		   //alert("chick to download"+result);
 			/* $.fileDownload(result)
					    .done(function () { alert('File download success!'); })
					    .fail(function () { alert('File download failed!'); }); 
		}); */
		//$.get();
	});
	
});	
</script>
<title>xmldataVerify</title>
</head>
<body>
<ul class="nav nav-tabs">
  <li><a href="dataverify.html">Datebase</a></li>
  <li class="active" ><a href="xmltv.html">Xmltv</a></li>
</ul>

<form class="form-inline" method="POST" action="/upload.do" enctype="multipart/form-data">  
<div class="form-group">
    <label for="file" class="sr-only">file</label>
    <div class="col-sm-10">
     <input type="file" id="file" name="file"/>
    </div>
</div>  
<button id="filesubmit" type="submit" value="upload">submit</button>
</form>

file<select id="fileselect"></select>
<button id="fileinfo">summary</button>
channel id:<input type="text" id="stationid" name="stationid"/>
begtime:<input type="text" id="begtime" name="begtime"/>
endtime:<input type="text" id="endtime" name="endtime" />
<button id="submit" >query</button>
<button id="export">export</button>

<table class="table table-hover table-condensed" id ="channelinfo">
<tr>
<th>#</th>
<th>channel_id</th>
<th>name</th>
<th>description</th>
<th>start_time</th>
<th>end_time</th>
<th>program_numbers</th>
</tr>
</table>

<table class="table table-hover table-condensed" id ="result">
<tr>
<th>#</th>
<th>title</th>
<th>Ericsson_ProgramId</th>
<th>channel_id</th>
<th>start_time</th>
<th>end_time</th>
<th>detail</th>
</tr>
</table>

<div class="alert alert-info navbar-fixed-bottom" id="summary"></div>
</body>
</html>
