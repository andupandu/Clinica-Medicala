<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
if(session.getAttribute("tipUser")=="admin"){%>
<jsp:include page="indexAdmin.jsp" />
<%}else{%>
<script>
	window.location.href = "index.jsp?message=Nu aveti drept de intrare pe pagina solicitata!";
	</script>
<%} %>


<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="Styles/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<script src="http://ajax.microsoft.com/ajax/jquery.validate/1.7/additional-methods.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/js/bootstrap-datepicker.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/locales/bootstrap-datepicker.ro.min.js"></script>
<script src="https://momentjs.com/downloads/moment.js"></script>
<script>
$.validator.addMethod("verifTelefon", function (value, element) {
    return this.optional(element) || /(02|07)\d{8}$/.test(value);
}, 'Telefon invalid');
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
			required:true,
			verifTelefon:true
		
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
    email: "Email valid.",
   
    
});
</script>

<title>Pagina administrator</title>
</head>
<body id="gradient">
<%String msg=(String)request.getAttribute("msg");%>



	<div id="right">
<div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">

</div>
	<form method="post" action="ContMedic" id="formContMedic">
	<center>
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
<td>Data nasterii:<input type="text" name="dataNasterii" id="dataNasterii" placeholder="dd/mm/yyyy" class=" form-control" readonly>
<input type="hidden" name="data1" id="data1" ></td>
</tr>
<tr>
<td><br><input type="submit" class="btn btn-secondary" id="creaza" name="creaza" value="Creaza cont" style="text-align:center"></td>
</tr>

	</table>
	</fieldset>
	</center>
	</form>
	
	</div>
	
</body>
<script>

   $('#dataNasterii').datepicker({
	   format:"dd/mm/yyyy",
	   language: 'ro',
	   endDate:"+1d"})
	      
	    $('#creaza').click(function(){
    $('#data1').val(moment($('#dataNasterii').datepicker("getDate")).format("YYYY-MM-DD"));
}); 
	      var msj="<%=msg%>";
 if(msj!="null"){
	 var mesaj=document.getElementById("mesaj");
	 mesaj.innerHTML=msj+"<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
 document.getElementById("mesaj").style.display="block";
 }
  </script>
</html>