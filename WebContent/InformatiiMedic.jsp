<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
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
	<form method="post" action="ModificaMedic">
	<center>
		<fieldset>
 <legend style=text-align:center>Cauta medic dupa Specialitate</legend>
			<br> <select
				id="specialitateIntrodusa" name="specialitateIntrodusa"
				class="custom-select">
				<%
					List<Specialitate> specializari = (List<Specialitate>) DbOperations.getSpecializari();
					for (Specialitate spec : specializari) {
				%>
				<option value="<%=spec.getCod()%>"><%=spec.getDenumire()%></option>
				<%
					}
				%>
			</select> <input type="submit" value="Cauta" class="btn btn-secondary">
		</fieldset>
		</center>
	</form>
	<br>
	<form method="post" action="ModificaMedic">
	<center> 
	<fieldset style=top:200px>
 <legend style=text-align:center>Afiseaza toti medicii</legend>
			<br> <input type="submit" value="Cauta"
				class="btn btn-secondary">
				</fieldset>
	</center>			
	</form>
	<br>
	<form id="informatii">
	<%
		List<Medic> medici = (List<Medic>) request.getAttribute("medici");
		if (medici != null) {
	%>
	<table class="table">
		<tr>
			<th>Nume</th>
			<th>Prenume</th>
			<th>Specialitate</th>
			<th>Telefon</th>
			<th>Email</th>
			<th>Optiuni</th>
		</tr>

		<%
			int i = 0;
				for (Medic p : medici) {
		%>

		<tr id="medic<%=i%>">
			<td><input type="hidden" id="medicId" name="medicId"
				value="<%=p.getId()%>">
			<input type="text" name="nume" id="nume"
				class=" form-control" size="<%=p.getNume().length()%>"
				value="<%=p.getNume()%>" disabled></td>
			<td><input type="text" name="prenume" id="prenume"
				class=" form-control" size="<%=p.getPrenume().length()%>"
				value="<%=p.getPrenume()%>" disabled></td>
			<td><select id="spec" name="spec" class="custom-select"
				style="display: none; width: 100%">
					<%
						for (Specialitate spec : specializari) {
					%>
					<option value="<%=spec.getDenumire()%>" <%if(spec.getCod()==p.getCodSpec()){ %> selected <%} %>><%=spec.getDenumire()%></option>
					<%
						}
					%>
			</select> <input type="text" name="codSpec" id="codSpec" class=" form-control"
				value="<%=DbOperations.getSpecializari(p.getCodSpec())%>" disabled></td>
			<td><input type="text" name="telefon" id="telefon"
				class=" form-control" value="<%=p.getTelefon()%>" disabled></td>
			<td><input type="text" name="email" id="email"
				class=" form-control" value="<%=p.getEmail()%>" disabled></td>
			<td><input type="submit" class="btn btn-danger"
				id="sterge" name="sterge" value="Sterge"
				onclick="readyToModify(<%=i%>, 'delete')"> <input
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
		}
	%>
	</form>
	</div>
</body>

<script>

function readyToModify(i, verify){
	var accept = false;
	var element = document.getElementById("medic"+i);
	var nume = element.querySelector("#nume");
	var prenume = element.querySelector("#prenume");
	var codspec = element.querySelector("#codSpec");
	var medicId = element.querySelector("#medicId");
	var spec = element.querySelector("#spec");
	var telefon=element.querySelector("#telefon");
	var email=element.querySelector("#email");
	if(verify=="modif"){
		if(!$("#informatii").valid() && !telefon.disabled){
			return;
		}
		codspec.disabled = !codspec.disabled;
		telefon.disabled=!telefon.disabled;
		email.disabled=!email.disabled;
		
		if(spec.style.display == "none"){
			spec.style.display = "block";
			codspec.style.display = "none";
		} 
	
		if(telefon.disabled){
			if (confirm('Sunteti sigur ca doriti sa modificati medicul?')){
				accept = true;
			}
			else{
				window.location.href = "InformatiiMedic.jsp";
			}
		}
	}else{
	if(verify=="delete")
	 if (confirm('Sunteti sigur ca doriti sa stergeti medicul?'))
		accept = true;
	}
	if(accept){
		if(telefon.disabled ||verify=="delete")
		{
		$.post("ModificaMedic",
		        {
		          nume:nume.value,
		          prenume:prenume.value,
		          specializare:codspec.value,
		          verif:verify,
		          medicId:medicId.value,
		          telefon:telefon.value,
		          email:email.value,
		          spec:spec.value
		        },
		        function(data,status){
		         window.location.href = "InformatiiMedic.jsp?message=" + data;
		        });
		}
	}
		 

}


$.validator.addMethod("verifTelefon", function (value, element) {
    return this.optional(element) || /(02|07)\d{8}$/.test(value);
}, 'Telefon invalid');
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
});
	
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