package com.pandore.ffxiv.characters.persist.service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pandore.ffxiv.characters.persist.config.JobInfoRepository;
import com.pandore.ffxiv.characters.persist.config.JobRepository;
import com.pandore.ffxiv.characters.persist.config.RoleRepository;
import com.pandore.ffxiv.characters.persist.entity.XIVCharacter;
import com.pandore.ffxiv.characters.persist.entity.XIVJob;
import com.pandore.ffxiv.characters.persist.entity.XIVJobInfoHistory;
import com.pandore.ffxiv.characters.persist.entity.XIVRole;

@Component
public class JobHistoryService {

	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private JobInfoRepository jobHistoryRepository;
	
	
	public XIVJobInfoHistory save(XIVJobInfoHistory info) {
		return jobHistoryRepository.save(info);
	}
	
	/**
	 * Find all history for a character and a specific job
	 * @param character
	 * @param job
	 * @return A list of XIVJobInfoHistory, order from oldest to most recent
	 */
	public List<XIVJobInfoHistory> findAll(XIVCharacter character, XIVJob job) {
		return jobHistoryRepository.findByCharacterAndJobOrderByDateAsc(character, job);
	}
	
	/**
	 * Find latest information for a character for a specific job
	 * @param character
	 * @param job
	 * @return
	 */
	public XIVJobInfoHistory findLastest(XIVCharacter character, XIVJob job) {
		return jobHistoryRepository.findFirstByCharacterAndJobOrderByDateDesc(character, job);
	}
	
	@Transactional
	public List<XIVJobInfoHistory> getLatestHistory() {
		List<BigInteger> idsBig = jobHistoryRepository.findAllCurrentIds();
		List<Long> idsLong = idsBig.stream().map(elt -> elt.longValue()).collect(Collectors.toList());
		
		return jobHistoryRepository.findByIdInOrderByLevelDescILevelDescDateAsc(idsLong);
	}
	
	@Transactional
	public List<XIVJobInfoHistory> getLatestHistoryByJob(String jobShortName) {
		XIVJob job = jobRepository.findFirstByShortName(jobShortName);
		List<BigInteger> idsBig = jobHistoryRepository.findAllCurrentIdsByJob(job.getId());
		List<Long> idsLong = idsBig.stream().map(elt -> elt.longValue()).collect(Collectors.toList());
		
		return jobHistoryRepository.findByIdInOrderByLevelDescILevelDescDateAsc(idsLong);
	}
	
	@Transactional
	public List<XIVJobInfoHistory> getLatestHistoryByRole(String roleName) {
		XIVRole role = roleRepository.findFirstByName(roleName);
		List<BigInteger> idsBig = jobHistoryRepository.findAllCurrentIdsByRole(role.getId());
		List<Long> idsLong = idsBig.stream().map(elt -> elt.longValue()).collect(Collectors.toList());
		
		return jobHistoryRepository.findByIdInOrderByLevelDescILevelDescDateAsc(idsLong);
	}
}
