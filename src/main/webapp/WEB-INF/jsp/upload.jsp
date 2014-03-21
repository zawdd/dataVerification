<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="./js/ajaxfileupload.js"></script>  
<script type="text/javascript" src="./js/bootstrap.min.js"></script> 
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" /> 
<script>
 function ajaxFileUpload(){
	var fieldValue = $("#file").val();
	
	if (fieldValue == null || fieldValue == "") {
		alert("please browse a file to upload!");
		return;
	}
	
	$("#loading").ajaxStart(function(){$(this).show();}).ajaxComplete(function(){$(this).hide();});  
	
	$.ajaxFileUpload({ 
        url:'/fileUpload',  
        secureuri:false,                           
        fileElementId:'file',               
        dataType:'json',                            
        success: function (data, status)
        {
            if(typeof(data.error) != 'undefined')
            {
                if(data.error != '')
                {
                    alert(data.error);
                }else
                {
                    alert(data.msg);
                }
            }
        },
        error: function (data, status, e)
        {
            alert(e);
        }
    });  	
	
} 
</script>
 
<title>xml upload</title>

</head>
<body>

<ul class="nav nav-tabs">
  <li><a href="dataverify.html">Datebase</a></li>
  <li class="active" ><a href="upload.html">Xmltv</a></li>
</ul>
<p class="text-info">Please upload a zip file</p>
<!-- <div id="result"></div>  
<form action="" method="POST" enctype="multipart/form-data">
<input type="file" id="file" name="file"/> 
 <img id="loading" style="display: none;" src="/img/loading.gif"><strong>please wait...</strong>
<input type="button" value="upload" onclick="ajaxFileUpload()"/>
</form> -->
<form method="POST" action="/upload.do" enctype="multipart/form-data">  
            <input type="file" id="file" name="file"/>  
            <input type="submit"  class="btn btn-primary" value="upload"/>  
</form>
<!--jsp:forward page="result.jsp"-->

</body>
</html>
