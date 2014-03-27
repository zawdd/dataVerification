
$(function(){ 
    $("#begtime").datepicker(); 
    $("#endtime").datepicker(); 
});

function getScheduleByStationID(){
   $("#summary").empty();
   $("tr:has(td)").remove();
   $.getJSON("data.json?stationid="+$("#stationid").val()+"&begtime="+
		  $("#begtime").val()+"&endtime="+$("#endtime").val(),function(result){
    	$.each(result, function(i, field){
    		 $("#result").append("<tr>"+
    				 "<td>"+i+"</td>"+
    				 "<td class='scheduleid'>"+field.schedule_id+"</td>"+
    				 "<td class='stationid'>"+field.station_id+"</td>"+
    				 "<td class='stationname'>"+field.station_name+"</td>"+
    				 "<td class='programid'>"+field.program_id+"</td>"+
    				 "<td class='programname'>"+field.program_name+"</td>"+
    				 "<td class='starttime'>"+field.start_time+"</td>"+
    				 "<td class='endtime'>"+field.end_time+"</td>"+
    				 "<td class='country'>"+field.country+"</td>"+
    				 "<td class='state'>"+field.state+"</td>"+
    				 "</tr>");
   	 	});
	});
   $.get("check.json?stationid="+$("#stationid").val()+"&begtime="+
			  $("#begtime").val()+"&endtime="+$("#endtime").val(), function(result){
	    $("#summary").append(result);
	});
}

function getScheduleByProgramID(){
   $("#summary").empty();
   $("tr:has(td)").remove();
   $.getJSON("getschedulesbyprogramid.json?programid="+$("#programid").val()+"&begtime="+
		  $("#begtime").val()+"&endtime="+$("#endtime").val(),function(result){
    	$.each(result, function(i, field){
    		 $("#result").append("<tr>"+
    				 "<td>"+i+"</td>"+
    				 "<td class='scheduleid'>"+field.schedule_id+"</td>"+
    				 "<td class='stationid'>"+field.station_id+"</td>"+
    				 "<td class='stationname'>"+field.station_name+"</td>"+
    				 "<td class='programid'>"+field.program_id+"</td>"+
    				 "<td class='programname'>"+field.program_name+"</td>"+
    				 "<td class='starttime'>"+field.start_time+"</td>"+
    				 "<td class='endtime'>"+field.end_time+"</td>"+
    				 "<td class='country'>"+field.country+"</td>"+
    				 "<td class='state'>"+field.state+"</td>"+
    				 "</tr>");
   	 	});
	});
}
	
function getProgramsByID(){
	 $("tr:has(td)").remove();
	 $.getJSON("getprogramsbyid.json?programid="+$("#programid").val(),function(result){
    	$.each(result, function(i, field){
    		 $("#programresult").append("<tr>"+
    				 "<td>"+i+"</td>"+
    				 "<td class='programid'>"+field.programID+"</td>"+
    				 "<td class='programname'>"+field.programTitle+"</td>"+
    				 "<td class='language'>"+field.language+"</td>"+
    				 "<td class='category'>"+field.category+"</td>"+
    				 "<td class='describe'>"+field.describe+"</td>"+
    				 "</tr>");
   	 	});
	});
}

function getProgramsByTitle(){
	 $("tr:has(td)").remove();
	 $.getJSON("getprogramsbytitle.json?programtitle="+$("#programtitle").val(),function(result){
   	$.each(result, function(i, field){
   		 $("#programresult").append("<tr>"+
   				 "<td>"+i+"</td>"+
				 "<td class='programid'>"+field.programID+"</td>"+
				 "<td class='programname'>"+field.programTitle+"</td>"+
				 "<td class='language'>"+field.language+"</td>"+
				 "<td class='category'>"+field.category+"</td>"+
				 "<td class='describe'>"+field.describe+"</td>"+
   				 "</tr>");
  	 	});
	});
}

function getStationsByZipcode(){
	 $("tr:has(td)").remove();
	 $.getJSON("getstationsbyzipcode.json?zipcode="+$("#zipcode").val(),function(result){
 	$.each(result, function(i, field){
 		 $("#stationresult").append("<tr>"+
 				 "<td>"+i+"</td>"+
 				 "<td class='stationid'>"+field.stationID+"</td>"+
 				 "<td class='stationname'>"+field.stationName+"</td>"+
 				 "<td class='zipcode'>"+field.zipcode+"</td>"+
 				 "<td class='headendid'>"+field.headendID+"</td>"+
 				 "<td class='country'>"+field.country+"</td>"+
 				 "<td class='state'>"+field.state+"</td>"+
 				 "<td class='city'>"+field.city+"</td>"+
 				 "</tr>");
	 	});
	});	
}
function getStationsByID(){
	 $("tr:has(td)").remove();
	 $.getJSON("getstationsbystationid.json?stationid="+$("#stationid").val(),function(result){
	$.each(result, function(i, field){
		 $("#stationresult").append("<tr>"+
				 "<td>"+i+"</td>"+
 				 "<td class='stationid'>"+field.stationID+"</td>"+
 				 "<td class='stationname'>"+field.stationName+"</td>"+
 				 "<td class='zipcode'>"+field.zipcode+"</td>"+
 				 "<td class='headendid'>"+field.headendID+"</td>"+
 				 "<td class='country'>"+field.country+"</td>"+
 				 "<td class='state'>"+field.state+"</td>"+
 				 "<td class='city'>"+field.city+"</td>"+
				 "</tr>");
	 	});
	});	
}

function getStationsByHeadendID(){
	 $("tr:has(td)").remove();
	 $.getJSON("getstationsbyheadendid.json?headendid="+$("#headendid").val(),function(result){
	$.each(result, function(i, field){
		 $("#stationresult").append("<tr>"+
				 "<td>"+i+"</td>"+
 				 "<td class='stationid'>"+field.stationID+"</td>"+
 				 "<td class='stationname'>"+field.stationName+"</td>"+
 				 "<td class='zipcode'>"+field.zipcode+"</td>"+
 				 "<td class='headendid'>"+field.headendID+"</td>"+
 				 "<td class='country'>"+field.country+"</td>"+
 				 "<td class='state'>"+field.state+"</td>"+
 				 "<td class='city'>"+field.city+"</td>"+
				 "</tr>");
	 	});
	});	
}

function getHeadendsByID(){
	 $("tr:has(td)").remove();
	 $.getJSON("getheadendsbyheadendid.json?headendid="+$("#headendid").val(),function(result){
	$.each(result, function(i, field){
		 $("#headendresult").append("<tr>"+
				 "<td>"+i+"</td>"+
				 "<td class='headendid'>"+field.headendID+"</td>"+
				 "<td class='headendname'>"+field.headendName+"</td>"+
				 "<td class='zipcode'>"+field.zipcode+"</td>"+
				 "<td class='state'>"+field.state+"</td>"+
				 "<td class='location'>"+field.location+"</td>"+
				 "</tr>");
	 	});
	});		
}

function getHeadendsByZipcode(){
	 $("tr:has(td)").remove();
	 $.getJSON("getheadendsbyheadendzipcode.json?zipcode="+$("#zipcode").val(),function(result){
	$.each(result, function(i, field){
		 $("#headendresult").append("<tr>"+
				 "<td>"+i+"</td>"+
				 "<td class='headendid'>"+field.headendID+"</td>"+
				 "<td class='headendname'>"+field.headendName+"</td>"+
				 "<td class='zipcode'>"+field.zipcode+"</td>"+
				 "<td class='state'>"+field.state+"</td>"+
				 "<td class='location'>"+field.location+"</td>"+
				 "</tr>");
	 	});
	});		
}

function hideAllTables(){
	$("#result_cb").hide();
	$("#result").hide();
	
	$("#programresult").hide();
	$("#programresult_cb").hide();
	
	$("#stationresult").hide();
	$("#stationresult_cb").hide();
	
	$("#headendresult").hide();
	$("#headendresult_cb").hide();
}

function hideAllInputs(){
	$("#stationid").attr("disabled","disabled");
	$("#begtime").attr("disabled","disabled");
	$("#endtime").attr("disabled","disabled");
	$("#programid").attr("disabled","disabled");
	$("#programtitle").attr("disabled","disabled");
	$("#headendid").attr("disabled","disabled");
	$("#zipcode").attr("disabled","disabled");
}

function bodyInitil(){
	hideAllTables();
	$("#programtitle").attr("disabled","disabled");
	$("#headendid").attr("disabled","disabled");
	$("#zipcode").attr("disabled","disabled");
}
$(document).ready(function(){
	
	bodyInitil();
	
	$("#submit").click(function(){
		if($("#tableselect").val() == "schedule"){
			$("#result").show();
			$("#result_cb").show();
			if($("#begtime").val() != "" && $("#stationid").val() != "" && $("#endtime").val() != ""){
				getScheduleByStationID();
				return;
			}
			else if($("#begtime").val() != "" && $("#programid").val() != "" && $("#endtime").val() != ""){
				getScheduleByProgramID();
				return;
			}else{
				alert("Make sure that begtime and endtime are not empty, stationid and programid only one can be set");
				return;
			}
		}
		if($("#tableselect").val() == "program"){
			$("#programresult").show();
			$("#programresult_cb").show();
			if($("#programid").val() != ""){
				getProgramsByID();
				return;
			}else if($("#programtitle").val() != ""){
				getProgramsByTitle();
				return;
			}else{
				alert("Make sure that either programID or programTitle is not empty!");
				return;
			}			
		}
		if($("#tableselect").val() == "station"){
			$("#stationresult").show();
			$("#stationresult_cb").show();
			if($("#stationid").val() != ""){
				getStationsByID();
				return;
			}else if($("#headendid").val() != ""){
				getStationsByHeadendID();
				return;
			}else if($("#zipcode").val() != ""){
				getStationsByZipcode();
				return;
			}else{
				alert("Make sure that either stationid , headendid or zipcode is not empty");
				return;
			}			
		}
		if($("#tableselect").val() == "headend"){
			$("#headendresult").show();
			$("#headendresult_cb").show();
			if($("#headendid").val() != ""){
				getHeadendsByID();
				return;
			}else if($("#zipcode").val() != ""){
				getHeadendsByZipcode();
				return;
			}else{
				alert("Make sure that either headendID or zipcode is not empty!");
				return;
			}
		}	   
	});
	
	$("#tableselect").change(function(){
		hideAllTables();
		hideAllInputs();
		$("#summary").empty();
		$("#summary").append("table change to " + $("#tableselect").val());
		if($("#tableselect").val() == "schedule"){
			$("#stationid").removeAttr("disabled");
			$("#begtime").removeAttr("disabled");
			$("#endtime").removeAttr("disabled");
			$("#programid").removeAttr("disabled");
		}
		if($("#tableselect").val() == "program"){
			$("#programid").removeAttr("disabled");
			$("#programtitle").removeAttr("disabled");
		}
		if($("#tableselect").val() == "station"){
			$("#stationid").removeAttr("disabled");
			$("#zipcode").removeAttr("disabled");
			$("#headendid").removeAttr("disabled");
		}
		if($("#tableselect").val() == "headend"){
			$("#headendid").removeAttr("disabled");
			$("#zipcode").removeAttr("disabled");
		}
	});
	
	//show or hide a col
	$(".scheduleid_cb").click(function(){
		 $(".scheduleid").toggle();
	});
	
	$(".stationid_cb").click(function(){
		 $(".stationid").toggle();
	});
	
	$(".stationname_cb").click(function(){
		 $(".stationname").toggle();
	});
	
	$(".programid_cb").click(function(){
		 $(".programid").toggle();
	});

	$(".programname_cb").click(function(){
		 $(".programname").toggle();
	});
	
	$(".starttime_cb").click(function(){
		 $(".starttime").toggle();
	});
	
	$(".endtime_cb").click(function(){
		 $(".endtime").toggle();
	});
	
	$(".country_cb").click(function(){
		 $(".country").toggle();
	});
	
	$(".state_cb").click(function(){
		 $(".state").toggle();
	});
	
	$(".language_cb").click(function(){
		 $(".language").toggle();
	});
	
	$(".category_cb").click(function(){
		 $(".category").toggle();
	});
	
	$(".describe_cb").click(function(){
		 $(".describe").toggle();
	});
	
	$(".zipcode_cb").click(function(){
		 $(".zipcode").toggle();
	});
	
	$(".headendid_cb").click(function(){
		 $(".headendid").toggle();
	});
	
	$(".city_cb").click(function(){
		 $(".city").toggle();
	});
	
	$(".headendname_cb").click(function(){
		 $(".headendname").toggle();
	});
	
	$(".location_cb").click(function(){
		 $(".location").toggle();
	});
	
});	
