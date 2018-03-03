<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="Styles/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pagina administrator</title>
</head>
<body>
<table class="table">
    <tr>
      <th>Nume</th>
      <th>Prenume</th>
        <th>Specialitate</th>
      <th>Optiuni</th>
    </tr>

<%int i=0;
for(Medic p : DbOperations.getListaMedici()){ %>
<form name="info" id="info" action="ModificaMedic" method="post">
<tr id="medic<%=i%>">
<input type="hidden" id="medicId" name="medicId" value="<%=p.getId()%>">
<input type="hidden" id="verif" name="verif">
<td><input type="text" name="nume" id="nume" class=" form-control" size="<%=p.getNume().length() %>" value="<%=p.getNume() %>" disabled></td>
<td><input type="text" name="prenume" id="prenume" class=" form-control" size="<%=p.getPrenume().length() %>" value="<%=p.getPrenume() %>" disabled></td>
<td><input type="text" name="codSpec" id="codSpec" class=" form-control"size="<%=DbOperations.getSpecializari(p.getCodSpec()).length() %>"  value="<%=DbOperations.getSpecializari(p.getCodSpec()) %>" disabled></td>
<td>
<input type="submit" class="btn btn-outline-danger" id="sterge" name="sterge" value="Sterge" onclick="verifOperation()">
<input type="button" class="btn btn-outline-secondary" id="modifica" name="modifica" value="Modifica" onclick="readyToModify(<%=i%>)">
</form></td>
</tr>
<%
i++;
} %>
</table>
</body>
</html>
<script>

$('#modifica').click(function(){
    $('#nume').prop('disabled', false);
    $('#prenume').prop('disabled', false);
    $('#codSpec').prop('disabled', false);
});

function readyToModify(i){
	var element = document.getElementById("medic"+i);
	var nume = element.querySelector("#nume");
	var prenume = element.querySelector("#prenume");
	var codspec = element.querySelector("#codspec");
	
	nume.disabled = !nume.disabled;
	prenume.disabled = !prenume.disabled;
	codspec.disabled = !codspec.disabled;
	
	if(nume.disabled ){
		alert("s a facut modificarea....")
	}
	
}
function verifOperation(){
		document.getElementById("verif").value="delete";
}
</script>