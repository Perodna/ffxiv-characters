package com.pandore.ffxiv.characters.charts;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.pandore.ffxiv.characters.persist.entity.XIVJob;
import com.pandore.ffxiv.characters.persist.entity.XIVJobInfoHistory;

public class JsonChartData {
	
	
	//--- Sub-classes ---------------------------------------------------------
	
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
		Object v; // cell value
		String f; // version of value formatted for display
		public Cell(Object v, String f) {
			this.v = v;
			this.f = f;
		}
		public Object getV() {
			return v;
		}
		public void setV(Object v) {
			this.v = v;
		}
		public String getF() {
			return f;
		}
		public void setF(String f) {
			this.f = f;
		}
	}
	
	
	//--- JsonCharData class --------------------------------------------------
	
	
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
	
	public void setJobStatsData(Map<String, Long> jobsStats) {
		// Set Columns
		Column[] columns = new Column[2];
		
		columns[0] = new Column("job", "Job", "string");
		columns[1] = new Column("popularity", "Popularity", "number");
		
		
		// Set rows, ordered by job
		TreeSet<String> sortedJobs = new TreeSet<String>(new Comparator<String>() {
			@Override
			public int compare(String j1, String j2) {
				return JsonDataUtil.getNaturalJobOrdering(j1) - JsonDataUtil.getNaturalJobOrdering(j2);
			}
		});
		sortedJobs.addAll(jobsStats.keySet());
		
		Row[] rows = new Row[jobsStats.size()];
		int i= 0;
		for (String job : sortedJobs) {
			Cell[] cells = new Cell[columns.length];
			cells[0] = new Cell(job, job);
			cells[1] = new Cell(jobsStats.get(job), null);
			rows[i] = new Row(cells);
			i++;
		}
		
		this.cols = columns;
		this.rows = rows;
	}
	
	public void setRoleStatsData(Map<String, Long> rolesStats) {
		// Set Columns
		Column[] columns = new Column[2];
		
		columns[0] = new Column("role", "Role", "string");
		columns[1] = new Column("popularity", "Popularity", "number");
		
		
		// Set rows, ordered by role
		TreeSet<String> sortedRoles = new TreeSet<String>(new Comparator<String>() {
			@Override
			public int compare(String r1, String r2) {
				return JsonDataUtil.getNaturalRoleOrdering(r1) - JsonDataUtil.getNaturalRoleOrdering(r2);
			}
		});
		sortedRoles.addAll(rolesStats.keySet());
		
		Row[] rows = new Row[rolesStats.size()];
		int i= 0;
		for (String role : sortedRoles) {
			Cell[] cells = new Cell[columns.length];
			cells[0] = new Cell(role, role);
			cells[1] = new Cell(rolesStats.get(role), null);
			rows[i] = new Row(cells);
			i++;
		}
		
		this.cols = columns;
		this.rows = rows;
	}

	public void setJobInfoHistoryData(Map<XIVJob, List<XIVJobInfoHistory>> allJobsInfos) {
		Map<Date, Cell[]> dataByDate = new HashMap<Date, Cell[]>();
		
		TreeSet<XIVJob> sortedJobs = new TreeSet<XIVJob>(new Comparator<XIVJob>() {
			@Override
			public int compare(XIVJob j1, XIVJob j2) {
				return JsonDataUtil.getNaturalJobOrdering(j1) - JsonDataUtil.getNaturalJobOrdering(j2);
			}
		});
		
		sortedJobs.addAll(allJobsInfos.keySet());
		
		
		int columnsSize = allJobsInfos.keySet().size() + 1; // first element is Date, then iLevels
		Column[] columns = new Column[columnsSize];
		
		columns[0] = new Column("date", "Date", "date");
		
		int jobColumn = 1;
		for (XIVJob job : sortedJobs) {
			columns[jobColumn] = new Column(job.getShortName(), job.getName(), "number");
			
			List<XIVJobInfoHistory> jobInfoList = allJobsInfos.get(job);
			
			for (XIVJobInfoHistory jobInfo : jobInfoList) {
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
				
				dataByDate.get(date)[jobColumn].setV(jobInfo.getiLevel());
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
