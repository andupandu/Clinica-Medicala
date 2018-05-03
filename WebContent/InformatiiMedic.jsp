<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="Styles/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pagina administrator</title>
</head>
<body>
	<jsp:include page="indexAdmin.jsp" />
	<div id="right">
	<form method="post" action="ModificaMedic">
		<center>
			Cauta medic dupa Specialitate:<br> <select
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
			</select> <input type="submit" value="Cauta" class="btn btn-outline-secondary">
		</center>
	</form>
	<form method="post" action="ModificaMedic">
		<center>
			Afiseaza toti medicii:<br> <input type="submit" value="Cauta"
				class="btn btn-outline-secondary">
		</center>
	</form>
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
			<input type="hidden" id="medicId" name="medicId"
				value="<%=p.getId()%>">
			<td><input type="text" name="nume" id="nume"
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
					<option value="<%=spec.getDenumire()%>"><%=spec.getDenumire()%></option>
					<%
						}
					%>
			</select> <input type="text" name="codSpec" id="codSpec" class=" form-control"
				value="<%=DbOperations.getSpecializari(p.getCodSpec())%>" disabled></td>
			<td><input type="text" name="telefon" id="telefon"
				class=" form-control" value="<%=p.getTelefon()%>" disabled></td>
			<td><input type="text" name="email" id="email"
				class=" form-control" value="<%=p.getEmail()%>" disabled></td>
			<td><input type="submit" class="btn btn-outline-danger"
				id="sterge" name="sterge" value="Sterge"
				onclick="readyToModify(<%=i%>, 'delete')"> <input
				type="button" class="btn btn-outline-secondary" id="modifica"
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
	</div>
</body>
</html>
<script>

function readyToModify(i, verify){
	var element = document.getElementById("medic"+i);
	var nume = element.querySelector("#nume");
	var prenume = element.querySelector("#prenume");
	var codspec = element.querySelector("#codspec");
	var medicId = element.querySelector("#medicId");
	var spec = element.querySelector("#spec");
	var telefon=element.querySelector("#telefon");
	var email=element.querySelector("#email");
	
	nume.disabled = !nume.disabled;
	prenume.disabled = !prenume.disabled;
	codspec.disabled = !codspec.disabled;
	telefon.disabled=!telefon.disabled;
	email.disabled=!email.disabled;
	
	if(spec.style.display == "none"){
		spec.style.display = "block";
		codspec.style.display = "none";
	} 
	
	if(nume.disabled ||verify=="delete"){
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
		        	alert(data);
		        	location.reload(true);
		        });
	}
}
function Verif(){
	document.getElementById("verif").value="add";
	if(document.getElementById("numeNou").value==''||document.getElementById("prenumeNou").value==''
			||document.getElementById("specNoua").value==''||document.getElementById("telefonNou").value==''||
			document.getElementById("emailNou").value==''){
	alert("Toate campurile sunt obligatorii");
	return false;
	}else
		return true;
	
}
</script>