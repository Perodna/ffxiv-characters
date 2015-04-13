package com.pandore.ffxiv.characters.configuration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.pandore.ffxiv.characters.persist.config.CharacterRepository;
import com.pandore.ffxiv.characters.persist.config.JobInfoRepository;
import com.pandore.ffxiv.characters.persist.config.JobRepository;
import com.pandore.ffxiv.characters.persist.config.RoleRepository;
import com.pandore.ffxiv.characters.persist.entity.XIVCharacter;
import com.pandore.ffxiv.characters.persist.entity.XIVJob;
import com.pandore.ffxiv.characters.persist.entity.XIVJobInfo;
import com.pandore.ffxiv.characters.persist.entity.XIVRole;

public class DataInit {
	
	private static boolean generateRandomJobInfo = true;
	
	public static void init(RoleRepository roleRepo, JobRepository jobRepo, CharacterRepository charRepo, JobInfoRepository jobInfoRepo) {
		XIVRole tank = new XIVRole("Tank");
		XIVRole healer = new XIVRole("Healer");
		XIVRole dps = new XIVRole("DPS");
		roleRepo.save(tank);
		roleRepo.save(healer);
		roleRepo.save(dps);
		
		//tanks
		XIVJob pld = new XIVJob("Paladin", "PLD", tank);
		XIVJob war = new XIVJob("Warrior", "WAR", tank);
		// healers
		XIVJob whm = new XIVJob("White Mage", "WHM", healer);
		XIVJob sch = new XIVJob("Scholar", "SCH", healer);
		// melee dps
		XIVJob mnk = new XIVJob("Monk", "MNK", dps);
		XIVJob drg = new XIVJob("Dragoon", "DRG", dps);
		XIVJob nin = new XIVJob("Ninja", "NIN", dps);
		// range dps
		XIVJob brd = new XIVJob("Bard", "BRD", dps);
		XIVJob blm = new XIVJob("Black Mage", "BLM", dps);
		XIVJob smn = new XIVJob("Summoner", "SMN", dps);
		
		jobRepo.save(pld);
		jobRepo.save(war);
		jobRepo.save(whm);
		jobRepo.save(sch);
		jobRepo.save(mnk);
		jobRepo.save(drg);
		jobRepo.save(nin);
		jobRepo.save(brd);
		jobRepo.save(blm);
		jobRepo.save(smn);
		
		// save some characters
		saveNewChar(charRepo, jobInfoRepo, "Vivishu", "Vishu", sch, whm, blm, smn, war, mnk);
		saveNewChar(charRepo, jobInfoRepo, "Myobi", "Yui", whm, pld, smn);
        saveNewChar(charRepo, jobInfoRepo, "Itani", "Valkyrie", brd, whm);
        saveNewChar(charRepo, jobInfoRepo, "Reynhart", "Kristensen", pld, drg, war);
        saveNewChar(charRepo, jobInfoRepo, "Yukino", "Xilo", mnk, nin, drg);        
        saveNewChar(charRepo, jobInfoRepo, "Diiwica", "Faryuen", war, nin);
        saveNewChar(charRepo, jobInfoRepo, "Indy", "Thonyk", blm, whm);
        saveNewChar(charRepo, jobInfoRepo, "Shybo", "Natsu", drg, blm);
        saveNewChar(charRepo, jobInfoRepo, "Ookami", "Starlight", sch, blm);
        
        saveNewChar(charRepo, jobInfoRepo, "Aiorfus", "Vantopace", pld);
        saveNewChar(charRepo, jobInfoRepo, "Amuraa", "Namie", brd);
        saveNewChar(charRepo, jobInfoRepo, "Arah", "Nea", sch, whm, pld, smn, blm);
        saveNewChar(charRepo, jobInfoRepo, "Ashe", "Valky'rur", whm, drg);
        saveNewChar(charRepo, jobInfoRepo, "Azazel", "Angelus", mnk);
        saveNewChar(charRepo, jobInfoRepo, "Azrael", "Bigglesworth", blm, drg);
        saveNewChar(charRepo, jobInfoRepo, "Barbe", "Blanche", mnk);
        saveNewChar(charRepo, jobInfoRepo, "Celes", "Milember", sch, drg, war);
        saveNewChar(charRepo, jobInfoRepo, "Choko", "Chti", whm);
        saveNewChar(charRepo, jobInfoRepo, "Daenerys", "Dutiphon", whm, blm);
        saveNewChar(charRepo, jobInfoRepo, "Dams", "Yume", whm, brd, war);
        saveNewChar(charRepo, jobInfoRepo, "Dark", "Moon", brd, pld, war, sch, whm);
        saveNewChar(charRepo, jobInfoRepo, "Elmyra", "Ilfalna", whm, drg, blm, pld);
        saveNewChar(charRepo, jobInfoRepo, "Erranea", "Nishiru", brd, pld);
        saveNewChar(charRepo, jobInfoRepo, "Erzebeth", "Leyfidia", blm);
        saveNewChar(charRepo, jobInfoRepo, "Helysoa", "Chocolate", whm);
        saveNewChar(charRepo, jobInfoRepo, "Isilua", "Lyssalis", whm, blm);
        saveNewChar(charRepo, jobInfoRepo, "Jaina", "Solo", blm);
        saveNewChar(charRepo, jobInfoRepo, "Jee", "Salaheem", nin, whm, brd);
        saveNewChar(charRepo, jobInfoRepo, "John", "Sidfrid", drg, pld);
        saveNewChar(charRepo, jobInfoRepo, "Julian", "Manack", mnk, pld);
        saveNewChar(charRepo, jobInfoRepo, "Kahia", "Alistard", pld);
        saveNewChar(charRepo, jobInfoRepo, "Kats", "Eyes", whm, brd, pld);
        saveNewChar(charRepo, jobInfoRepo, "Lavos", "Zero", drg, war);
        saveNewChar(charRepo, jobInfoRepo, "Lilou", "Wiloh", mnk);
        saveNewChar(charRepo, jobInfoRepo, "Lina", "Eiky", drg);
        saveNewChar(charRepo, jobInfoRepo, "Linoa", "Mia", sch, smn);
        saveNewChar(charRepo, jobInfoRepo, "Lulukho", "Yume", brd);
        saveNewChar(charRepo, jobInfoRepo, "Melophage", "Whatelse", drg);
        saveNewChar(charRepo, jobInfoRepo, "Munto", "Valesti", drg);
        saveNewChar(charRepo, jobInfoRepo, "Mylady", "Rae", smn);
        saveNewChar(charRepo, jobInfoRepo, "Papi", "Tromblon", smn);
        saveNewChar(charRepo, jobInfoRepo, "Prad", "Zenzu", blm);
        saveNewChar(charRepo, jobInfoRepo, "Pyshalia", "Ishtar", brd, whm);
        saveNewChar(charRepo, jobInfoRepo, "Richard", "Invictus", drg, pld);
        saveNewChar(charRepo, jobInfoRepo, "Seyrin", "Dunedain", nin, brd);
        saveNewChar(charRepo, jobInfoRepo, "Sora", "Ryuji", drg, war);
        saveNewChar(charRepo, jobInfoRepo, "Squall'", "Alexander", pld, drg, sch);
        saveNewChar(charRepo, jobInfoRepo, "Tivice", "Conowell", pld, mnk);
	}
	
	private static void saveNewChar(CharacterRepository charRepo, JobInfoRepository jobInfoRepo, String firstName, String lastName, XIVJob main, XIVJob... alts) {
		XIVCharacter character = new XIVCharacter(firstName, lastName, main);
		character.addAltJobs(alts);
		charRepo.save(character);
		
		if (generateRandomJobInfo) {
			generateJobInfo(character, main, jobInfoRepo);
			for (XIVJob job : alts) {
				generateJobInfo(character, job, jobInfoRepo);
			}
		}
	}
	
	private static void generateJobInfo(XIVCharacter character, XIVJob job, JobInfoRepository jobInfoRepo) {
		System.out.println("Generating random job info for " + character.getFullName() + " - " + job.getShortName());
		
		List<XIVJobInfo> infos = new ArrayList<XIVJobInfo>();
		
		Random rng = new Random();
		
		Calendar cal = Calendar.getInstance();
		// set to 00:00:00.000
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		cal.add(Calendar.DATE, -50-rng.nextInt(50)); // start date between -100 days and -50 days
		Integer level = 50; // initial level
		Integer iLevel = 70; // initial iLevel
		
		infos.add(new XIVJobInfo(character, job, cal.getTime(), iLevel, level));
		
		for (int i = 0; i < 15; i++) {
			cal.add(Calendar.DATE, rng.nextInt(20));
			iLevel += rng.nextInt(5);
			if (iLevel > 130) { // max level 130
				infos.add(new XIVJobInfo(character, job, cal.getTime(), 130, level));
				break;
			}
			infos.add(new XIVJobInfo(character, job, cal.getTime(), iLevel, level));
		}
		
		jobInfoRepo.save(infos);
	}
}
