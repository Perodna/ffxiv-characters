package com.pandore.ffxiv.characters.charts;

import com.pandore.ffxiv.characters.persist.entity.XIVJob;

public class JsonDataUtil {
	public static int getNaturalJobOrdering(XIVJob job) {
		return getNaturalJobOrdering(job.getShortName().toUpperCase());
	}
	
	public static int getNaturalJobOrdering(String job) {
		switch (job) {
			case "PLD":
				return 0;
			case "WAR":
				return 1;
			case "DRK":
				return 2;
			case "WHM":
				return 3;
			case "SCH":
				return 4;
			case "AST":
				return 5;
			case "MNK":
				return 6;
			case "DRG":
				return 7;
			case "NIN":
				return 8;
			case "BRD":
				return 9;
			case "MCH":
				return 10;
			case "BLM":
				return 11;
			case "SMN":
				return 12;
			default:
				return -1;
		}
	}
}
