function drawJobsPie(jsonData, title, div) {	
	var data = new google.visualization.DataTable(jsonData);
	
    var options = {
    	    'title': title,
    	    'pieHole': 0.5, // make it a donut
    	    'height': 400,
    	    'width': '100%',
    	    'chartArea': { width:'75%',height:'75%'},
    	    'slices': getPieJobColors(data)
   	};
    var chart = new google.visualization.PieChart(document.getElementById(div));
    chart.draw(data, options);
};

function drawRolesPie(jsonData, title, div) {
	var data = new google.visualization.DataTable(jsonData);

    var options = {
    	    'title': title,
    	    'pieHole': 0.5, // make it a donut
    	    'height': 400,
    	    'width': '100%',
    	    'chartArea': { width:'75%',height:'75%'},
	  	    'colors': [
		  	    getRoleColorCode('Tank'),
		  	    getRoleColorCode('Healer'),
		  	    getRoleColorCode('DPS')
			]
   	};
    var chart = new google.visualization.PieChart(document.getElementById(div));
    chart.draw(data, options);
};

function drawRolesDiffBars(jsonData, title, div) {
	var tmpData = new google.visualization.DataTable(jsonData);
	var total = 0;
	
	for (var i = 0; i < tmpData.getNumberOfRows(); i++) {
		total += tmpData.getValue(i, 1);
	}
	
	var tankDiff = tmpData.getValue(0, 1) / total / 0.25 - 1;
	var healerDiff = tmpData.getValue(1, 1) / total / 0.25 - 1;
	var dpsDiff = tmpData.getValue(2, 1) / total / 0.5 - 1;
	
	var rows = [ ['Tank', tankDiff], ['Healer', healerDiff], ['DPS', dpsDiff] ];
	
	var data = google.visualization.arrayToDataTable([
      ["Role", "Difference", { role: "style" } ],
      ["Tank", parseFloat(tankDiff.toFixed(4)), getRoleColorCode('Tank')],
      ["Healer", parseFloat(healerDiff.toFixed(4)), getRoleColorCode('Healer')],
      ["DPS", parseFloat(dpsDiff.toFixed(4)), getRoleColorCode('DPS')],
    ]);
	
	var view = new google.visualization.DataView(data);
    view.setColumns([0, 1,
                     { calc: getValueAt.bind(undefined, 1),
                       sourceColumn: 1,
                       type: "string",
                       role: "annotation" },
                     2]);
	
	 var options = {
		        'title': title,
		        'width': '100%',
		        'height': 250,
		        bar: {groupWidth: "95%"},
		        legend: { position: "none" },
		        vAxis: {format:'#.## %'}
	};
	 
	var chart = new google.visualization.ColumnChart(document.getElementById(div));
    chart.draw(view, options);
}

function drawJobEvolutionLineChart(jsonData, title, div) {
	var data = new google.visualization.DataTable(jsonData);

	var options = {
		chart : { 'title' : title }, // not used by standard line chart, hopefully the functionality is added soon
		width : "100%",
		height : 500,
		series : getSeriesForLineChartData(data) // series color change not supported by Material Line chart, reason why we stick to standard line chart
	};

	var chart = new google.visualization.LineChart(document.getElementById(div));
	chart.draw(data, options);
}


function getValueAt(column, dataTable, row) {
    return (dataTable.getValue(row, column) * 100).toFixed(2) + ' %';
}

function getSeriesForLineChartData(dataTable) {
	var series = {};
	
	var view = new google.visualization.DataView(dataTable);
	
	for (var i=1; i < view.getNumberOfColumns(); i++) {
		series[i-1] = { color: getJobColorCode(view.getColumnId(i)), job: view.getColumnId(i) };
	}
	
	return series;
}


// ----------------------------------
//  COLOR FUNCTIONS FOR JOBS & ROLES
// ----------------------------------

var jobColors = {
	'PLD': '#1B98E0',
	'WAR': '#247BA0',
	'DRK': '#006494',
	'WHM': '#049016',
	'SCH': '#41B338',
	'AST': '#7FD75B',
	'MNK': '#97071B',
	'DRG': '#A82721',
	'NIN': '#B94728',
	'BRD': '#CB682E',
	'MCH': '#DC8835',
	'BLM': '#EDA83B',
	'SMN': '#FFC942'
};

var roleColors = {
	'Tank': '#28459D',
	'Healer': '#116416',
	'DPS': '#E93325'
};

function getPieJobColors(data) {
	var slicesColor = {};

	for(var i=0; i < data.getNumberOfRows(); i++) {
		// label is in the first column of each row
		slicesColor[i] = {color: jobColors[data.getValue(i, 0)] };
	}
	
	return slicesColor;
}

function getJobColorCode(jobName) {
	return jobColors[jobName];
}

function getRoleColorCode(roleName) {
	return roleColors[roleName];
}