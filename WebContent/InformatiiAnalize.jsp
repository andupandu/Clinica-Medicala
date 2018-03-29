<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%><%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="Styles/bootstrap.min.js"></script>
 <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pagina administrator</title>
</head>
<body>
<body>
<h1>Cere o programare pentru analize</h1>
<center>
<form name="analize" action="ProgramariAnalize" method="post">
<table>
<tr>
 <td>
 Cauta pacient:<input type="text" name="cnp" id="cnp" class=" form-control"  placeholder="Insereaza CNP-ul pacientului">
 <input type="button" onclick="searchPacient();" value="Cauta" class="btn btn-outline-secondary">
 </td>
 </tr>
<tr style="display:none" id="trnume">
<td>Nume:<input type="text" name="nume" id="nume" class=" form-control" ></td>
</tr>
<tr style="display:none" id="trprenume">
<td>Prenume:<input type="text" name="prenume" id="prenume" class=" form-control"></td>
</tr>
<tr style="display:none" id="tremail">
<td>Email:<input type="text" name="email" id="email" class=" form-control"></td>
</tr>
<tr style="display:none" id="trtelefon">
<td>Telefon:<input type="text" name="telefon" id="telefon" class=" form-control"></td>
</tr>
<tr style="display:none" id="trcnp">
<td>CNP:<input type="text" name="cnpNou" id="cnpNou" class=" form-control"></td>
</tr>
<tr style="display:none" id="trdataNasterii">
<td>Data nasterii:<input type="date" name="dataNasterii" id="dataNasterii" class=" form-control"></td>
</tr>
<tr style="display:none" id="trpacient">
<td>Pacient:<input type="text" name="pacient" id="pacient" class=" form-control" readonly></td>
</tr>
<tr style="display:none">
<td><input type="text" name="pacientcod" id="pacientcod" class=" form-control"></td>
</tr>

<tr>
<td>Data:<input type="text" name="data" id="data" class=" datepicker"></td>
</tr>
<tr>
<td>Ora:<input type="time" min="07:00:00" max="10:00:00" name="ora" id="ora" class=" form-control" /></td>
</tr>
<br>
<tr>
<td>Analize:<br><select id="analize" name="analize" class="custom-select" multiple="multiple"  style="height: 50pt"><%for(Analiza analiza :DbOperations.getAnalize()){ %>
<option value="<%=analiza.getCod()%>"><%=analiza.getDenumire() %></option>
<%} %></select></td>
</tr>

<tr>
<td><br><input type="submit" class="btn btn-outline-secondary" id="continua" name="continua" value="Programeaza"></td>
</tr>
</table>
</form>
</center>
</body>
<script>
function searchPacient(){
	var cnp=document.getElementById("cnp");
	if(cnp.value==""){
		alert("Introduceti CNP-UL!!");
		return;
	}
	$.post("ProgramariConsultatie",
	        {
	          cnp:cnp.value
	        },
	        function(data,status){
	        	var response = JSON.parse(data)
	        	if(response.valid==true){
	        	
	        		document.getElementById("pacient").value=response.nume+' '+response.prenume;
	        		document.getElementById("pacientcod").value=response.id;
	        		document.getElementById("trpacient").style.display="block";
	        		document.getElementById("trnume").style.display="none";
	        		document.getElementById("trprenume").style.display="none";
	        		document.getElementById("trtelefon").style.display="none";
	        		document.getElementById("tremail").style.display="none";
	        	}
	        	else{
	        		document.getElementById("trcnp").style.display="block";
	        		document.getElementById("trdataNasterii").style.display="block";
	        		document.getElementById("trnume").style.display="block";
	        		document.getElementById("trprenume").style.display="block";
	        		document.getElementById("trtelefon").style.display="block";
	        		document.getElementById("tremail").style.display="block";
	        		document.getElementById("trpacient").style.display="none";

	        	}
	        });
}

function Disable() {
	 
	  var date = new Date();
	   var day=date.getTime();
	return day;
	}
	  
	 $(function() {
	 $( "#data" ).datepicker({
	 minDate: 0
	 });
	 });</script>