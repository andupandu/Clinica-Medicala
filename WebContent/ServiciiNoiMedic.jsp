<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@page import="pkg.Utils.*"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/js/bootstrap-datepicker.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/locales/bootstrap-datepicker.ro.min.js"></script>
 <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<script src="http://ajax.microsoft.com/ajax/jquery.validate/1.7/additional-methods.js"></script>

</head>

<body id="gradient">
<%
String msg=(String)request.getAttribute("msg");%>
<div id="right">
<div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
<form method="post" action="AdaugaServiciuMedic" onsubmit="return Verif()" id="adaugaServiciu">
<center>
<fieldset>
<legend>Adauga serviciu nou</legend>
<table align="center">
<tr>
<td>

Denumire serviciu:<input id="text" name="serviciu" id="serviciu" class=" form-control"><br>
Timp alocat:<input type="time" name="timp" id="timp" class="form-control" min="00:00" max="05:00" >
Pret(Lei):
  <input type="text" class="form-control" id="pret" name="pret">
<input type="submit" class="btn btn-secondary"  id="adauga" name="adauga" value="Adauga serviciu">
</td>
</tr>
</table>
</fieldset>
</center>
</form>
</div>
</body>
<script>
	   
	   
function Verif(){
	if(!$("#adaugaServiciu").valid()){
	return false;
	}else
		return true;
	
}



$( document ).ready(function() {
	document.getElementById("timp").defaultValue = "00:10";

})

$.validator.addMethod("verifDenumire", function (value, element) {
    return this.optional(element) || /(02|07)\d{8}$/.test(value);
}, 'Telefon invalid');
 $(document).ready(function(){
		$('#adaugaServiciu').validate({
			rules:{
				serviciu:{
					required:true,
					digits:false
					
				},
				pret:{
					required:true,
					digits:true
					}
				},
				timp:{
					required:true
				}
				
			})
			
	})


	jQuery.extend(jQuery.validator.messages, {
    required: "Campul este obligatoriu.",
    digits:"Camp invalid",
});
 var msj="<%=msg%>";
 if(msj!="null"){
 	 var mesaj=document.getElementById("mesaj");
 	 mesaj.innerHTML=msj+"<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
 document.getElementById("mesaj").style.display="block";
 }
  </script>
</html>