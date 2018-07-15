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

	
<div id="right">
<div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
<form method="post" action="AdaugaZiLibera"  id="adaugazilibera">
<center>
<fieldset>
<legend>Zile libere</legend>
<table align="center">
<tr>
<td>
Data:<input type="text" name="data" id="data" class="datepicker form-control" readonly>
<input type="hidden" name="data1" id="data1">
<input type="button" class="btn btn-secondary"  id="adauga"  name="adauga" value="Adauga zi libera" onClick="VerifyDate()">


<!-- Modal -->
<div class="modal fade bd-example-modal-lg" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
       In aceasta zi aveti programate urmatoarele consultatii:
         <table class="table" id="tableContent">	  
		 </table>
       Doriti sa continuati operatiunea si sa anulati consultatiile din aceasta zi?
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="window.location.reload()">Nu</button>
        <button type="button" class="btn btn-primary" onclick="document.getElementById('adaugazilibera').submit()">Da</button>
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
</div>

</body>
<script>
$( function() {
    $( "#data" ).datepicker();})
   $('#data').datepicker({ format:"dd/mm/yyyy",
	   						language:"ro",
	   						startDate: '+1d',
	   						endDate:'+3m'})   
   
	   $('#adauga').click(function(){
		   $('#data1').val(moment($('#data').datepicker("getDate")).format("YYYY-MM-DD"));
}); 
	     


var msjInsert="<%=msg%>";
if(msjInsert!="null"){
	 var mesaj=document.getElementById("mesaj");
	 mesaj.innerHTML=msjInsert+"<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
 document.getElementById("mesaj").style.display="block";
}

 $(document).ready(function(){
		$('#adaugazilibera').validate({
			rules:{
				data:{
					required:true
					
				}
				
			}
	})
		})
	
	
	jQuery.extend(jQuery.validator.messages, {
    required: "Campul este obligatoriu.",

});

 function VerifyDate(){
	 $('#data1').val(moment($('#data').datepicker("getDate")).format("YYYY-MM-DD"));
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