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
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="Styles/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"/>
 
</head>
<%List<Consultatie> consultatii=(List<Consultatie>)request.getAttribute("consultatii");
String msg=(String)request.getAttribute("msg");%>
<body>
<jsp:include page="indexAdmin.jsp" />
<div id="right">
<table align="center">
<tr>
<td>
<form method="post" action="AnuleazaProgramare" onsubmit="return Verif()">
Cauta medic:<input type="text" id="medic" name="medic" class=" form-control"><br>
Data:<input type="text" name="data" id="data" class="datepicker form-control"><br>
<input type="hidden" name="data1" id="data1" ><br>
<input type="submit" class="btn btn-outline-secondary" value="Afiseaza programari"><br>
</form>
</td>
</tr>
<% if(consultatii!=null)
if(!consultatii.isEmpty()){%>
<table class="table" style="width: auto" align="center">
			<tr>
				<th>Data</th>
				<th>Ora</th>
				<th>Pacient</th>
				<th>Tip Consultatie</th>
				<th>Status</th>
				<th><input type="button" class="btn btn-outline-secondary"
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
					type="button" class="btn btn-outline-secondary" id="anuleaza"
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
   $('#data').datepicker({ dateFormat: 'dd/mm/yy',
	   altField: "#data1",
	      altFormat: "yy-mm-dd"})
	      
	      
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
 		        	alert(data);
 		        	location.reload(true);
 		        });
 	}
}
function Verif(){
	if(document.getElementById("medic").value==''||document.getElementById("data").value==''){
	alert("Introduceti medicul si data");
	return false;
	}else
		return true;
	
}
 var msj="<%=msg%>";
 if(msj!="null")
 	alert(msj);
  </script>
</html>
