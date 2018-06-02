<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="Styles/bootstrap.min.js"></script>
 <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"/>
 
 <link rel="stylesheet" href="Styles/Style.css"/>
<title>Clinica medicala</title>
</head>
<body id="gradient">
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
   <div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
</body>
<script>
if(window.location.href.indexOf("message=") > 0)
{	
	var message = window.location.href.split("message=")[1].replace(/%20/g," ");
	var mesaj=document.getElementById("mesaj");
    mesaj.innerHTML=message+"<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
    document.getElementById("mesaj").style.display="block";
}</script>
</html>
