package com.pandore.ffxiv.characters.web.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pandore.ffxiv.characters.persist.config.CharacterRepository;
import com.pandore.ffxiv.characters.persist.config.JobRepository;
import com.pandore.ffxiv.characters.persist.config.RoleRepository;
import com.pandore.ffxiv.characters.persist.entity.XIVRole;

@Controller
public class RoleStatsController implements BeanFactoryAware  {

	JobRepository jobRepo;
	CharacterRepository charRepo;
	RoleRepository roleRepo;
	
	@Override
	public void setBeanFactory(BeanFactory context) throws BeansException {
		charRepo = context.getBean(CharacterRepository.class);
		roleRepo = context.getBean(RoleRepository.class);
	}
	
	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public ModelAndView roles() {
		List<Object[]> mainRoles = roleRepo.findCountPerMainRole();
		List<Object[]> altRoles = roleRepo.findCountPerAltRole();
		List<Object[]> allRoles = roleRepo.findCountPerRoleAll();
		
		return new ModelAndView("roles")
			.addObject("mainRoles", transformMainToMap(mainRoles))
			.addObject("altRoles", transformAltToMap(altRoles))
			.addObject("allRoles", transformAllToMap(allRoles));
	}
	
	private Map<String, Long> transformMainToMap(List<Object[]> distribution) {
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
	
	private Map<String, Long> transformAltToMap(List<Object[]> distribution) {
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
	
	private Map<String, Long> transformAllToMap(List<Object[]> distribution) {
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
