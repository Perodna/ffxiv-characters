package com.pandore.ffxiv.characters.data;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pandore.ffxiv.characters.persist.entity.XIVCharacter;
import com.pandore.ffxiv.characters.persist.entity.XIVJob;
import com.pandore.ffxiv.characters.persist.entity.XIVJobInfoHistory;
import com.pandore.ffxiv.characters.persist.service.CharacterService;
import com.pandore.ffxiv.characters.persist.service.JobHistoryService;
import com.pandore.ffxiv.characters.persist.service.JobService;
import com.pandore.ffxiv.lodestone.entity.LSCharacter;
import com.pandore.ffxiv.lodestone.entity.LSFreeCompany;
import com.pandore.ffxiv.lodestone.parser.LodestoneParser;

@Component
public class DataRetriever {

	private static Logger logger = LoggerFactory.getLogger(DataRetriever.class);
	
	@Autowired
	private CharacterService characterService;
	@Autowired
	private JobService jobService;
	@Autowired
	private JobHistoryService historyService;
	
	
	@Scheduled(fixedRate = 15*60000)
	public void saveInwilis() {
		saveFreeCompany("9237023573225243170");
	}
	
//	@Scheduled(fixedRate = 5*60000)
//	public void saveSanctum() {
//		saveFreeCompany("9237023573225275675");
//	}
	
	public void saveFreeCompany(String fcId) {
		LodestoneParser lodestoneParser = new LodestoneParser();
		
		LSFreeCompany fc;
		try {
			logger.info("Saving data for FC " + fcId);
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			fc = lodestoneParser.getFreeCompanyByid(fcId, true);
			
			logger.info("Saving characters for FC " + fcId);
			for (LSCharacter c : fc.getMembers()) {
				saveCharacter(c);
			}
			stopWatch.stop();
			logger.info("Saved data for FC " + fc.getName()  + " [Total time: " + stopWatch.toString() +"]");
		} catch (Exception e) {
			logger.error("Could not save free company data from lodestone", e);
		}
	}

	private void saveCharacter(LSCharacter lodestoneChar) {
		// get job
		XIVJob currentJob = jobService.findByShortName(lodestoneChar.getClassOrJob());
		if (currentJob == null) {
			return; // crafting/gathering classes, not supported yet
		}
		
		// get existing character from DB or create a new one		
		XIVCharacter character = characterService.findFirstByLodestoneId(lodestoneChar.getId());
		if (character == null) {
			String[] names = lodestoneChar.getName().split(" ");
			character = new XIVCharacter(names[0], names[1], currentJob);
			character.setLodestoneId(lodestoneChar.getId());
			character = characterService.save(character);
		} else {
			if (!character.hasJob(currentJob)) { // update list of character jobs
				character.addJob(currentJob);
				character = characterService.save(character);
			}
		}
		
		// TODO save classes/jobs progression from list of classes on lodestone, once lodestone API provides the info 
		
		XIVJobInfoHistory lodestoneInfo = new XIVJobInfoHistory(character, currentJob, new Date(), lodestoneChar.getItemLevel(), lodestoneChar.getLevel());
		XIVJobInfoHistory databaseInfo = historyService.findLastest(character, currentJob);
		
		// Save job info from lodestone only if one of the following conditions is met:
		// - there was no previous info for this job
		// - character level is greater
		// - item level is different for characters that are level 50 or 60 (don't save iLevel when leveling)
		if (databaseInfo == null) {
			historyService.save(lodestoneInfo);
		} else if (lodestoneInfo.getLevel() > databaseInfo.getLevel()) {
			historyService.save(lodestoneInfo);
		} else if ((databaseInfo.getLevel() == 50 || databaseInfo.getLevel() == 60)
				&& (lodestoneInfo.getiLevel() != databaseInfo.getiLevel())) {
			historyService.save(lodestoneInfo);
		}
	}
}
