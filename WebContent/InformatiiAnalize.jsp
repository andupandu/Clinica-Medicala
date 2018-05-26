<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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
<body style="background-color:#98B9F2">
<%String msg=(String)request.getAttribute("msg"); %>
<jsp:include page="indexAdmin.jsp" />
	<div id="right">

<center>
<form name="analize" action="ProgramariAnalize" method="post">
<fieldset>
 <legend style=text-align:center>Cere o programare pentru analize</legend>
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
<td>Data nasterii:<input type="text" name="dataNasterii" id="dataNasterii" class=" datepicker form-control">
<input type="hidden" name="data1" id="data1"></td>
</tr>
<tr style="display:none" id="trpacient">
<td>Pacient:<input type="text" name="pacient" id="pacient" class="form-control" readonly></td>
</tr>
<tr style="display:none">
<td><input type="text" name="pacientcod" id="pacientcod" class=" form-control"></td>
</tr>

<tr>
<td>Data:<br><input type="text" name="data" id="data" class=" datepicker form-control" onchange="getOreDisp();">
<input type="hidden" name="dataprog" id="dataprog"></td>
</tr>
<tr>
<td>Ora:<br><select id="ora" name="ora" class="custom-select">
</select></td>
</tr>
<br>
<tr>
<td>Analize:<br><select id="analize" name="analize" class="custom-select" multiple style="height: 50pt"><%for(Analiza analiza :DbOperations.getAnalize()){ %>
<option value="<%=analiza.getCod()%>"><%=analiza.getDenumire() %></option>
<%} %></select></td>
</tr>

<tr>
<td><br><input type="submit" class="btn btn-outline-secondary" id="continua" name="continua" value="Programeaza"></td>
</tr>

</table>
</fieldset>
</form>
</center>
</div>
</body>
</html>
<script>
function searchPacient(){
	var cnp=document.getElementById("cnp");
	if(cnp.value==""){
		alert("Introduceti CNP-UL!!");
		return;
	}
	$.post("ProgramariAnalize",
	        {
			metoda:"detalii",
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
	        		document.getElementById("trcnp").style.display="none";
	        		document.getElementById("trdataNasterii").style.display="none";
	        	}
	        	else{
	        		document.getElementById("trcnp").style.display="block";
	        		document.getElementById("trdataNasterii").style.display="block";
	        		document.getElementById("trnume").style.display="block";
	        		document.getElementById("trprenume").style.display="block";
	        		document.getElementById("trtelefon").style.display="block";
	        		document.getElementById("tremail").style.display="block";
	        		document.getElementById("trpacient").style.display="none";
	        		document.getElementById("pacient").value="";

	        	}
	        });
}
function getOreDisp(){
	var data=document.getElementById("dataprog");
	$.post("ProgramariAnalize",
	        {
			metoda:"ore",
	          dataAnalize:data.value
	        },
	        function(data,status){
	        	alert(data);
	        	if(data!=null){
	        	var response = JSON.parse(data);
	        	alert(response);
	        	var selectOra=document.getElementById("ora");
				selectOra.innerHTML="";
    	response.forEach(ora=>
    		selectOra.innerHTML+="<option value="+ora+">"+ora+"</option>"	
        );
	        	}else{
	        		alert("Nu mai sunt ore disponibile pentru aceasta zi.Va rugam selectati o alta zi!")
	        	}
	        });
}
function Disable() {
	 
	  var date = new Date();
	   var day=date.getTime();
	return day;
	}
var msj="<%=msg%>";
if(msj!="null")
	alert(msj);
	
	 $(function() {
	 $( "#data" ).datepicker({
	 minDate: 0
	 });
	 });
	 
	 $(function() {
		 $( "#dataNasterii" ).datepicker();
		 });
	 $('#data').datepicker({ dateFormat: 'dd/mm/yy',
		   altField: "#dataprog",
		      altFormat: "yy-mm-dd"})
	 $('#dataNasterii').datepicker({ dateFormat: 'dd/mm/yy',
		   altField: "#data1",
		      altFormat: "yy-mm-dd"})</script>