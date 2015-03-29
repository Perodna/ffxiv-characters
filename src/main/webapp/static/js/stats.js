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
		  	    '#28459D', // PLD
				'#425DAE', // WAR
				'#116416', // WHM
				'#54A759', // SCH
				'#E93325', // MNK
				'#FF594C', // DRG
				'#FF978F', // NIN
				'#FF814C', // BRD
				'#E98925', // BLM
				'#FFA84C' // SMN
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
		  	    '#28459D', // Tank
				'#116416', // Healer
				'#E93325' // DPS
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
      ["Tank", parseFloat(tankDiff.toFixed(4)), "#28459D"],
      ["Healer", parseFloat(healerDiff.toFixed(4)), "116416"],
      ["DPS", parseFloat(dpsDiff.toFixed(4)), "E93325"],
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