package com.pandore.ffxiv.characters.charts;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.pandore.ffxiv.characters.persist.entity.XIVJob;
import com.pandore.ffxiv.characters.persist.entity.XIVJobInfo;

public class JsonChartData {
	
	public class Column {
		private String id;
		private String label;
		private String type;
		
		public Column(String id, String label, String type) {
			this.id = id;
			this.label = label;
			this.type = type;
		}

		public String getId() {
			return id;
		}
		public String getLabel() {
			return label;
		}
		public String getType() {
			return type;
		}
	}
	
	public class Row {
		Cell[] c;
		public Row(Cell[] c) {
			this.c = c;
		}
		public Cell[] getC() {
			return c;
		}		
	}
	
	public class Cell {
		String v; // cell value
		String f; // version of value formatted for display
		public Cell(String v, String f) {
			this.v = v;
			this.f = f;
		}
		public String getV() {
			return v;
		}
		public void setV(String v) {
			this.v = v;
		}
		public String getF() {
			return f;
		}
		public void setF(String f) {
			this.f = f;
		}
	}
	
	
	private Column[] cols;
	private Row[] rows;
	
	public JsonChartData() {
	}
	
	public JsonChartData(Column[] cols, Row[] rows) {
		super();
		this.cols = cols;
		this.rows = rows;
	}
	
	public Column[] getCols() {
		return cols;
	}
	public Row[] getRows() {
		return rows;
	}
	
	protected String formatDate(Date date) {
		return "Date(" + date.getTime() + ")";
	}


	public void setJobDataForLineChart(Map<XIVJob, List<XIVJobInfo>> allJobsInfos) {
		Map<Date, Cell[]> dataByDate = new HashMap<Date, Cell[]>();
		
		TreeSet<XIVJob> sortedJobs = new TreeSet<XIVJob>(new Comparator<XIVJob>() {
			@Override
			public int compare(XIVJob j1, XIVJob j2) {
				return getNaturalOrdering(j1) - getNaturalOrdering(j2);
			}
			
			private int getNaturalOrdering(XIVJob job) {
				switch (job.getShortName().toUpperCase()) {
				case "PLD":
					return 0;
				case "WAR":
					return 1;
				case "WHM":
					return 2;
				case "SCH":
					return 3;
				case "MNK":
					return 4;
				case "DRG":
					return 5;
				case "NIN":
					return 6;
				case "BRD":
					return 7;
				case "BLM":
					return 8;
				case "SMN":
					return 9;
				default:
					return -1;
				}
			}
		});
		
		sortedJobs.addAll(allJobsInfos.keySet());
		
		
		int columnsSize = allJobsInfos.keySet().size() + 1; // first element is Date, then iLevels
		Column[] columns = new Column[columnsSize];
		
		columns[0] = new Column("date", "Date", "date");
		
		int jobColumn = 1;
		for (XIVJob job : sortedJobs) {
			columns[jobColumn] = new Column(job.getShortName(), job.getName(), "number");
			
			List<XIVJobInfo> jobInfoList = allJobsInfos.get(job);
			
			for (XIVJobInfo jobInfo : jobInfoList) {
				Date date = jobInfo.getDate();
				if (!dataByDate.containsKey(date)) {
					// Initialize row for this date
					Cell[] cells = new Cell[columnsSize];
					cells[0] = new Cell(formatDate(date), null);
					for (int i = 1; i < columnsSize; i++) { // fill with null Cells
						cells[i] = new Cell(null, null);
					}
					dataByDate.put(date, cells);
				}
				
				dataByDate.get(date)[jobColumn].setV(jobInfo.getiLevel().toString());
			}
			
			jobColumn++;
		}
		
		// Convert to Row array
		Row[] rows = new Row[dataByDate.size()];
		
		SortedSet<Date> sortedDates = new TreeSet<Date>();
		sortedDates.addAll(dataByDate.keySet());
		
		int i = 0;
		for (Date date : sortedDates) {
			rows[i] = new Row(dataByDate.get(date));
			i++;
		}
		
		// fill in gaps with previous values (google charts interpolateNulls does not really work)
		Cell[] previousValues = rows[0].getC();
		for (i = 1; i < rows.length; i++) {
			Cell[] cells = rows[i].getC();
			for (int j = 1; j < cells.length; j++) {
				if (cells[j].getV() == null) {
					cells[j].setV(previousValues[j].getV());
				}
			}
			previousValues = cells;
		}
		
		this.cols = columns;
		this.rows = rows;
	}
}
