<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<html>
<head>
<link rel="stylesheet" type="text/css" href="Styles/Style.css">
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
		<form method="post" action="ModificaPacient" style="text-align:center">
			<center>
				Cauta pacient dupa CNP: <input type="text" name="cnpintrodus"
					id="cnpintrodus" class=" form-control" style="width: auto">
				<input type="submit" value="Cauta" class="btn btn-outline-secondary">
			</center>
		</form>
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
			}else{%>
				<div class="p-3 mb-2 bg-light text-dark" style="text-align:center">Nu s-a gasit niciun pacient cu acest cnp</div>
			<%}
		
		%>
	</div>
</body>
</html>
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
 	if(verify=="modif"){
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
	}else{
		if(verify=="delete")
			 if (confirm('Sunteti sigur ca doriti sa stergeti pacientul?'))
				accept = true;
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
 		        	alert(data);
 		        	location.reload(true);
 		        });
 	}
 }
	}
 </script>