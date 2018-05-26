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
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<script src="http://ajax.microsoft.com/ajax/jquery.validate/1.7/additional-methods.js"></script>

<script>
$(document).ready(function(){
	$('#formContMedic').validate({
		rules:{
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
			required:true
		
		},
		dataNasterii:{
			required:true
		}
	}
})
})
</script>

<script>
jQuery.extend(jQuery.validator.messages, {
    required: "Campul este obligatoriu.",
   lettersonly:"Va rog inserati doar litere",
    email: "Inserati un email valid.",
    equalTo: "Please enter the same value again.",
    accept: "Please enter a value with a valid extension.",
    email:"Va rog inserati un email valid",
    
});
</script>

<title>Pagina administrator</title>
</head>
<body style="background-color:#98B9F2">
<%String msg=(String)request.getAttribute("msg");%>
<jsp:include page="indexAdmin.jsp" />


	<div id="right">

	<form method="post" action="ContMedic" id="formContMedic">
	<fieldset>
 <legend style=text-align:center>Date medic</legend>
	<table align="center">	
	<tr>
 <td>
<tr>
<td>Nume:<input type="text" name="nume" id="nume" class=" form-control" ></td>
</tr>
<tr>
<td>Prenume:<input type="text" name="prenume" id="prenume" class=" form-control"></td>
</tr>
<tr><td>
Specialitate:<br><select id="spec" name="spec"class="custom-select">
<%
	List<Specialitate> specializari = (List<Specialitate>) DbOperations.getSpecializari();
	for (Specialitate spec : specializari) {
	%>
				<option value="<%=spec.getCod()%>"><%=spec.getDenumire()%></option>
				<%
					}
				%>
			</select></td></tr>
			<tr>
<td>Email:<input type="text" name="email" id="email" class=" form-control"></td>
</tr>
<tr>
<td>Telefon:<input type="text" name="telefon" id="telefon" class=" form-control"></td>
</tr>
<tr>
<td>Data nasterii:<input type="text" name="dataNasterii" id="dataNasterii" placeholder="dd/mm/yyyy" class=" form-control">
<input type="hidden" name="data1" id="data1" ></td>
</tr>
<tr>
<td><br><input type="submit" class="btn btn-outline-secondary" id="creaza" name="creaza" value="Creaza cont" style="text-align:center"></td>
</tr>

	</table>
	</fieldset>
	</form>
	
	</div>
	
</body>
<script>
$( function() {
    $( "#dataNasterii" ).datepicker();})
   $('#dataNasterii').datepicker({ dateFormat: 'dd/mm/yy',
	   altField: "#data1",
	      altFormat: "yy-mm-dd"})
	      
	      var msj="<%=msg%>";
 if(msj!="null")
 	alert(msj);
  </script>
</html>