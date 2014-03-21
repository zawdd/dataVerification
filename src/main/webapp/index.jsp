<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"	src="./js/jquery.min.js"></script>
<script type="text/javascript"	src="./js/jquery.json-2.3.min.js"></script>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<title>data</title>

<script>
$(document).ready(function() {
	$('li a').click(function() {
		  $(this).parent().addClass('on').siblings().removeClass('on');
	});
});

</script>
</head>
<!-- <body> -->
<%-- <a href="dataverify.html">data verify</a></br>
<a href="upload.html">xml verify</a>
<jsp:forward page="dataverify.html" /> --%>

<frameset rows="50%,50%">

  <frame src="dataverify.html">
  <frame src="xmltv.html">

</frameset>

<!-- </body> -->
</html>
