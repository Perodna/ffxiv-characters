package com.pandore.ffxiv.characters.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pandore.ffxiv.characters.persist.config.CharacterRepository;
import com.pandore.ffxiv.characters.persist.config.JobInfoRepository;
import com.pandore.ffxiv.characters.persist.entity.XIVCharacter;
import com.pandore.ffxiv.characters.persist.entity.XIVJobInfo;

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
	public ModelAndView character() {
		XIVCharacter character= charRepo.findOne(1l);
		String mainJobLevel = "unknown";
		
		List<XIVJobInfo> mainJobInfos = jobInfoRepo.findByCharacterAndJobOrderByDateAsc(character, character.getMainJob());
		if (mainJobInfos != null && !mainJobInfos.isEmpty()) {
			mainJobLevel = mainJobInfos.get(mainJobInfos.size() - 1).getiLevel().toString();
		}
		
		List<List<XIVJobInfo>> allJobsInfos = new ArrayList<>(character.getAltJobs().size() + 1);
		allJobsInfos.add(mainJobInfos);
		
		for (int i = 0; i < character.getAltJobs().size(); i++) {
			List<XIVJobInfo> altJobInfos = jobInfoRepo.findByCharacterAndJobOrderByDateAsc(character, character.getAltJobs().get(i));
			allJobsInfos.add(altJobInfos);
		}
		
		return new ModelAndView("character")
			.addObject("c", character)
			.addObject("mainJobLevel", mainJobLevel)
			.addObject("jobInfos", allJobsInfos)
		;
	}
}