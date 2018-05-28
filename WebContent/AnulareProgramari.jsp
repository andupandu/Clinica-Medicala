<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@page import="pkg.Utils.*"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pagina administrator</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
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
<%List<Consultatie> consultatii=(List<Consultatie>)request.getAttribute("consultatii");
String msg=(String)request.getAttribute("msg");%>
<body id="gradient">
<jsp:include page="indexAdmin.jsp" />
<div id="right">
<div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
<form method="post" action="AnuleazaProgramare" onsubmit="return Verif()" id="anulareProg">
<center>
<fieldset>
<legend>Anulare programari</legend>
<table align="center">
<tr>
<td>
Cauta medic:<select id="medic" name="medic" class=" form-control">
<%for(Medic m :DbOperations.getMedici()){%>
<option value="<%=m.getId()%>"><%=m.getNume()+" "+m.getPrenume() %></option> <%} %></select><br>
Data:<input type="text" name="data" id="data" class="datepicker form-control" readonly>
<input type="hidden" name="data1" id="data1">
<input type="submit" class="btn btn-secondary"  id="cauta" name="cauta" value="Afiseaza programari">
</td>
</tr>
</table>
</fieldset>
</center>
</form>
<% if(consultatii!=null)
if(!consultatii.isEmpty()){%>
<table class="table" style="width: auto" align="center">
			<tr>
				<th>Data</th>
				<th>Ora</th>
				<th>Pacient</th>
				<th>Tip Consultatie</th>
				<th>Status</th>
				<th><input type="button" class="btn btn-secondary"
						id="anuleaza" name="anuleaza" value="Anuleaza toate programarile"
						onclick="anuleazaProg('1', 'anularetotala')"></th>
				
			</tr>

			<%
				int i = 0;
					for (Consultatie cons : consultatii) {
			%>

			<tr id="consultatie<%=i%>">
				<td><input type="text"
					class=" form-control" size="10" value="<%=DateUtil.changeDateFormat(cons.getData())%>" disabled>
					<input type="hidden" name="data" id="data"
					class=" form-control" size="10" value="<%=cons.getData()%>" disabled>
					<input type="hidden" name="medic" id="medic"
					class=" form-control" size="10" value="<%=cons.getMedic()%>" disabled></td>
				<td><input type="text" name="ora" id="ora"
					class=" form-control" size="5" value="<%=cons.getOraInceput()%>" disabled></td>
				<td><input type="text" name="pacient" id="pacient"
					class=" form-control" value="<%=cons.getPacient()%>" disabled></td>
				<td><input type="text" id="tipCons" name="tipCons"
					class=" form-control"  size="40" value="<%=cons.getTipConsutatie()%>" disabled></td>
				<td><input type="text" id="status" name="status"
					class=" form-control"  size="10" value="<%=cons.getStatus()%>" disabled></td>
		<td> <input
					type="button" class="btn btn-secondary" id="anuleaza"
					name="anuleaza" value="Anuleaza"
					onclick="anuleazaProg(<%=i%>, 'anulare')"></td>
			</tr>
			<%
				i++;
					}
			%>
</table>
<%
			}
		%>
</div>
</body>
<script>
$( function() {
    $( "#data" ).datepicker();})
   $('#data').datepicker({ format:"dd/mm/yyyy",
	   						language:"ro"})   
   
	   $('#cauta').click(function(){
    $('#data1').val(moment($('#data').datepicker("getDate")).format("YYYY-MM-DD"));
}); 
	      function anuleazaProg(i, verify){
 	var element = document.getElementById("consultatie"+i);
 	var pacient = element.querySelector("#pacient");
	var medic=element.querySelector("#medic");
	var data=element.querySelector("#data");
	var status=element.querySelector("#status");
	if(verify=="anulare"){
	if(status.value=="anulat"){
		alert("Programarea este deja anulata");
	return false;
	}
	}
	if(confirm("Sunteti sigur ca doriti sa anulati programarea?")){
		
	
 		$.post("AnuleazaProgramare",
 		        {
		          pacient:pacient.value,
 		          verif:verify,
 		          medic:medic.value,
 		          data:data.value
 		          
 		        },
 		        function(data,status){
 		        	window.location.href = "AnulareProgramari.jsp?message="+data;
 		        });
 	}
}
function Verif(){
	if(!$("#anulareProg").valid()){
	return false;
	}else
		return true;
	
}

 
 $(document).ready(function(){
		$('#anulareProg').validate({
			rules:{
				data:{
					required:true
					
				}
				
			}
	})
	if(window.location.href.indexOf("message=") > 0)
	{	
		var message = window.location.href.split("message=")[1].replace(/%20/g," ");
		var mesaj=document.getElementById("mesaj");
	    mesaj.innerHTML=message+"<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
	    document.getElementById("mesaj").style.display="block";
	}
		})
	
	
	jQuery.extend(jQuery.validator.messages, {
    required: "Campul este obligatoriu.",
    lettersonly: "Va rog inserati doar litere",
});
  </script>
</html>
