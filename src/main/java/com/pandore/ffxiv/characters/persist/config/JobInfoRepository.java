package com.pandore.ffxiv.characters.persist.config;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.pandore.ffxiv.characters.persist.entity.XIVCharacter;
import com.pandore.ffxiv.characters.persist.entity.XIVJob;
import com.pandore.ffxiv.characters.persist.entity.XIVJobInfoHistory;


public interface JobInfoRepository extends PagingAndSortingRepository<XIVJobInfoHistory, Long> {

	List<XIVJobInfoHistory> findByCharacterAndJobOrderByDateAsc(@Param("character") XIVCharacter character, @Param("job") XIVJob job);
	
	XIVJobInfoHistory findFirstByCharacterAndJobOrderByDateDesc(@Param("character") XIVCharacter character, @Param("job") XIVJob job);
}
