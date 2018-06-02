<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@page import="pkg.Entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="Styles/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/Style.css">
<title>Pagina Medic</title>

</head>
<body id="gradient">
<%Persoana persoanaLogata=(Persoana)session.getAttribute("persoanaLogata"); %>
<% 
if(session.getAttribute("tipUser")!="medic"){%>
<script>
	window.location.href = "index.jsp?message=Nu aveti drept de intrare pe pagina solicitata!";
	</script>
<%}%>
<nav class="navbar navbar-toggleable-md navbar-light bg-faded">
  <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <a class="navbar-brand" href="#">Clinica medicala</a>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">      
   </ul>
   <a class="navbar-nav ml-auto">Medic:<%if(persoanaLogata!=null){%> <%=persoanaLogata.getNume()+" "+persoanaLogata.getPrenume()%><%} %></a>&nbsp;
				 <input type="button" class="btn btn-sm btn-outline-secondary" value="Delogare" onclick="reloadPage()">
</div>
</nav>

<div class="list-group float-left" id="left" style="background:linear-gradient(#e6f3f7,#b1cad1)">
   
  <a href="InformatiiPacient.jsp"  class="list-group-item list-group-item-action" >Zile libere</a>
  
  <a class="list-group-item list-group-item-action"  href="AnulareProgramari.jsp"> Anulare programari consultatie</a>
<div id="accordion">

  <div class="card">
   
      <a class="list-group-item list-group-item-action" data-toggle="collapse" href="#collapseOne">
        Servicii oferite
      </a>
   
    <div id="collapseOne" class="collapse" data-parent="#accordion">
      <div class="card-body">
           <a class="list-group-item list-group-item-action"  href="StatusProgAnalize.jsp">Adauga serviciu</a>
  <a class="list-group-item list-group-item-action"  href="StatusProgAnalize.jsp">Modifica serviciu</a>
      </div>
    </div>
  </div>

  <a class="list-group-item list-group-item-action"  href="StatusProgAnalize.jsp">Program de lucru</a>
  <a class="list-group-item list-group-item-action"  href="DateCont.jsp"> Date Cont</a>
</div>
</div>
<form id="back" action="Delogare" action=post></form>
</body>
<script>
function reloadPage(){
	
	document.getElementById("back").submit();
}
</script>
</html>