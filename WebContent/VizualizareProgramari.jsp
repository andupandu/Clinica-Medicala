<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
	if(session.getAttribute("tipUser")=="medic"){%>
	<jsp:include page="indexMedic.jsp" />
<%}else{%>
<script>
	window.location.href = "index.jsp?message=Nu aveti drept de intrare pe pagina solicitata!";
	</script>
	<% }%>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="Styles/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/js/bootstrap-datepicker.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/locales/bootstrap-datepicker.ro.min.js"></script>
<script src="https://momentjs.com/downloads/moment.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<script src="http://ajax.microsoft.com/ajax/jquery.validate/1.7/additional-methods.js"></script>
<%if(session.getAttribute("tipUser")=="admin"){%>
<title>Pagina administrator</title>
<%}else{
	if(session.getAttribute("tipUser")=="receptioner"){%>
<title>Pagina receptioner</title>
<%}
	}%>
</head>
<body id="gradient">
<%String msg=(String)request.getAttribute("msg");%>

	<div id="right">
	<div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
	<form method="post" action="VizualizareProgramari" id="vizualizareProgramari">
	<center>
	<fieldset>
 <legend style=text-align:center>Programarile mele</legend>
	<table>
<tr>

<td>Perioada:
<select name="perioada" id="perioada" class="form-control">
<option value="zi">Ziua curenta</option>
<option value="sapt">Saptamana Curenta</option>
<option value="luna">Luna Curenta</option>
<option value="alta">Alta perioada</option>
</select>
</td>
</tr>
<tr style="display:none;margin-left: auto;
	margin-right:auto">
<td>Data inceput:</td>
<td></td>
<td>Data sfarsit:</td>
</tr>
<tr style="display:none" id="data">
<td>
<input type="text" name="dataInceput" id="dataInceput" class=" form-control datepicker">
</td>
<td>
<input type="text" name="dataSfarsit" id="dataSfarsit" class=" form-control datepicker">
</td>
</tr>
<tr>
<td><br><input type="submit" class="btn btn-secondary" id="creaza" name="creaza" value="Afiseaza programari" onclick="return VerificaData()"></td>
</tr>
	</table>
	</fieldset>
	</center>
	</form>
		<br>
		<form id="informatii">
		<%
			List<Consultatie> consultatii = (List<Consultatie>) request.getAttribute("consultatii");

			if (consultatii != null) 
				if(!consultatii.isEmpty()){
			
				
		%>
		<table class="table" style="width: auto" align="center">
			<tr>
				<th>Pacient</th>
				<th>Serviciu</th>
				<th>Data</th>
				<th>Ora</th>
				<th>Detalii</th>
			</tr>

			<%
					for (Consultatie c : consultatii)
					{
			%>

			<tr>
					<td>
				<input type="text" name="pacient" id="pacient"
					class=" form-control" value="<%=c.getPacient()%>" disabled></td>
				<td><input type="text" name="serviciu" id="serviciu"
					class=" form-control" value="<%=c.getTipConsutatie()%>" disabled></td>
				<td><input type="text" id="data" name="data"
					class=" form-control" value=<%=c.getData()%> disabled></td>
						<td><input type="text" id="ora" name="ora"
					class=" form-control" value=<%=c.getOraInceput()%> disabled></td>
				<td><input type="text" name="detalii" id="detalii"
					class=" form-control" value="<%=c.getDetalii()%>" disabled></td>
			</tr>
			<%
					}
			%>
		</table>
		<%
			}else{%>
				<div class="alert alert-info alert-dismissible fade show" role="alert" id="mesaj">
				<center>Nu s-a gasit nicio programare !</center>
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>
			<%}
		
		%>
		</form>
	</div>
</body> 

<script>
$(document).ready(function(){
$('#dataInceput').datepicker({ 
	   format: "dd/mm/yyyy",
	   language:"ro",
	   endDate:"+3m",
	   startDate:"today",
	   onSelect: function(selected) {
	          $("#dataSfarsit").datepicker("option","minDate", selected)
	   }
	   });
	   
	   $('#dataSfarsit').datepicker({ 
	   format: "dd/mm/yyyy",
	   language:"ro",
	   endDate:"+3m",
	   startDate:"today",
	   onSelect: function(selected) {
	          $("#dataInceput").datepicker("option","maxDate", selected)
	          }
	   });
});
	  
		
	   $('#perioada').change(function(){
		   var data=new Date();
		   var primaZiSapt=moment(moment().startOf('week').toDate()).add(1,'days').toString();
		   var ultimaZiSapt=moment(moment().endOf('week').toDate()).add(1,'days').toString();
		   if($('#perioada').val()=='zi'){
			   $('#dataInceput').val(moment(data).format("YYYY-MM-DD"));
			   $('#dataSfarsit').val(moment(data).format("YYYY-MM-DD"));
		   }
			   else if($('#perioada').val()=='luna'){
				   $('#dataInceput').val(moment(new Date(data.getFullYear(), data.getMonth(), 1)).format("YYYY-MM-DD"));
				   $('#dataSfarsit').val(moment(new Date(data.getFullYear(), data.getMonth() + 1, 0)).format("YYYY-MM-DD"));
			   }
			   else if($('#perioada').val()=='sapt'){
				   $('#dataInceput').val(moment(primaZiSapt).format("YYYY-MM-DD"));
				   $('#dataSfarsit').val(moment(ultimaZiSapt).format("YYYY-MM-DD"));
			   }
			   else if($('#perioada').val()=='alta'){
				   $('#dataInceput').val('');
				   $('#dataSfarsit').val('');
				   $('#data').show();
				 
			   }
	   })
	    function VerificaData(){
		   if( $('#dataInceput').val()!=''&& $('#dataSfarsit').val()!=''){
				  if($('#dataInceput').datepicker("getDate")>$('#dataSfarsit').datepicker("getDate")){
					  alert("Specificati corect data de inceput si de sfarsit!");
					  return false;
				  }
			  }
		   if($('#perioada').val()=='alta'){
			   $('#dataInceput').val(moment($('#dataInceput').datepicker("getDate")).format("YYYY-MM-DD"));
			   $('#dataSfarsit').val(moment($('#dataSfarsit').datepicker("getDate")).format("YYYY-MM-DD"));
		   }
		 
		  return true;
		   }
</script>
</html>