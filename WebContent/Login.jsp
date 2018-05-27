<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="Styles/bootstrap.min.js"></script>
 <link rel="stylesheet" href="Styles/Style.css"/>
<title>Clinica medicala</title>
</head>


<body id="gradient">

<%Persoana persoanaLogata=(Persoana)session.getAttribute("persoanaLogata"); %>



<nav class="navbar navbar-toggleable-md navbar-light bg-faded">
  <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <a class="navbar-brand" href="#">Clinica medicala</a>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
 
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Specializari
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
        
            <%List<Specialitate> specializari=DbOperations.getSpecializari(); 
    for(Specialitate spec:specializari){%>
    
      <a href="#" class="dropdown-item"><%=spec.getDenumire() %></a>
<%} %>
        </div>
      </li>
  
   

      
   </ul>
<% if(persoanaLogata==null){%>
<ul class="navbar-nav ml-auto">
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#"
					id="navbarDropdownMenuLink" data-toggle="dropdown"
					aria-haspopup="true" aria-expanded="false"> Log in </a>
					<div class="dropdown-menu dropdown-menu-right">
						<form class="px-4 py-3" action="Login" method="post">
							<div class="form-group">
								<label for="exampleDropdownFormEmail1">Email address</label> <input
									type="email" name="email" class="form-control"
									id="exampleDropdownFormEmail1" placeholder="email@example.com">
							</div>
							<div class="form-group">
								<label for="exampleDropdownFormPassword1">Password</label> <input
									type="password" name="password" class="form-control"
									id="exampleDropdownFormPassword1" placeholder="Password">
							</div>
							<button type="submit" class="btn btn-primary">Sign in</button>
						</form>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="ContNou.jsp">New around here?
							Sign up</a> <a class="dropdown-item" href="#">Forgot password?</a>
					</div></li>
			</ul>
			<%}else{%>
				<a class="navbar-nav ml-auto"><%=persoanaLogata.getNume()+" "+persoanaLogata.getPrenume() %></a>
				 <input type="button" class="btn btn-sm btn-outline-secondary" value="Delogare" onclick="reloadPage();">
				
			<% }%>
  </div>
</nav>

	<%
		String msg = (String) request.getAttribute("msg");
		if (msg != null && msg != "null") {
	%>
	<p><%=msg %></p>
<% }%>
<form id="back" action="index.jsp" method="post"></form>
</body>
<script type="text/javascript">
function reloadPage(){
	<%session.removeAttribute("persoanaLogata");
	%>
	document.getElementById("back").submit();
}
</script>
</html>
