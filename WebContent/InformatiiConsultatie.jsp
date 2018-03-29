<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%List<Specialitate> specialitati=DbOperations.getSpecializari();%>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="Styles/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pagina administrator</title>
</head>

<body>
<h1>Cere o programare</h1>
<center>
<form name="consultatie" action="ContinuaConsultatie" method="post">
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
<tr style="display:none" id="trpacient">
<td>Pacient:<input type="text" name="pacient" id="pacient" class=" form-control" readonly></td>
</tr>
<tr style="display:none">
<td><input type="text" name="pacientcod" id="pacientcod" class=" form-control"></td>
</tr>
<tr>
<td>Specialitate:<br><select id="spec" name="spec" class="custom-select" onchange="fillMedicSelect()">
<% for(Specialitate spec:DbOperations.getSpecializari()){%>
<option value="<%=spec.getDenumire()%>"><%=spec.getDenumire() %></option>
<%} %></select></td>

</tr>
<tr>
<td>Medic:<br><select id="medic" name="medic" class="custom-select" onchange="fillMotivCons()">
</select></td>
</tr>
<tr>
<td>Motiv Consultatie:<br><select id="serviciu" name="serviciu" class="custom-select">
</select></td>
</tr>
<tr>
<td>Detalii Consultatie:<br><textarea id="detalii" name="detalii" class="form-control"></textarea></td>
</tr>
<tr>
<td><br><input type="submit" class="btn btn-outline-secondary" id="continua" name="continua" value="Continua"></td>
</tr>
</table>
</form>
</center>
</body>
</html>
<script>

function fillMedicSelect(){
	var spec=document.getElementById("spec").value;
	var selectMedic=document.getElementById("medic");
	<%for(Specialitate specialitate:DbOperations.getSpecializari()){%>
	if("<%=specialitate.getDenumire()%>"==spec){
		selectMedic.innerHTML="<%for(Medic m:DbOperations.getMedicFromCodSpec(DbOperations.getCodSpecFromDenSpec(specialitate.getDenumire()))){%><option value='<%=m.getId()%>'><%=m.getNume()+" "+m.getPrenume()%></option><%}%>";
	}
		<%}%>
		
		fillMotivCons();
}

fillMedicSelect();

function fillMotivCons(){
	var medic=document.getElementById("medic").value;
	var selectMotiv=document.getElementById("serviciu");
	selectMotiv.innerHTML="";
	<%for(Serviciu codServ:DbOperations.getServicii()){%>
	if("<%=codServ.getCodMedic()%>"==medic){
		selectMotiv.innerHTML+="<option value='<%=codServ.getCod()%>'><%=DbOperations.getDenServiciuFromCodServiciu(codServ.getCod())%></option>";
	}
		<%}%>
}
fillMotivCons();

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
	        		alert(response.valid);
	        		document.getElementById("pacient").value=response.nume+' '+response.prenume;
	        		document.getElementById("pacientcod").value=response.id;
	        		document.getElementById("trpacient").style.display="block";
	        		document.getElementById("trnume").style.display="none";
	        		document.getElementById("trprenume").style.display="none";
	        		document.getElementById("trtelefon").style.display="none";
	        		document.getElementById("tremail").style.display="none";
	        	}
	        	else{
	        		alert(response.valid);
	        		document.getElementById("trnume").style.display="block";
	        		document.getElementById("trprenume").style.display="block";
	        		document.getElementById("trtelefon").style.display="block";
	        		document.getElementById("tremail").style.display="block";
	        		document.getElementById("trpacient").style.display="none";

	        	}
	        });
}
</script>