package com.pandore.ffxiv.characters.persist.service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pandore.ffxiv.characters.persist.config.JobInfoRepository;
import com.pandore.ffxiv.characters.persist.entity.XIVJobInfoHistory;

@Component
public class JobHistoryService {

	@Autowired
	private JobInfoRepository jobHistoryRepository;
	
	@Transactional
	public List<XIVJobInfoHistory> getCurrentHistory() {
		List<BigInteger> idsBig = jobHistoryRepository.findAllCurrentIds();
		List<Long> idsLong = idsBig.stream().map(elt -> elt.longValue()).collect(Collectors.toList());
		
		return jobHistoryRepository.findByIdInOrderByLevelDescILevelDescDateAsc(idsLong);
	}
}
