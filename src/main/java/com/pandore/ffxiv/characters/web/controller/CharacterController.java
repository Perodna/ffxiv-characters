package com.pandore.ffxiv.characters.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pandore.ffxiv.characters.charts.JsonChartData;
import com.pandore.ffxiv.characters.persist.entity.XIVCharacter;
import com.pandore.ffxiv.characters.persist.entity.XIVJob;
import com.pandore.ffxiv.characters.persist.entity.XIVJobInfoHistory;
import com.pandore.ffxiv.characters.persist.service.CharacterService;
import com.pandore.ffxiv.characters.persist.service.JobHistoryService;
import com.pandore.ffxiv.characters.persist.service.JobService;
import com.pandore.ffxiv.characters.persist.service.RoleService;

@Controller
public class CharacterController implements BeanFactoryAware {
	
	@Autowired
	private CharacterService characterService;
	@Autowired
	private JobHistoryService historyService;
	@Autowired
	private JobService jobService;
	@Autowired
	private RoleService roleService;
	
	@Override
	public void setBeanFactory(BeanFactory context) throws BeansException {
		// Nothing to do
	}
	
	@RequestMapping(value = "/characters", method = RequestMethod.GET)
	public ModelAndView characters() {
		Iterable<XIVCharacter> chars = characterService.findAll();
		
		return new ModelAndView("characters").addObject("chars", chars);
	}
	
	@RequestMapping(value = "/character", method = RequestMethod.GET)
	public ModelAndView character(@RequestParam(required=true) Long charId) {
		XIVCharacter character= characterService.findOne(charId);
		Integer mainJobLevel = historyService.findLastest(character, character.getMainJob()).getiLevel();
		
		return new ModelAndView("character")
			.addObject("c", character)
			.addObject("mainJobLevel", mainJobLevel)
		;
	}
	
	@RequestMapping(value = "/characterLevels", method = RequestMethod.GET)
	public ModelAndView characterLevels(@RequestParam(required=false) String job, @RequestParam(required=false) String role) {
		List<XIVJobInfoHistory> levels;
		if (job != null && !job.isEmpty()) {
			levels = historyService.getLatestHistoryByJob(job);
		} else if (role != null && !role.isEmpty()) {
			levels = historyService.getLatestHistoryByRole(role);
		} else {
			levels = historyService.getLatestHistory();
		}
		
		return new ModelAndView("characterLevels")
			.addObject("levels", levels)
			.addObject("roles", roleService.findAll())
			.addObject("tanks", jobService.findByRoleIgnoreClasses("Tank"))
			.addObject("healers", jobService.findByRoleIgnoreClasses("Healer"))
			.addObject("dps", jobService.findByRoleIgnoreClasses("DPS"))
		;
	}
	
	@RequestMapping(value="/characterJobData", method = RequestMethod.GET)
	public @ResponseBody JsonChartData getJobChartData(@RequestParam(required=true) Long charId) {
		XIVCharacter character= characterService.findOne(charId);
		
		Map<XIVJob, List<XIVJobInfoHistory>> allJobsInfos = new HashMap<XIVJob, List<XIVJobInfoHistory>>(character.getJobs().size() + 1);
		
		// Get all jobs info
		for (XIVJob job : character.getJobs()) {
			List<XIVJobInfoHistory> jobHistories = historyService.findAll(character, job);
			if (allJobsInfos != null && !jobHistories.isEmpty()) {
				allJobsInfos.put(job, jobHistories);
			}
		}
		
		JsonChartData jsonData = new JsonChartData();
		jsonData.setJobInfoHistoryData(allJobsInfos);
		return jsonData;
	}
	
	
}