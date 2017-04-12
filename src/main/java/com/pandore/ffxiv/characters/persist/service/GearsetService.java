package com.pandore.ffxiv.characters.persist.service;

import com.pandore.ffxiv.characters.persist.config.GearsetRepository;
import com.pandore.ffxiv.characters.persist.entity.XIVCharacter;
import com.pandore.ffxiv.characters.persist.entity.XIVGearset;
import com.pandore.ffxiv.characters.persist.entity.XIVJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GearsetService {

	@Autowired
	private GearsetRepository gearsetRepository;

	public XIVGearset save(XIVGearset gearset) {
		return gearsetRepository.save(gearset);
	}

	public XIVGearset findByCharacterAndJob(XIVCharacter character, XIVJob job) {
		return gearsetRepository.findOneByCharacterAndJob(character, job);
	}
}
