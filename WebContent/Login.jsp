<%@page import="pkg.DbOperations"%>
<%@page import="java.util.List"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="Styles/Style.css">
<title>Clinica medicala</title>
</head>


<body>
	<h1>Clinica Medicala!</h1>
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

	<form action="Login">
	Email:<input type="email" name="email"><br>
	Password:<input type="password" name="password"><br>
	<input type="submit" value="Log in">
	</form>
	<%String err=(String)request.getAttribute("err");
if(err!=null && err!="null")%>
<p><%=err %></p>
</body>
</html>
