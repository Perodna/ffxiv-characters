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

<!-- Google charts -->
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">

  // Load the Visualization API and the piechart package.
  google.load('visualization', '1.0', {'packages':['corechart']});

  // Set a callback to run when the Google Visualization API is loaded.
  google.setOnLoadCallback(drawChart);

  // Callback that creates and populates a data table,
  // instantiates the pie chart, passes in the data and
  // draws it.
  function drawChart() {

    // Main job roles
    $.ajax({
		url : "rolesData?rolesType=main",
		dataType : "json",
		async : true,
		success : function(jsonDataMain) {
				drawRolesPie(jsonDataMain, 'Main roles distribution', 'main_roles');
			    drawRolesDiffBars(jsonDataMain, '% difference compared to ideal distribution', 'main_roles_diff');
			}
	});

 	// Alt job roles
    $.ajax({
		url : "rolesData?rolesType=main",
		dataType : "json",
		async : true,
		success : function(jsonDataAlt) {
				drawRolesPie(jsonDataAlt, 'Alt roles distribution', 'alt_roles');
			    drawRolesDiffBars(jsonDataAlt, '% difference compared to ideal distribution', 'alt_roles_diff');
			}
	});
    
 	// All job roles
 	$.ajax({
		url : "rolesData?rolesType=main",
		dataType : "json",
		async : true,
		success : function(jsonDataAll) {
			    drawRolesPie(jsonDataAll, 'All roles distribution (mains & alts)', 'all_roles');
			    drawRolesDiffBars(jsonDataAll, '% difference compared to ideal distribution', 'all_roles_diff');
			}
	});
  }
</script>

<title>Roles statistics</title>
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
								<li class="active"><a href="#">Roles</a></li>
								<li><a href="characters">Characters</a></li>
								<li><a href="characterLevels">All levels</a></li>
							</ul>
						</div>
						<!--/.nav-collapse -->
					</div>
				</div>
			</div>
			
			<!-- Main content goes here -->
			<div class="col-sm-9">
				<div><h5>Roles distribution</h5></div>
				
				<p>Ideal distribution of roles is 25% of tanks, 25% of healers and 50% of DPS.</p>
			
				<div class="row row-same-height">
					<div class="col-sm-8 col-md-height"><div id="main_roles"></div></div>
					<div class="col-sm-4 col-md-height"><div id="main_roles_diff"></div></div>
				</div>
				
				<div class="row row-same-height">
					<div class="col-sm-8 col-md-height"><div id="alt_roles"></div></div>
					<div class="col-sm-4 col-md-height"><div id="alt_roles_diff"></div></div>
				</div>
				
				<div class="row row-same-height">
					<div class="col-sm-8 col-md-height"><div id="all_roles"></div></div>
					<div class="col-sm-4 col-md-height"><div id="all_roles_diff"></div></div>
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