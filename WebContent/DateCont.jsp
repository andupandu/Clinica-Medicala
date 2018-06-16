<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@page import="pkg.Utils.*"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%Persoana persoanaLogata=(Persoana)session.getAttribute("persoanaLogata");
String tipUser=(String)session.getAttribute("tipUser");%>
<title>Pagina receptioner</title>
	<%if(tipUser=="receptioner"){%>
<jsp:include page="indexReceptioner.jsp" />
<%}else if(tipUser=="medic"){%>
<jsp:include page="indexMedic.jsp" />
	<%}else{%>
<script>
	window.location.href = "index.jsp?message=Nu aveti drept de intrare pe pagina solicitata!";
	</script>
<%} %>
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
<% String msg=(String)request.getAttribute("msg");%>

<body id="gradient">


<div id="right">
<div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
<center>
<fieldset>
<legend>Date cont</legend>
<form method="post" action="DateCont"  id="modificareEmail" onsubmit="return Verif()">

<table align="center">
<tr>
<td>
Email:<input type="text" name="email" id="email" class="datepicker form-control" value="<%=(persoanaLogata!=null)?DbOperations.getUserEmail(persoanaLogata,tipUser):""%>" readonly>
<input type="submit" class="btn btn-secondary"  id="modifica" name="modifica" value="Modifica" >
</table>
</form>
<hr><hr>
<form method="post" action="DateCont" id="schimbaParola">
<table align="center">
<tr>
<td>
Parola curenta:<input type="password" name="parolacurenta" id="parolacurenta" class="datepicker form-control" ><br>
Noua parola:<input type="password" name="parolanoua" id="parolanoua" class="datepicker form-control" ><br>
Confirma parola:<input type="password" name="parolaconfirma" id="parolaconfirma" class="datepicker form-control"><br>
<input type="submit" class="btn btn-secondary"  id="schimba" name="schimba" value="Schimba parola">
</td>
</tr>
</table>
</form>

</fieldset>
</center>

</div>
</body>
<script>

function Verif(){
	var email=document.getElementById("email");
	email.readOnly=!email.readOnly;
	if(!$("#modificareEmail").valid()){
			return false;
	}else if(email.readOnly && confirm("Sunteti sigur ca doriti sa modificati emailul?")) 
		return true;
	
	return false;
}

var msj="<%=msg%>";
if(msj!="null"){
	 var mesaj=document.getElementById("mesaj");
	 mesaj.innerHTML=msj+"<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
document.getElementById("mesaj").style.display="block";
}
 $(document).ready(function(){
		$('#schimbaParola').validate({
			rules:{
				parolacurenta:{
					required:true
					
				},
				parolanoua:{
					required:true,
					minlength:5
			
				},
				parolaconfirma:{
					equalTo:"#parolanoua"
					
				}
				
			}

 })
 $('#modificareEmail').validate({
		rules:{
			email:{
				required:true,
				email:true
				
			}
		}
})
 })

	
	
	jQuery.extend(jQuery.validator.messages, {
    required: "Campul este obligatoriu.",
    minlength:"Introduceti minim 5 caractere",
    equalTo:"Parolele nu coincid",
    email:"Email invalid"
});
  </script>
</html>