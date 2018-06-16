<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@page import="pkg.Utils.*"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<%if(session.getAttribute("tipUser")=="admin"){%>
<title>Pagina administrator</title>
<%}else
	if(session.getAttribute("tipUser")=="receptioner"){%>
<title>Pagina receptioner</title>
<%}
	else
	if(session.getAttribute("tipUser")=="medic"){%>
<title>Pagina medic</title>
<%}
	%>
<%
String msg=(String)request.getAttribute("msg");%>
<body id="gradient">
<% 
if(session.getAttribute("tipUser")=="admin"){%>
<jsp:include page="indexAdmin.jsp" />
<%}else
	if(session.getAttribute("tipUser")=="receptioner"){%>
	<jsp:include page="indexReceptioner.jsp" />
<%}else
	if(session.getAttribute("tipUser")=="medic"){%>
	<jsp:include page="indexMedic.jsp" />
<%}
else{
	%><script>
	window.location.href = "index.jsp?message=Nu aveti drept de intrare pe pagina solicitata!";
	</script>
<% 
}%>

<script src="https://momentjs.com/downloads/moment.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="Styles/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"/>



</head>

	
<div id="right">
<div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
<form method="post" action="AdaugaZiLibera"  id="adaugazilibera">
<center>
<fieldset>
<legend>Program de lucru</legend>
<table align="center">
<tr>
<td>
Ziua:<br><select id="spec" name="spec"class="custom-select">
<%
	List<String> zile = (List<String>)request.getAttribute("zile");
	for (String zi : zile) {
	%>
				<option value="<%=zi%>"><%=zi%></option>
				<%
					}
				%>
			</select></td></tr>
			<tr><td>
Ora inceput:<input type="time" name="orainceput" id="orainceput" class="datepicker form-control" ></td></tr>
<tr><td>
Ora sfarsit:<input type="time" name="orasfarsit" id="orasfarsit" class="datepicker form-control">
</td></tr>
<tr><td>
<input type="button" class="btn btn-secondary"  id="adauga"  name="adauga" value="Adauga program" onClick="VerifyDate()">
</td>
</tr>
</table>
</fieldset>
</center>
</form>
</div>

</body>
<script>
var msjInsert="<%=msg%>";
if(msjInsert!="null"){
	 var mesaj=document.getElementById("mesaj");
	 mesaj.innerHTML=msjInsert+"<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
 document.getElementById("mesaj").style.display="block";
}
	

 function VerifyDate(){
	 var data = document.getElementById("data1").value;
	 $.post("AdaugaZiLibera",
		        {	tip:"verificazi",
		        	data:data
		        },
		        function(data,status){
		        	 var response = JSON.parse(data);
		        	 if(!response.areConsultatii){
		        		 alert()
		        		 document.getElementById('adaugazilibera').submit();
		        		 return;
		        	 }
		        	 var table = document.getElementById("tableContent");
		        	 table.innerHtml=" <tr> <th>Pacient</th><th>Tip Consultatie</th><th>Ora</th></tr>";
		        	 response.consultatii.forEach(consultatie => { 
		        		var row = table.insertRow(-1);
		        		var cell1 = row.insertCell(0);
		        		var cell2 = row.insertCell(1);
		        		var cell3 = row.insertCell(2);
		        		
		        		cell1.innerHTML = consultatie.pacient;
		        		cell2.innerHTML = consultatie.serviciu;
		        		cell3.innerHTML = consultatie.oraInceput;
		        	 });
		        	 
		        	 $('#exampleModal').modal("toggle");
		        });
 }
  </script>
</html>