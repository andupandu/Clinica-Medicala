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
<title>Pagina Administrator</title>

</head>
<body id="gradient">
<% 
if(session.getAttribute("tipUser")!="admin"){%>
<script>
	window.location.href = "index.jsp?message=Nu aveti drept de intrare pe pagina solicitata!";
	</script>
<%}%>
<nav class="navbar navbar-toggleable-md navbar-light bg-faded">
  <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <a class="navbar-brand" href="#"><img src="resources/clinica.png" width="40" height="40">Clinica medicala-Pagina Administrator</a>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">      
   </ul>
   <a class="navbar-nav ml-auto"><img src="resources/user-circle.svg" width="25" height="25"> Admin</a>&nbsp;
				 <input type="button" class="btn btn-sm btn-outline-secondary" value="Delogare" onclick="reloadPage()">
</div>
</nav>

<div class="list-group float-left" id="left" style="background:linear-gradient(#e6f3f7,#b1cad1)">
<div id="accordion">

  <div class="card">
   
      <a class="list-group-item list-group-item-action" data-toggle="collapse" href="#collapse1">
    <img src="resources/user-md.svg" width="25" height="25"> Medici
      </a>
   
    <div id="collapse1" class="collapse" data-parent="#accordion">
      <div class="card-body">
           <a class="list-group-item list-group-item-action"  href="ContMedic.jsp">Cont medic</a>
  <a class="list-group-item list-group-item-action"  href="InformatiiMedic.jsp">Date medic</a>
      </div>
    </div>
  </div>
  <div class="card">
   
      <a class="list-group-item list-group-item-action" data-toggle="collapse" href="#collapse2">
   <img src="resources/user.svg" width="25" height="25">Pacienti
      </a>
   
    <div id="collapse2" class="collapse" data-parent="#accordion">
      <div class="card-body">
           <a class="list-group-item list-group-item-action"  href="ContPacient.jsp">Cont pacient</a>
  <a class="list-group-item list-group-item-action"  href="InformatiiPacient.jsp">Date pacient</a>
      </div>
    </div>
  </div>
  <div class="card">
   
      <a class="list-group-item list-group-item-action" data-toggle="collapse" href="#collapse3">
 <img src="resources/edit.svg" width="25" height="25">Actualizare date
      </a>
   
    <div id="collapse3" class="collapse" data-parent="#accordion">
      <div class="card-body">
           <a class="list-group-item list-group-item-action"  href="InformatiiSpecialitati.jsp">Specialitati</a>
  <a class="list-group-item list-group-item-action"  href="AdaugaAnalize.jsp">Analize</a>
  <a class="list-group-item list-group-item-action"  href="RezultateAnalize.jsp"> Rezultate analize</a>
  <a class="list-group-item list-group-item-action"  href="CategoriiAnalize.jsp"> Categorii analize</a>
    <a class="list-group-item list-group-item-action"  href="AdaugaIndicator.jsp"> Indicator analize</a>
      </div>
    </div>
  </div>



  <div class="card">
   
      <a class="list-group-item list-group-item-action" data-toggle="collapse" href="#collapse4">
<img src="resources/diagnoses.svg" width="25" height="25">Receptioneri
      </a>
   
    <div id="collapse4" class="collapse" data-parent="#accordion3">
      <div class="card-body">
           <a class="list-group-item list-group-item-action"  href="ContReceptioner.jsp">Cont Receptioner</a>
      </div>
    </div>
  </div>
  </div>
</div>

<form id="back" action="Delogare" action=post></form>
<script>
function reloadPage(){
	document.getElementById("back").submit();
}
</script>
</body>

</html>