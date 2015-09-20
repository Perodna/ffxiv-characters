package com.pandore.ffxiv.characters.persist.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pandore.ffxiv.characters.persist.config.RoleRepository;
import com.pandore.ffxiv.characters.persist.entity.XIVRole;

@Component
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public List<XIVRole> findAll() {
		return roleRepository.findAllByOrderByIdAsc();
	}

	public XIVRole findByName(String name) {
		return roleRepository.findFirstByName(name);
	}
	
	public Map<String, Long> findCountPerMainRole() {
		List<Object[]> distribution = roleRepository.findCountPerMainRole();
		
		Map<String, Long> res = new HashMap<String, Long>(distribution.size()) {
			private static final long serialVersionUID = -3631581506704768798L;

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
			res.put(((XIVRole) line[0]).getName(), (Long) line[1]);
		}
		return res;
	}
	
	public Map<String, Long> findCountPerAltRole() {
		List<Object[]> distribution = roleRepository.findCountPerAltRole();
		
		Map<String, Long> res = new HashMap<String, Long>(distribution.size()) {
			private static final long serialVersionUID = -3238248724475411395L;

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
	
	public Map<String, Long> findCountPerRoleAll() {
		List<Object[]> distribution = roleRepository.findCountPerRoleAll();
		
		Map<String, Long> res = new HashMap<String, Long>(distribution.size()) {
			private static final long serialVersionUID = 3771277183593538507L;

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
