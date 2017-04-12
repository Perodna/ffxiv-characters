package com.pandore.ffxiv.characters.persist.config;

import com.pandore.ffxiv.characters.persist.entity.XIVCharacter;
import com.pandore.ffxiv.characters.persist.entity.XIVGearset;
import com.pandore.ffxiv.characters.persist.entity.XIVJob;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface GearsetRepository extends PagingAndSortingRepository<XIVGearset, Long> {

	XIVGearset findOneByCharacterAndJob(@Param("character") XIVCharacter character, @Param("job") XIVJob job);
}
