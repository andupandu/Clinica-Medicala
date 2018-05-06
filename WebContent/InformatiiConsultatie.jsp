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
<link rel="stylesheet" type="text/css" href="Styles/Style.css">
<script src="Styles/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/js/bootstrap-datepicker.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/locales/bootstrap-datepicker.ro.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pagina administrator</title>
</head>
<%String msg=(String)request.getAttribute("msg");%>
<body>
<jsp:include page="indexAdmin.jsp" />
	<div id="right">
<h1>Cere o programare</h1>
<center>
<form name="consultatie" action="ProgramariConsultatie" method="post">
<table>
 <tr>
 <td>
 Cauta pacient:<input type="text" name="cnpcautat" id="cnpcautat" class=" form-control"  placeholder="Insereaza CNP-ul pacientului">
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
<td>Cnp:<input type="text" name="cnp" id="cnp" class=" form-control"></td>
</tr>
<tr style="display:none" id="trdatanasterii">
<td>Data Nasterii:<input type="text" name="dataNasterii" id="dataNasterii" class=" datepicker form-control">
</td>
</tr>
<tr style="display:none" id="trpacient">
<td>Pacient:<input type="text" name="pacient" id="pacient" class=" form-control" readonly></td>
</tr>
<tr style="display:none">
<td><input type="text" name="pacientcod" id="pacientcod" class=" form-control"></td>
</tr>
<tr>
<td>Specialitate:<br><select id="spec" name="spec" class="custom-select" onchange="fillMedicSelect();ReinitializeDatePicker()">
<% for(Specialitate spec:DbOperations.getSpecializari()){%>
<option value="<%=spec.getDenumire()%>"><%=spec.getDenumire() %></option>
<%} %></select></td>

</tr>
<tr>
<td>Medic:<br><select id="medic" name="medic" class="custom-select" onchange="onChangeMedic()">
</select></td>
</tr>


<tr>
<td>Motiv Consultatie:<br><select id="serviciu" name="serviciu" class="custom-select" onchange="ReinitializeDatePicker();getOreDisponibile()">
</select></td>
</tr>
<tr>
<td>
	Data:<br><input id="data" name="data" class="data"  data-date="" data-date-format="dd/mm/yyyy hh:ii"  data-link-format="yyyy-mm-dd hh:ii" onchange="getOreDisponibile()">
      </td>      			
</tr>
<tr>
<td>Ora:<br><select id="ora" name="ora" class="custom-select">
</select></td>
</tr>
<tr>
<td>Detalii Consultatie:<br><textarea id="detalii" name="detalii" class="form-control"></textarea></td>
</tr>

<tr>
<td><br><input type="submit" class="btn btn-outline-secondary" id="continua" name="continua" value="Programeaza"></td>
</tr>
</table>
</form>
</center>
</div>
</body>
</html>
<script>


function fillMedicSelect(){
	var spec=document.getElementById("spec").value;
	var selectMedic=document.getElementById("medic");
	<%for(Specialitate specialitate:DbOperations.getSpecializari()){%>
	if("<%=specialitate.getDenumire()%>"==spec){
		selectMedic.innerHTML="<%for(Medic m:DbOperations.getMediciPentruConsultatii(specialitate.getCod())){%><option value='<%=m.getId()%>'><%=m.getNume()+" "+m.getPrenume()%></option><%}%>";
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
	var cnp=document.getElementById("cnpcautat");
	if(cnp.value==""){
		alert("Introduceti CNP-UL!!");
		return;
	}
	$.post("ProgramariConsultatie",
	        {
			  metoda:"detalii",
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
	        		document.getElementById("trdatanasterii").style.display="none";
	        		document.getElementById("trcnp").style.display="none";
	        	}
	        	else{
	        		alert(response.valid);
	        		document.getElementById("trnume").style.display="block";
	        		document.getElementById("trprenume").style.display="block";
	        		document.getElementById("trtelefon").style.display="block";
	        		document.getElementById("tremail").style.display="block";
	        		document.getElementById("trdatanasterii").style.display="block";
	        		document.getElementById("trcnp").style.display="block";
	        		document.getElementById("trpacient").style.display="none";

	        	}
	        });
}
function InitializeDatepicker(){
$('.data').datepicker({
	startDate: "today",
	endDate: '+1m',
    changeyear:false,
	daysOfWeekDisabled: "0,6",
	format:"dd/mm/yyyy",
	 beforeShowDay: function (date){
		 var response;
		 var medic=document.getElementById("medic").value;
		 var serviciu=document.getElementById("serviciu").value;
		 $.ajax({
		        type: "POST",
		        url: "ProgramariConsultatie",
		        data:{
		        	metoda:"data",
		        	data: date,
		        	codMedic:medic,
		        	serviciu:serviciu
		        },
		        async: false,
		        success: function(results) {
		        	response=JSON.parse(results);
		        }
		        
		    });
		
		 	return{
	        	classes:response.color,
	        	enabled:response.valid
	        }
       }
});

}

function getOreDisponibile(){
	var data=$('.data').datepicker( "getDate" );
	 var medic=document.getElementById("medic").value;
	 var serviciu=document.getElementById("serviciu").value;
	//if(cnp.value==""){
	//	alert("Introduceti CNP-UL!!");
	//	return;
	//}
	$.post("ProgramariConsultatie",
	        {
			  metoda:"ora",
	          data:data,
	          codMedic:medic,
	          serviciu:serviciu
	        },
	        function(data,status){
	        	var response = JSON.parse(data);
	        	var selectOra=document.getElementById("ora");
					selectOra.innerHTML="";
        	response.forEach(ora=>
        		selectOra.innerHTML+="<option value="+ora+">"+ora+"</option>"	
	        );
	        })
}

function ReinitializeDatePicker(){
	$('.data').datepicker('update');
}
function onChangeMedic(){
	fillMotivCons();
	ReinitializeDatePicker();
	getOreDisponibile();
}
if(document.getElementById("serviciu").value != null)
	InitializeDatepicker();
	

var msj="<%=msg%>";
if(msj!="null")
	alert(msj);
$( function() {
    $( '#dataNasterii' ).datepicker();})
  
</script>