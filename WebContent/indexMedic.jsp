<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@page import="pkg.Entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
  <a class="navbar-brand" href="#"><img src="resources/clinica.png" width="40" height="40">&nbsp Clinica medicala</a>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">      
   </ul>
   <a class="navbar-nav ml-auto"><img src="resources/user-circle.svg" width="25" height="25">&nbsp Medic:<%if(persoanaLogata!=null){%> <%=persoanaLogata.getNume()+" "+persoanaLogata.getPrenume()%><%} %></a>&nbsp;
				 <input type="button" class="btn btn-sm btn-outline-secondary" value="Delogare" onclick="reloadPage()">
</div>
</nav>

<div class="list-group float-left" id="left" style="background:linear-gradient(#e6f3f7,#b1cad1)">
   
  <a href="ZileLibereMedic.jsp"  class="list-group-item list-group-item-action" ><img src="resources/calendar-alt.svg" width="25" height="25">&nbsp Zile libere</a>
  <div class="card">
   
      <a class="list-group-item list-group-item-action" data-toggle="collapse" href="#collapse1">
      <img src="resources/stethoscope.svg" width="15" height="15">&nbsp Programari consultatie
      </a>
   
    <div id="collapse1" class="collapse" data-parent="#accordion">
      <div class="card-body">
           <a class="list-group-item list-group-item-action"  href="AnulareProgramari.jsp">Anulare programari consultatie</a>
  <a class="list-group-item list-group-item-action"  href="VizualizareProgramari.jsp">Programarile mele</a>
      </div>
    </div>
  </div>
<div id="accordion">

  <div class="card">
   
      <a class="list-group-item list-group-item-action" data-toggle="collapse" href="#collapseOne">
      <img src="resources/tasks.svg" width="25" height="25"> &nbsp Servicii oferite
      </a>
   
    <div id="collapseOne" class="collapse" data-parent="#accordion">
      <div class="card-body">
           <a class="list-group-item list-group-item-action"  href="ServiciiNoiMedic.jsp">Adauga serviciu</a>
  <a class="list-group-item list-group-item-action"  href="ModificareServiciiMedic.jsp">Modifica serviciu</a>
      </div>
    </div>
  </div>
</div>
<div id="accordion">

  <div class="card">
   
      <a class="list-group-item list-group-item-action" data-toggle="collapse" href="#collapse2">
     <img src="resources/calendar-alt.svg" width="25" height="25">&nbsp Program de lucru
      </a>
   
    <div id="collapse2" class="collapse" data-parent="#accordion">
      <div class="card-body">
           <a class="list-group-item list-group-item-action"  href="ZileProgramMedic">Adauga program de lucru</a>
  <a class="list-group-item list-group-item-action"  href="ModificaProgramMedic.jsp">Modifica program de lucru</a>
      </div>
    </div>
  </div>
</div>

  <a class="list-group-item list-group-item-action"  href="DateCont.jsp"><img src="resources/user-circle.svg" width="25" height="25">&nbsp Contul meu</a>

</div>
<form id="back" action="Delogare" action=post></form>
</body>
<script>
function reloadPage(){
	
	document.getElementById("back").submit();
}
</script>
</html>