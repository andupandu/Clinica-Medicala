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
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/js/bootstrap-datepicker.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/locales/bootstrap-datepicker.ro.min.js"></script>
 <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<script src="http://ajax.microsoft.com/ajax/jquery.validate/1.7/additional-methods.js"></script>
</head>

	<% List<String> zile =(List<String>)DbOperations.getZileProgramLucruMedic((String)session.getAttribute("idMedic"));%>
<div id="right">
<%if(zile.isEmpty()){%>
<div class="alert alert-info alert-dismissible fade show" role="alert" id="mesaj">
				<center>Programul dvs de lucru nu este inca stabilit!Daca doriti sa doriti sa adaugati click <a href="ZileProgramMedic">aici</a></center>
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>
<%}else{ %>
<div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
<form method="post" action="ModificaProgramMedic"  id="modificaprogram">
<center>
<fieldset>
<legend>Modificare program</legend>
<table align="center">

<tr>
<td>
Ziua:<br><select id="zi" name="zi" class="custom-select">
<%
	for (String zi: zile) {
	%>
				<option value="<%=zi%>"><%=zi%></option>
				<%
					}
				%>
			</select></td></tr>
			<tr><td>
Ora inceput:<input type="time" name="orainceput" id="orainceput" class="datepicker form-control" min="00:00" max="20:00"></td></tr>
<tr><td>
Ora sfarsit:<input type="time" name="orasfarsit" id="orasfarsit" class="datepicker form-control" min="00:00" max="20:00">
</td></tr>
<tr><td>
<input type="button" class="btn btn-secondary"  id="modifica"  name="modifica" value="Modifica program" onClick="VerifyDate()">


<!-- Modal -->
<div class="modal fade bd-example-modal-lg" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
       In urma schimbarii programului urmatoarele consultatii voi fi anulate.
         <table class="table" id="tableContent">	  
		 </table>
       Doriti sa continuati?
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="window.location.reload()">Nu</button>
        <button type="button" class="btn btn-primary" onclick="document.getElementById('modificaprogram').submit()">Da</button>
      </div>
    </div>
  </div>
</div>

</td>
</tr>
</table>
</fieldset>
</center>
</form>
<%} %>
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
	 var zi = document.getElementById("zi").value;
	 $.post("ModificaProgramMedic",
		        {	tip:"verificazi",
		        	zi:zi
		        },
		        function(data,status){
		        	 var response = JSON.parse(data);
		        	 if(!response.areConsultatii){
		        		 alert()
		        		 document.getElementById('modificaprogram').submit();
		        		 return;
		        	 }
		        	 var table = document.getElementById("tableContent");
		        	 table.innerHTML=" <tr> <th>Pacient</th><th>Tip Consultatie</th><th>Ora</th><th>Data</th></tr>";
		        	 response.consultatii.forEach(consultatie => { 
		        		var row = table.insertRow(-1);
		        		var cell1 = row.insertCell(0);
		        		var cell2 = row.insertCell(1);
		        		var cell3 = row.insertCell(2);
		        		var cell4 = row.insertCell(3);
		        		
		        		cell1.innerHTML = consultatie.pacient;
		        		cell2.innerHTML = consultatie.serviciu;
		        		cell3.innerHTML = consultatie.oraConsS;
		        		cell4.innerHTML = consultatie.data;
		        	 });
		        	 
		        	 $('#exampleModal').modal("toggle");
		        });
 }
 $( document ).ready(function() {
		document.getElementById("orainceput").defaultValue = "08:00";
		document.getElementById("orasfarsit").defaultValue = "16:00";

	})
  </script>
</html>