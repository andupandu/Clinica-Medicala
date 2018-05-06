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
<title>Pagina administrator</title>
</head>
<body>
<%String msg=(String)request.getAttribute("msg");%>
<jsp:include page="indexAdmin.jsp" />
	<div id="right">
	<form method="post" action="ContPacient">
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
<td>Data nasterii:<input type="text" name="dataNasterii" id="dataNasterii" class=" form-control">
<input type="hidden" name="data1" id="data1" ></td>
</tr>
<tr>
<td><br><input type="submit" class="btn btn-outline-secondary" id="creaza" name="creaza" value="Creaza cont"></td>
</tr>
	</table>
	</form>
	</div>
</body>
<script>
$( function() {
    $( "#dataNasterii" ).datepicker();})
   $('#dataNasterii').datepicker({ dateFormat: 'dd/mm/yy',
	   altField: "#data1",
	      altFormat: "yy-mm-dd"})
	      
	      var msj="<%=msg%>";
 if(msj!="null")
 	alert(msj);
  </script>
</html>