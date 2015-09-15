package com.pandore.ffxiv.characters.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pandore.ffxiv.characters.charts.JsonChartData;
import com.pandore.ffxiv.characters.persist.config.CharacterRepository;
import com.pandore.ffxiv.characters.persist.config.JobInfoRepository;
import com.pandore.ffxiv.characters.persist.entity.XIVCharacter;
import com.pandore.ffxiv.characters.persist.entity.XIVJob;
import com.pandore.ffxiv.characters.persist.entity.XIVJobInfoHistory;

@Controller
public class CharacterController implements BeanFactoryAware {
	
	CharacterRepository charRepo;
	JobInfoRepository jobInfoRepo;
	
	@Override
	public void setBeanFactory(BeanFactory context) throws BeansException {
		charRepo = context.getBean(CharacterRepository.class);
		jobInfoRepo = context.getBean(JobInfoRepository.class);
	}
	
	@RequestMapping(value = "/characters", method = RequestMethod.GET)
	public ModelAndView characters() {
		Iterable<XIVCharacter> chars = charRepo.findAll(new Sort("firstName", "lastName"));
		
		return new ModelAndView("characters").addObject("chars", chars);
	}
	
	@RequestMapping(value = "/character", method = RequestMethod.GET)
	public ModelAndView character(@RequestParam(required=true) Long chardId) {
		XIVCharacter character= charRepo.findOne(chardId);
		Integer mainJobLevel = jobInfoRepo.findFirstByCharacterAndJobOrderByDateDesc(character, character.getMainJob()).getiLevel();
		
		return new ModelAndView("character")
			.addObject("c", character)
			.addObject("mainJobLevel", mainJobLevel)
		;
	}
	
	@RequestMapping(value="/characterJobData", method = RequestMethod.GET)
	public @ResponseBody JsonChartData getJobChartData(@RequestParam(required=true) Long charId) {
		XIVCharacter character= charRepo.findOne(charId);
		
		Map<XIVJob, List<XIVJobInfoHistory>> allJobsInfos = new HashMap<XIVJob, List<XIVJobInfoHistory>>(character.getJobs().size() + 1);
		
		// Get all jobs info
		for (XIVJob job : character.getJobs()) {
			List<XIVJobInfoHistory> jobHistories = jobInfoRepo.findByCharacterAndJobOrderByDateAsc(character, job);
			if (allJobsInfos != null && !jobHistories.isEmpty()) {
				allJobsInfos.put(job, jobHistories);
			}
		}
		
		JsonChartData jsonData = new JsonChartData();
		jsonData.setJobInfoHistoryData(allJobsInfos);
		return jsonData;
	}
	
	
}