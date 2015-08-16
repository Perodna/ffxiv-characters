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

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
	google.load('visualization', '1.1', { packages: [ 'corechart' ] });
	google.setOnLoadCallback(drawChart);

	function drawChart() {

		var jsonData = $.ajax({
			url : "characterJobData?charId=${c.id}",
			dataType : "json",
			async : false
		}).responseText;

		console.log(jsonData);
		
		var data = new google.visualization.DataTable(jsonData);

		var options = {
			chart : { title : 'iLevel evolution by job' }, // not used by standard line chart, hopefully the functionality is added soon
			width : "100%",
			height : 500,
			series : getSeriesForLineChartData(data) // series color change not supported by Material Line chart, reason why we stick to standard line chart
		};

		var chart = new google.visualization.LineChart(document.getElementById('job_lvl_evolution'));

		chart.draw(data, options);
	}
</script>

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
								<li><a href="characters">Characters</a></li>
							</ul>
						</div>
						<!--/.nav-collapse -->
					</div>
				</div>
			</div>
			
			<!-- Main content goes here -->
			<div class="col-sm-9">
				<div><h5>Character info</h5></div>
			
				<div>
					<p>First name: ${c.firstName}</p>
					<p>Last name: ${c.lastName}</p>
					<p>Main job: <span class="badge badge-${c.mainJob.shortName}">${c.mainJob.shortName}</span> - iLevel: ${mainJobLevel} </p>
					<p>Alt jobs: <c:forEach items="${c.altJobs}" var="j"><span class="badge badge-${j.shortName}">${j.shortName}</span>&nbsp;</c:forEach></p>
					
					<div>
					<!-- Won't work for a character with a quote ' in his name -->
					<p><a href="http://www.inwilis.fr/inventaire/char.php?name=${c.firstName}%20${c.lastName}">Open gear details</a></p>
					</div>
					
					<div>
						<h5 align="center">iLevel evolution by job</h5>
						<div align="center" id="job_lvl_evolution"></div>
					</div>
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