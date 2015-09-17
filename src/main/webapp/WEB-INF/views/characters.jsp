<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script> -->
<script src="static/js/jquery-1.11.2.min.js" type="text/javascript"></script>

<!-- Bootstrap -->
<script src="static/js/bootstrap.min.js" type="text/javascript"></script>
<link href="static/css/bootstrap.paper.min.css" rel="stylesheet">

<script src="static/js/stats.js" type="text/javascript"></script>
<link href="static/css/stats.css" rel="stylesheet">



<title>Characters list</title>
</head>
<body>
	<div class="container">
		<div class="row">
        	<div class="col-xs-12"><h3>Inwilis players statistics</h3></div>
        </div>
	
		<div class="row">
			<div class="col-sm-3">
				<div class="sidebar-nav">
					<div class="navbar navbar-default" role="navigation">
						<div class="navbar-header">
							<button type="button" class="navbar-toggle"
								data-toggle="collapse" data-target=".sidebar-navbar-collapse">
								<span class="sr-only">Toggle navigation</span> <span
									class="icon-bar"></span> <span class="icon-bar"></span> <span
									class="icon-bar"></span>
							</button>
							<span class="visible-xs navbar-brand">Sidebar menu</span>
						</div>
						<div class="navbar-collapse collapse sidebar-navbar-collapse">
							<ul class="nav navbar-nav">
								<li><a href="jobs">Jobs</a></li>
								<li><a href="roles">Roles</a></li>
								<li class="active"><a href="#">Characters</a></li>
								<li><a href="characterLevels">All levels</a></li>
							</ul>
						</div>
						<!--/.nav-collapse -->
					</div>
				</div>
			</div>
			
			<!-- Main content goes here -->
			<div class="col-sm-9">
				<div><h5>Characters list</h5></div>
			
				<div>
					<table class="table table-striped table-hover table-condensed table-responsive">
						<thead>
							<tr>
								<th>Name</th>
								<th>Main job</th>
								<th>Alt jobs</th>
							</tr>
						</thead>
						
						<tbody>
							<c:forEach items="${chars}" var="c">
							<tr>
								<td class="vert-align"><a href="character?chardId=${c.id}">${c.firstName} ${c.lastName}</a></td>
								<td class="vert-align"><span class="badge badge-${c.mainJob.shortName}">${c.mainJob.shortName}</span></td>
								<td class="vert-align"><c:forEach items="${c.jobs}" var="j"><c:if test="${j.id != c.mainJob.id}"><span class="badge badge-${j.shortName}">${j.shortName}</span>&nbsp;</c:if></c:forEach></td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Footer -->
	<div id="footer">
	  <div class="container">
	    <p class="text-muted" align="right">No lalafells were harmed while making this website.</p>
	  </div>
	</div>
</body>
</html>