<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    
<html>
<head>
<link rel="stylesheet" type="text/css" href="Styles/Style.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="Styles/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<script src="http://ajax.microsoft.com/ajax/jquery.validate/1.7/additional-methods.js"></script>
<title>Pagina administrator</title>
</head>
<body id="gradient">
	<jsp:include page="indexAdmin.jsp" />
	<div id="right">
	<div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
		<form method="post" action="ModificaPacient" style="text-align:center" id="cautaCnp">
			<center>
			<fieldset>
			<legend>Cauta pacient dupa CNP</legend>
				<input type="text" name="cnpintrodus"
					id="cnpintrodus" class=" form-control" style="width: auto">
				<input type="submit" value="Cauta" class="btn btn-secondary">
				</fieldset>
			</center>
			
		</form>
		<br>
		<form id="informatii">
		<%
			List<Persoana> pacienti = (List<Persoana>) request.getAttribute("pacienti");

			if (pacienti != null) 
			if(pacienti.get(0).getId()!=null){
				
		%>
		<table class="table" style="width: auto" align="center">
			<tr>
				<th>Nume</th>
				<th>Prenume</th>
				<th>CNP</th>
				<th>Email</th>
				<th>Data nasterii</th>
				<th>Telefon</th>
				<th>Optiuni</th>
			</tr>

			<%
				int i = 0;
					for (Persoana p : pacienti)
					{
			%>

			<tr id="pacient<%=i%>">
					<td><input type="hidden" id="pacientId" name="pacientId"
					value="<%=p.getId()%>">
				<input type="text" name="nume" id="nume"
					class=" form-control" value="<%=p.getNume()%>" disabled></td>
				<td><input type="text" name="prenume" id="prenume"
					class=" form-control" value="<%=p.getPrenume()%>" disabled></td>
				<td><input type="text" id="cnp" name="cnp"
					class=" form-control" value=<%=p.getCnp()%> disabled></td>
				<td><input type="text" name="email" id="email"
					class=" form-control" value="<%=p.getEmail()%>" disabled></td>
				<td><input type="text" name="dataNasterii" id="dataNasterii"
					class=" form-control" value="<%=p.getData_nastere()%>" disabled></td>
				<td><input type="text" name="telefon" id="telefon"
					class=" form-control" value="<%=p.getTelefon()%>" disabled></td>

				<td> <input
					type="button" class="btn btn-secondary" id="modifica"
					name="modifica" value="Modifica"
					onclick="readyToModify(<%=i%>, 'modif')"></td>
			</tr>
			<%
				i++;
					}
			%>
		</table>
		<%
			}else{%>
				<div class="p-3 mb-2 bg-light text-dark" style="text-align:center">Nu s-a gasit niciun pacient cu acest cnp</div>
			<%}
		
		%>
		</form>
	</div>
	
</body>

<script>

 function readyToModify(i, verify){
	var accept=false;
 	var element = document.getElementById("pacient"+i);
 	var nume = element.querySelector("#nume");
 	var prenume = element.querySelector("#prenume");
 	var cnp = element.querySelector("#cnp");
 	var pacientId = element.querySelector("#pacientId");
 	var datanasterii = element.querySelector("#dataNasterii");
 	var telefon=element.querySelector("#telefon");
 	var email=element.querySelector("#email");
 	if(!$("#informatii").valid() && !telefon.disabled){
		return;
	}
 	telefon.disabled=!telefon.disabled;
 	email.disabled=!email.disabled;

	if(telefon.disabled){
		if (confirm('Sunteti sigur ca doriti sa modificati pacientul?')){
			accept = true;
		}
		else{
			window.location.href = "InformatiiPacient.jsp";
		}
	}
	
	
	if(accept){
 	if(telefon.disabled ||verify=="delete"){
 		$.post("ModificaPacient",
 		        {
 		          nume:nume.value,
		          prenume:prenume.value,
 		          cnp:cnp.value,
 		          verif:verify,
 		          pacientId:pacientId.value,
 		          telefon:telefon.value,
 		          email:email.value,
 		          dataNasterii:datanasterii.value
 		        },
 		        function(data,status){
 			         window.location.href = "InformatiiPacient.jsp?message=" + data;

 		        });
 	}
 }
	}
 
 $.validator.addMethod("verifTelefon", function (value, element) {
	    return this.optional(element) || /(02|07)\d{8}$/.test(value);
	}, 'Telefon invalid');
 $.validator.addMethod(
	        "roCNP",
	        function(value, element) {
	        	if(value=="")
	        		return true;
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
		$('#informatii').validate({
			rules:{
				email:{
					required:true,
					email:true
				},
				telefon:{
					required:true,
					verifTelefon:true
				}
			}
	})
		$('#cautaCnp').validate({
			rules:{
				cnpintrodus:{
					roCNP:true
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
	    email: "Inserati un email valid.",
	});

 </script>
 </html>