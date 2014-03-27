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
<script type="text/javascript" src="./js/dataverify.js"></script> 
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css" /> 
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" /> 
<style type="text/css">
	body { padding-bottom: 60px; }
</style>

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
</select>

stationid:<input type="text" id="stationid" name="stationid" style="width:5%"/>
begtime:<input type="text" id="begtime" name="begtime" style="width:5%"/>
endtime:<input type="text" id="endtime" name="endtime" style="width:5%"/>
program_id:<input type="text" id="programid" name="programid" style="width:5%"/>
program_title:<input type="text" id="programtitle" name = "programtitle" style="width:5%"/>
headend_id:<input type="text" id="headendid" name="headendid" style="width:5%"/>
zipcode:<input type="text" id="zipcode" name="zipcode" style="width:5%"/>
<button id="submit" >query</button>

<div id="result_cb">
<input checked type="checkbox" class="scheduleid_cb">schedule_id</input>
<input checked type="checkbox" class="stationid_cb">station_id</input>
<input checked type="checkbox" class="stationname_cb">station_name</input>
<input checked type="checkbox" class="programid_cb">program_id</input>
<input checked type="checkbox" class="programname_cb">programname</input>
<input checked type="checkbox" class="starttime_cb">start_time</input>
<input checked type="checkbox" class="endtime_cb">end_time</input>
<input checked type="checkbox" class="country_cb">country</input>
<input checked type="checkbox" class="state_cb">state</input>
</div>

<table class="table table-hover table-condensed" id="result">
<tr>
<th>index</th>
<th class="scheduleid">schedule_id</th>
<th class="stationid">station_id</th>
<th class="stationname">station_name</th>
<th class="programid">program_id</th>
<th class="programname">program_name</th>
<th class="starttime">start_time</th>
<th class="endtime">end_time</th>
<th class="country">country</th>
<th class="state">state</th>
</tr>
</table>
<div id="programresult_cb">
<input checked type="checkbox" class="programid_cb">program_id</input>
<input checked type="checkbox" class="programname_cb">programname</input>
<input checked type="checkbox" class="language_cb">language</input>
<input checked type="checkbox" class="category_cb">category</input>
<input checked type="checkbox" class="describe_cb">describe</input>
</div>
<table class="table table-hover table-condensed" id="programresult">
<tr>
<th>index</th>
<th class="programid">program_id</th>
<th class="programname">program_title</th>
<th class="language">language</th>
<th class="category">category</th>
<th class="describe">describe</th>
</tr>
</table>
<div id="stationresult_cb">
<input checked type="checkbox" class="stationid_cb">station_id</input>
<input checked type="checkbox" class="stationname_cb">station_name</input>
<input checked type="checkbox" class="zipcode_cb">zipcode</input>
<input checked type="checkbox" class="headendid_cb">headend_id</input>
<input checked type="checkbox" class="country_cb">country</input>
<input checked type="checkbox" class="state_cb">state</input>
<input checked type="checkbox" class="city_cb">city</input>
</div>
<table class="table table-hover table-condensed" id="stationresult">
<tr>
<th>index</th>
<th class="stationid">station_id</th>
<th class="stationname">station_name</th>
<th class="zipcode">zipcode</th>
<th class="headendid">headend_id</th>
<th class="country">country</th>
<th class="state">state</th>
<th class="city">city</th>
</tr>
</table>
<div id="headendresult_cb">
<input checked type="checkbox" class="headendid_cb">headend_id</input>
<input checked type="checkbox" class="headendname_cb">headend_name</input>
<input checked type="checkbox" class="zipcode_cb">zipcode</input>
<input checked type="checkbox" class="state_cb">state</input>
<input checked type="checkbox" class="location_cb">location</input>
</div>
<table class="table table-hover table-condensed" id="headendresult">
<tr>
<th>index</th>
<th class="headendid">headend_id</th>
<th class="headendname">headend_name</th>
<th class="zipcode">zipcode</th>
<th class="state">state</th>
<th class="location">location</th>
</tr>
</table>
<div class="alert alert-info navbar-fixed-bottom" id="summary">

</div>
</body>
</html>
