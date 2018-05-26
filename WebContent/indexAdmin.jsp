<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@page import="pkg.Entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="Styles/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/Style.css">
<title>Pagina Administrator</title>

</head>
<body style="background-color:#98B9F2">
<nav class="navbar navbar-toggleable-md navbar-light bg-faded">
  <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <a class="navbar-brand" href="#">Clinica medicala-Pagina Administrator</a>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">      
   </ul>
   <a class="navbar-nav ml-auto">Admin</a>&nbsp;
				 <input type="button" class="btn btn-sm btn-outline-secondary" value="Delogare" onclick="document.getElementById('back').submit()">
</div>
</nav>

<div class="list-group float-left" id="left" style="background:linear-gradient(#e6f3f7,#b1cad1)">
    <a class="list-group-item list-group-item-action"  href="ContMedic.jsp" >Cont Medic</a>
 
    <a class="list-group-item list-group-item-action"   href="InformatiiMedic.jsp" >Medici</a>
  
    <a class="list-group-item list-group-item-action"   href="InformatiiConsultatie.jsp">Programari consultatii</a>
  
    <a class="list-group-item list-group-item-action"  href="InformatiiAnalize.jsp" >Programari analize</a>
    
    <a class="list-group-item list-group-item-action"  href="InformatiiSpecialitati.jsp">Specialitati</a>
    
   <a class="list-group-item list-group-item-action"  href="ContPacient.jsp" > Cont pacient</a>
   
  <a href="InformatiiPacient.jsp"  class="list-group-item list-group-item-action" >Pacienti</a>
  
  <a class="list-group-item list-group-item-action"  href="AnulareProgramari.jsp"> Anulare programari</a>
 
         
</div>
<form id="back" action="index.jsp" action=post></form>
</body>
</html>