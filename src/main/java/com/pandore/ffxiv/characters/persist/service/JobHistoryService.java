package com.pandore.ffxiv.characters.persist.service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pandore.ffxiv.characters.persist.config.JobInfoRepository;
import com.pandore.ffxiv.characters.persist.entity.XIVCharacter;
import com.pandore.ffxiv.characters.persist.entity.XIVJob;
import com.pandore.ffxiv.characters.persist.entity.XIVJobInfoHistory;

@Component
public class JobHistoryService {

	@Autowired
	private JobInfoRepository jobHistoryRepository;
	
	
	public List<XIVJobInfoHistory> findByCharacterAndJobOrderByDateAsc(XIVCharacter character, XIVJob job) {
		return jobHistoryRepository.findByCharacterAndJobOrderByDateAsc(character, job);
	}
	
	public XIVJobInfoHistory findFirstByCharacterAndJobOrderByDateDesc(XIVCharacter character, XIVJob job) {
		return jobHistoryRepository.findFirstByCharacterAndJobOrderByDateDesc(character, job);
	}
	
	@Transactional
	public List<XIVJobInfoHistory> getCurrentHistory() {
		List<BigInteger> idsBig = jobHistoryRepository.findAllCurrentIds();
		List<Long> idsLong = idsBig.stream().map(elt -> elt.longValue()).collect(Collectors.toList());
		
		return jobHistoryRepository.findByIdInOrderByLevelDescILevelDescDateAsc(idsLong);
	}
}
