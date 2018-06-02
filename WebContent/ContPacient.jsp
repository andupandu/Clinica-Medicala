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
<% 
if(session.getAttribute("tipUser")=="admin"){%>
<jsp:include page="indexAdmin.jsp" />
<%}else
	if(session.getAttribute("tipUser")=="receptioner"){%>
	<jsp:include page="indexReceptioner.jsp" />
<%}else{%>
<script>
	window.location.href = "index.jsp?message=Nu aveti drept de intrare pe pagina solicitata!";
	</script>
	<% }%>
	<div id="right">
	<div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
	<form method="post" action="ContPacient" id="formContPacient">
	<center>
	<fieldset>
 <legend style=text-align:center>Date pacient</legend>
	<table align="center">
	<tr>
 <td>
<tr>
<td>Nume:<input type="text" name="nume" id="nume" class=" form-control" ></td>
</tr>
<tr>
<td>Prenume:<input type="text" name="prenume" id="prenume" class=" form-control"></td>
</tr>
<tr>
<td>Email:<input type="text" name="email" id="email" class=" form-control"></td>
</tr>
<tr>
<td>Telefon:<input type="text" name="telefon" id="telefon" class=" form-control"></td>
</tr>
<tr>
<td>CNP:<input type="text" name="cnp" id="cnp" class=" form-control"></td>
</tr>
<tr>
<td>Data nasterii:<input type="text" name="dataNasterii" id="dataNasterii" class=" form-control" readonly>
<input type="hidden" name="data1" id="data1" ></td>
</tr>
<tr>
<td><br><input type="submit" class="btn btn-secondary" id="creaza" name="creaza" value="Creaza cont"></td>
</tr>
	</table>
	</fieldset>
	</center>
	</form>
	</div>
</body>
<script>

   $('#dataNasterii').datepicker({ 
	   format: "dd/mm/yyyy",
	   language:"ro"})
	      
	    $('#creaza').click(function(){
    $('#data1').val(moment($('#dataNasterii').datepicker("getDate")).format("YYYY-MM-DD"));
}); 
	      var msj="<%=msg%>";
 if(msj!="null"){
	 var mesaj=document.getElementById("mesaj");
	 mesaj.innerHTML=msj+"<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
 document.getElementById("mesaj").style.display="block";
 }
 	
 
 $.validator.addMethod("verifTelefon", function (value, element) {
     return this.optional(element) || /(02|07)\d{8}$/.test(value);
 }, 'Telefon invalid');
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
 $(document).ready(function(){
 	$('#formContPacient').validate({
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
 		},
 		cnp:{
 			required:true,
 			roCNP:true
 		}
 	}
 })
 })
 </script>

 <script>
 jQuery.extend(jQuery.validator.messages, {
     required: "Campul este obligatoriu.",
    lettersonly:"Va rog inserati doar litere",
     email: "Email valid."
     
     
 });
 </script>
 
</html>