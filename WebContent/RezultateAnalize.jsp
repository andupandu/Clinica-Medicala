<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%List<Specialitate> specialitati=DbOperations.getSpecializari();%>
<html>
<head>
<% 
if(session.getAttribute("tipUser")=="admin"){%>
<jsp:include page="indexAdmin.jsp" />
<%}else{
	if(session.getAttribute("tipUser")=="receptioner"){%>
	<jsp:include page="indexReceptioner.jsp" />
<%}else{%><script>
window.location.href = "index.jsp?message=Nu aveti drept de intrare pe pagina solicitata!";
</script>
<% }
}%>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="Styles/Style.css">
<script src="Styles/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/js/bootstrap-datepicker.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/locales/bootstrap-datepicker.ro.min.js" ></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<script src="http://ajax.microsoft.com/ajax/jquery.validate/1.7/additional-methods.js"></script>
<script src="https://momentjs.com/downloads/moment.js"></script>

<%if(session.getAttribute("tipUser")=="admin"){%>
<title>Pagina administrator</title>
<%}else{
	if(session.getAttribute("tipUser")=="receptioner"){%>
<title>Pagina receptioner</title>
<%}
	}%>
</head>
<%String msg=(String)request.getAttribute("msg");%>

<body id="gradient">
	<div id="right">
		<div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
<center>
<fieldset>
 <legend style=text-align:center>Cere o programare</legend>
<form id="cauta" action="RezultatAnalize" method="post">
<table>
 <tr>
 <td>
Cnp pacient:<input type="text" name="cnpcautat" id="cnpcautat" class=" form-control"  placeholder="Insereaza CNP-ul pacientului">
 <input type="button" onclick="searchPacient();" value="Continua" class="btn btn-secondary">
 </td>
 </tr>
 </table>
</form>
 <div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesajCnp">
</div>
 <form id="rezultat" action="RezultatAnalize" method="post"> 
 <table>
 <tbody align=center>
<tr style="display:none" id="trpacient" >
<td style="text-align:center">Pacient:<input type="text" name="pacient" id="pacient" class=" form-control" readonly></td>
</tr>
<tr style="display:none">
<td><input type="text" name="pacientcod" id="pacientcod" class=" form-control"></td>
</tr>
<tr style="display:none" id="tranalize">
<td>Analize:<br><select id="analize" name="analize" class="custom-select" >
</select></td>

</tr>
<tr style="display:none" id="trvaloare">
<td>Valoare rezultat:<br><input type="text" name="valoare" id="valoare" class=" form-control"></td>
</tr>
<tr style="display:none" id="trinterpretare">
<td>
	Interpretare:<br><textarea rows="4" cols="50" id="interpretare" name="interpretare">
</textarea>
      </td>      			
</tr>
<tr style="display:none" id="trbuton">
<td><br><input type="submit" class="btn btn-secondary" id="continua" name="continua" value="Adauga rezultat"></td>
</tr>
</tbody>
</table>
</form>
</fieldset>
</center>
</div>
</body>
<script>

function searchPacient(){
	var cnp=document.getElementById("cnpcautat");
	
	if($("#cauta").valid()){
	$.post("RezultatAnalize",
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
	        		document.getElementById("tranalize").style.display="block";
	        		document.getElementById("trinterpretare").style.display="block";
	        		document.getElementById("trvaloare").style.display="block";
	        		document.getElementById("trbuton").style.display="block";
	        		var selectAnalize=document.getElementById("analize");
					selectAnalize.innerHTML="";
        		(response.analize).forEach(analize=>
        		selectAnalize.innerHTML+="<option value="+analize+">"+analize+"</option>")
	        	}
	        	else{
	        		 var mesajCnp=document.getElementById("mesajCnp");
	        		 mesajCnp.innerHTML="Nu exista niciun pacient in baza de date cu acest Cnp !<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
	        		 document.getElementById("mesajCnp").style.display="block";
	        		
	        	}
	        });
}
}


var msj="<%=msg%>";
if(msj!="null"){
	 var mesaj=document.getElementById("mesaj");
	 mesaj.innerHTML=msj+"<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
 document.getElementById("mesaj").style.display="block";
}
</script>

</html>