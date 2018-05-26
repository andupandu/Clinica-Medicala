<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="Styles/bootstrap.min.js"></script>

<title>Clinica medicala</title>
</head>


<body>
	
<%Persoana persoanaLogata=(Persoana)session.getAttribute("persoanaLogata");
if(persoanaLogata!=null){
%><div style=text-align:right;"><%=persoanaLogata.getNume()+" "+persoanaLogata.getPrenume() %></div>
<%} %>

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
       <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Contul meu
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
        
            
    
      <a href="ContNou.jsp" class="dropdown-item">Cont nou</a>
       <a href="Login.jsp" class="dropdown-item">Log in</a>
        </div>
      </li>
  
   

      
   </ul>
</div>
</nav>
	<form action="ContNou" id="formContNou">
	Nume:<input type="text" name="nume" class=" form-control" size="50"><br>
	Prenume:<input type="text" name="prenume" class=" form-control" size="50"><br>
	Cnp:<input type="text" name="cnp" class=" form-control" size="50"><br>
	Data nastere:<input type="date" name="dataNasterii" class=" form-control" size="50"><br>
	Telefon:<input type="number" name="telefon" class=" form-control" size="50"><br>
	Email:<input type="email" name="email" class=" form-control" size="50"><br>
	<input type="submit" value="Log in" class="btn btn-secondary">
	</form>
	<%String msg=(String)request.getAttribute("msg");
if(msg!=null && msg!="null"){%>
<p><%=msg %></p>
<% }%>
</body>
</html>
