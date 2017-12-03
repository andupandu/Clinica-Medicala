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
</ul>
	
	<form action="Hello">
		<input type="submit" value="Press">
	</form>
</body>
</html>
