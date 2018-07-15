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
<form id="cauta" action="ProgramariConsultatie" method="post">
<table>
 <tr>
 <td>
 Cauta pacient:<input type="text" name="cnpcautat" id="cnpcautat" class=" form-control"  placeholder="Insereaza CNP-ul pacientului">
 <input type="button" onclick="searchPacient();" value="Cauta" class="btn btn-secondary">
 </td>
 </tr>
 </table>
</form>
 
 <form id="consultatie" action="ProgramariConsultatie" method="post"> 
 <table>
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
<td>Cnp:<input type="text" name="cnp" id="cnp" class=" form-control" readonly></td>
</tr>
<tr style="display:none" id="trdatanasterii">
<td>Data Nasterii:<input type="text" name="dataNasterii" id="dataNasterii" class=" datepicker form-control" readonly>
<input type="hidden" id="data1" name="data1">
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
	Data:<br><input id="data" name="data" class="data"  onchange="getOreDisponibile()" readonly>
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
<td><br><input type="submit" class="btn btn-secondary" id="continua" name="continua" value="Programeaza" onclick="changeDateFormat()"></td>
</tr>
</table>
</form>
</fieldset>
</center>
</div>
</body>


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

function changeDateFormat(){
	document.getElementById("data").value=moment(document.getElementById("data").value,"DD/MM/YYYY").format("YYYY-MM-DD");
}
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
	
	if($("#cauta").valid()){
	$.post("ProgramariConsultatie",
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
	        		document.getElementById("trdatanasterii").style.display="none";
	        		document.getElementById("trcnp").style.display="none";
	        	}
	        	else{
	        		
	        		document.getElementById("trnume").style.display="block";
	        		document.getElementById("trprenume").style.display="block";
	        		document.getElementById("trtelefon").style.display="block";
	        		document.getElementById("tremail").style.display="block";
	        		document.getElementById("trdatanasterii").style.display="block";
	        		document.getElementById("trcnp").style.display="block";
	        		document.getElementById("trpacient").style.display="none";
	        		document.getElementById("pacient").value="";
	        		document.getElementById("cnp").value=document.getElementById("cnpcautat").value;
	        	}
	        });
}
}
function InitializeDatepicker(){
$('.data').datepicker({
	startDate: "+1d",
	endDate: '+3m',
    changeyear:false,
	daysOfWeekDisabled: "0,6",
	format:"dd/mm/yyyy",
	language:"ro",
	 beforeShowDay: function (date){
		 var response;
		 var medic=document.getElementById("medic").value;
		 var serviciu=document.getElementById("serviciu").value;
		 $.ajax({
		        type: "POST",
		        url: "ProgramariConsultatie",
		        data:{
		        	metoda:"data",
		        	data: moment(date,"DD/MM/YYYY").format("YYYY-MM-DD"),
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
	
	$.post("ProgramariConsultatie",
	        {
			  metoda:"ora",
	          data:moment(data,"DD/MM/YYYY").format("YYYY-MM-DD"),
	          codMedic:medic,
	          serviciu:serviciu
	        },
	        function(data,status){
	        	var response = JSON.parse(data);
	        	response[0]=response[0].substring(0,5);
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
if(msj!="null"){
	 var mesaj=document.getElementById("mesaj");
	 mesaj.innerHTML=msj+"<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
 document.getElementById("mesaj").style.display="block";
}
	
$( function() {
    $( '#dataNasterii' ).datepicker({
    	format:"dd/mm/yyyy",
    	language:"ro",
    	endDate:"+1d"
    })
    })
      $('#continua').click(function(){
    $('#data1').val(moment($('#dataNasterii').datepicker("getDate")).format("YYYY-MM-DD"));
}); 

$.validator.addMethod(
        "roCNP",
        function(value, element) {
            var check = false;
            var re = /^\d{1}\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])(0[1-9]|[1-4]\d| 5[0-2]|99)\d{4}$/;
            if( re.test(value)) {
                var bigSum = 0, rest = 0, ctrlDigit = 0;
                var control = '279146358279';
                for (var i = 0; i < 12; i++) {
                    bigSum += value[i] * control[i];
                }
                ctrlDigit = bigSum % 11;
                if ( ctrlDigit == 10 ) ctrlDigit = 1;
 
                if ( ctrlDigit != value[12] ) return false;
                else return true;
            } return false;
        }, 
        "CNP invalid"
    );
$(document).ready(
function(){
	$("#cauta").validate({
		rules:{
			cnpcautat: { 
				required: true,
				roCNP: true
			}
	
		}
	})
})
$(document).ready(
function() {
	$('#consultatie').validate({
		rules:{
			cnp: { required: true,
				roCNP: true
				},
        nume:{
			required:true,
			lettersonly: true
		},
		prenume:{
			required:true,
			lettersonly: true
		},
		email:{
			required:true,
			email:true
		},
		telefon:{
				required:true,
			verifTelefon:true
		},
		dataNasterii:{
				required:true
			}
		,
		data:{ required:true
			
		},
		ora:{
			required:true
		}
		}
		    });
		})
		
		$.validator.addMethod("verifTelefon", function (value, element) {
    return this.optional(element) || /(02|07)\d{8}$/.test(value);
}, 'Telefon invalid');
jQuery.extend(jQuery.validator.messages, {
    required: "Campul este obligatoriu.",
   	lettersonly:"Va rog inserati doar litere",
    email: "Inserati un email valid.",
    equalTo: "Please enter the same value again.",
    accept: "Please enter a value with a valid extension.",
    email:"Email invalid",
    cnp:"CNP invalid",
    cnpcautat:"CNP invalid"
});
</script>

</html>