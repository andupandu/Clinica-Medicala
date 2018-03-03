<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.Persoana"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="Styles/Style.css">
<title>Clinica medicala</title>
</head>


<body>
	<h1>Clinica Medicala!</h1>
<%Persoana persoanaLogata=(Persoana)session.getAttribute("persoanaLogata");
if(persoanaLogata!=null){
%><div style=text-align:right;"><%=persoanaLogata.getNume()+" "+persoanaLogata.getPrenume() %></div>
<%} %>

	<ul>
 
  <li class="dropdown">
    <a href="javascript:void(0)" class="dropbtn">Specializari</a>
    <div class="dropdown-content">
    <%List<String> specializari=DbOperations.getSpecializari(); 
    for(String spec:specializari){%>
      <a href="#"><%=spec %></a>
<%} %>
    </div>
  </li>
  <li class="dropdown">
   <a href="#">Contul meu</a>
   <div class="dropdown-content">
   <a href="#">Cont nou</a>
   <a href="Login.jsp">Log in</a>
   </div>
   
  
  </li>
</ul>

	<form action="ContNou">
	Nume:<input type="text" name="nume"><br>
	Prenume:<input type="text" name="prenume"><br>
	Cnp:<input type="text" name="cnp"><br>
	Data nastere:<input type="date" name="dataNasterii"><br>
	Telefon:<input type="number" name="telefon"><br>
	Email:<input type="email" name="email"><br>
	<input type="submit" value="Log in">
	</form>
	<%String msg=(String)request.getAttribute("msg");
if(msg!=null && msg!="null"){%>
<p><%=msg %></p>
<% }%>
</body>
</html>
