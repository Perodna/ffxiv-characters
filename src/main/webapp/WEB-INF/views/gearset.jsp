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
<script src="static/js/gearset.js" type="text/javascript"></script>


<title>${c.fullName} ${j.shortName} gearset</title>
</head>
<body>
    <script src="https://secure.xivdb.com/tooltips.js"></script>

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
								<li><a href="characterLevels">All levels</a></li>
							</ul>
						</div>
						<!--/.nav-collapse -->
					</div>
				</div>
			</div>

			<!-- Main content goes here -->
			<div class="col-sm-9">
				<div><h4>Gearset details</h4></div>

				<div>
					<p>Character: <a href="character?charId=${c.id}">${c.fullName}</a></p>
					<p>Job: <span class="badge badge-${j.shortName}">${j.name}</span></p>

                    <div>
						<div>Weapon
						<a id="${g.weaponId}">http://xivdb.com/item/${g.weaponId}</a><script>setUrlXIVDB('${g.weaponId}');</script>
						<div>

						<div>Head
						<a id="${g.headId}">http://xivdb.com/item/${g.weaponId}</a><script>setUrlXIVDB('${g.headId}');</script>
						<div>

						<div>Body
						<a id="${g.bodyId}">http://xivdb.com/item/${g.weaponId}</a><script>setUrlXIVDB('${g.bodyId}');</script>
						<div>

						<div>Hands
						<a id="${g.handsId}">http://xivdb.com/item/${g.weaponId}</a><script>setUrlXIVDB('${g.handsId}');</script>
						<div>

						<div>Waist
						<a id="${g.waistId}">http://xivdb.com/item/${g.weaponId}</a><script>setUrlXIVDB('${g.waistId}');</script>
						<div>

						<div>Legs
						<a id="${g.legsId}">http://xivdb.com/item/${g.weaponId}</a><script>setUrlXIVDB('${g.legsId}');</script>
						<div>

						<div>Feet
						<a id="${g.feetId}">http://xivdb.com/item/${g.weaponId}</a><script>setUrlXIVDB('${g.feetId}');</script>
						<div>

						<br>

						<div>Offhand !!! TODO check if absent !!!
						<div>

						<div>Earrings
						<a id="${g.earringsId}">http://xivdb.com/item/${g.weaponId}</a><script>setUrlXIVDB('${g.earringsId}');</script>
						<div>

						<div>Necklace
						<a id="${g.necklaceId}">http://xivdb.com/item/${g.weaponId}</a><script>setUrlXIVDB('${g.necklaceId}');</script>
						<div>

						<div>Bracelets
						<a id="${g.braceletsId}">http://xivdb.com/item/${g.weaponId}</a><script>setUrlXIVDB('${g.braceletsId}');</script>
						<div>

						<div>Ring
						<a id="${g.ring1Id}">http://xivdb.com/item/${g.weaponId}</a><script>setUrlXIVDB('${g.ring1Id}');</script>
						<div>

						<div>Ring
						<a id="${g.ring2Id}">http://xivdb.com/item/${g.weaponId}</a><script>setUrlXIVDB('${g.ring2Id}');</script>
						<div>

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