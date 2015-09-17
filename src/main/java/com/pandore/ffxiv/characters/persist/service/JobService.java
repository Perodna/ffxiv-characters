package com.pandore.ffxiv.characters.persist.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pandore.ffxiv.characters.persist.config.JobRepository;
import com.pandore.ffxiv.characters.persist.entity.XIVJob;

@Component
public class JobService {
	
	@Autowired
	private JobRepository jobRepository;
	
	public List<XIVJob> findByShortName(String shortName) {
		return jobRepository.findByShortName(shortName);
	}
	
	
	public Map<String, Long> findCountPerMainJob() {
		List<Object[]> distribution = jobRepository.findCountPerMainJob();
		
		Map<String, Long> res = new HashMap<String, Long>(distribution.size()) {
			private static final long serialVersionUID = 4018418051779615666L;

			@Override
			public Long get(Object key) {
				if (containsKey(key)) {
					return super.get(key);
				} else {
					return Long.valueOf(0);
				}
			}
		};
		
		for (Object[] line : distribution) {
			res.put(((XIVJob) line[0]).getShortName(), (Long) line[1]);
//			System.out.println(((XIVJob) line[0]).getShortName() +  " - " + (Long) line[1]);
		}
		return res;
	}
	
	public Map<String, Long> findCountPerAltJob() {
		List<Object[]> distribution = jobRepository.findCountPerAltJob();
		
		Map<String, Long> res = new HashMap<String, Long>(distribution.size()) {
			private static final long serialVersionUID = 1977258208549043611L;

			@Override
			public Long get(Object key) {
				if (containsKey(key)) {
					return super.get(key);
				} else {
					return Long.valueOf(0);
				}
			}
		};
		
		for (Object[] line : distribution) {
			res.put(((String) line[0]), Long.valueOf(((BigInteger) line[1]).longValue()));
		}
		return res;
	}
	
	public Map<String, Long> findCountPerJobAll() {
		List<Object[]> distribution = jobRepository.findCountPerJobAll();
		
		Map<String, Long> res = new HashMap<String, Long>(distribution.size()) {
			private static final long serialVersionUID = -5590192955163564062L;

			@Override
			public Long get(Object key) {
				if (containsKey(key)) {
					return super.get(key);
				} else {
					return Long.valueOf(0);
				}
			}
		};
		
		for (Object[] line : distribution) {
			res.put(((String) line[0]), Long.valueOf(((BigInteger) line[1]).longValue()));
		}
		return res;
	}

}
