package com.pandore.ffxiv.characters.data;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pandore.ffxiv.characters.persist.config.CharacterRepository;
import com.pandore.ffxiv.characters.persist.config.JobInfoRepository;
import com.pandore.ffxiv.characters.persist.config.JobRepository;
import com.pandore.ffxiv.characters.persist.entity.XIVCharacter;
import com.pandore.ffxiv.characters.persist.entity.XIVJob;
import com.pandore.ffxiv.characters.persist.entity.XIVJobInfo;
import com.pandore.ffxiv.lodestone.entity.LSCharacter;
import com.pandore.ffxiv.lodestone.entity.LSFreeCompany;
import com.pandore.ffxiv.lodestone.parser.LodestoneParser;

@Component
public class DataRetriever {

	private static Logger logger = LoggerFactory.getLogger(DataRetriever.class);
	
	@Autowired
	CharacterRepository charRepo;
	@Autowired
	JobRepository jobRepo;
	@Autowired
	JobInfoRepository jobInfoRepo;
	
	
	@Scheduled(fixedRate = 15*60000)
	public void saveInwilis() {
		saveFreeCompany("9237023573225243170");
	}
	
	public void saveFreeCompany(String fcId) {
		LodestoneParser lodestoneParser = new LodestoneParser();
		
		LSFreeCompany fc;
		try {
			logger.info("Saving data for FC " + fcId);
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			fc = lodestoneParser.getFreeCompanyByid(fcId, true);
			
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
		List<XIVJob> jobs = jobRepo.findByShortName(lodestoneChar.getClassOrJob());
		if (jobs.isEmpty()) {
			return; // crafting/gathering classes, not supported yet
		}
		XIVJob currentJob = jobs.get(0);
		
		// get existing character from DB or create a new one		
		XIVCharacter character = charRepo.findFirstByLodestoneId(lodestoneChar.getId());
		if (character == null) {
			String[] names = lodestoneChar.getName().split(" ");
			character = new XIVCharacter(names[0], names[1], currentJob);
			character.setLodestoneId(lodestoneChar.getId());
			character = charRepo.save(character);
		} else {
			if (!character.hasJob(currentJob)) { // update list of character jobs
				character.addAltJob(currentJob);
				character = charRepo.save(character);
			}
		}
		
		// TODO save classes/jobs progression from list of classes on lodestone, once lodestone API provides the info 
		
		XIVJobInfo lodestoneInfo = new XIVJobInfo(character, currentJob, new Date(), lodestoneChar.getItemLevel(), lodestoneChar.getLevel());
		XIVJobInfo databaseInfo = jobInfoRepo.findFirstByCharacterAndJobOrderByDateDesc(character, currentJob);
		
		// Save job info from lodestone only if one of the following conditions is met:
		// - there was no previous info for this job
		// - character level is greater
		// - item level is different for characters that are level 50 or 60 (don't save iLevel when leveling)
		if (databaseInfo == null) {
			jobInfoRepo.save(lodestoneInfo);
		} else if (lodestoneInfo.getLevel() > databaseInfo.getLevel()) {
			jobInfoRepo.save(lodestoneInfo);
		} else if ((databaseInfo.getLevel() == 50 || databaseInfo.getLevel() == 60)
				&& lodestoneInfo.getiLevel() != databaseInfo.getiLevel()) {
			jobInfoRepo.save(lodestoneInfo);
		}
	}
}
