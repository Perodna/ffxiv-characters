function drawJobsPie(rows, title, div) {
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Job');
    data.addColumn('number', 'Characters');
    data.addRows(rows);
    var options = {
    	    'title': title,
    	    'pieHole': 0.5, // make it a donut
    	    'height': 400,
    	    'width': '100%',
    	    'chartArea': { width:'75%',height:'75%'},
	  	    'colors': [
		  	    getJobColorCode('PLD'),
				getJobColorCode('WAR'),
				getJobColorCode('WHM'),
				getJobColorCode('SCH'),
				getJobColorCode('MNK'),
				getJobColorCode('DRG'),
				getJobColorCode('NIN'),
				getJobColorCode('BRD'),
				getJobColorCode('BLM'),
				getJobColorCode('SMN')
			]
   	};
    var chart = new google.visualization.PieChart(document.getElementById(div));
    chart.draw(data, options);
};

function drawRolesPie(rows, title, div) {
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Role');
    data.addColumn('number', 'Characters');
    data.addRows(rows);
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

function drawRolesDiffBars(numbers, title, div) {
	var total = 0;
	
	for (var i = 0; i < numbers.length; i++) {
		total += numbers[i][1];
	}
	
	var tankDiff = numbers[0][1] / total / 0.25 - 1;
	var healerDiff = numbers[1][1] / total / 0.25 - 1;
	var dpsDiff = numbers[2][1] / total / 0.5 - 1;
	
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

function getValueAt(column, dataTable, row) {
    return (dataTable.getValue(row, column) * 100).toFixed(2) + ' %';
}

function getSeriesForLineChartData(dataTable) {
	var series = {};
	
	var view = new google.visualization.DataView(dataTable);
	
	for (var i=1; i < view.getNumberOfColumns(); i++) {
		series[i-1] = { color: getJobColorCode(view.getColumnId(i)), job: view.getColumnId(i) };
	}
	
	console.log(series);
	
	return series;
}

function getJobColorCode(jobName) {
	switch (jobName) {
	case 'PLD':
		return '#28459D';
	case 'WAR':
		return '#425DAE';
	case 'WHM':
		return '#116416';
	case 'SCH':
		return '#54A759';
	case 'MNK':
		return '#E93325';
	case 'DRG':
		return '#FF594C';
	case 'NIN':
		return '#FF978F';
	case 'BRD':
		return '#FF814C';
	case 'BLM':
		return '#E98925';
	case 'SMN':
		return '#FFA84C';
	default:
		return null;
	}
}

function getRoleColorCode(roleName) {
	switch (roleName) {
	case 'Tank':
		return '#28459D';
	case 'Healer':
		return '#116416';
	case 'DPS':
		return '#E93325';
	default:
		return null;
	}
}