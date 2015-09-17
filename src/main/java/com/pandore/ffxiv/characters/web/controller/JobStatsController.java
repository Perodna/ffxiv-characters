package com.pandore.ffxiv.characters.web.controller;

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
import com.pandore.ffxiv.characters.persist.service.JobService;

@Controller
public class JobStatsController implements BeanFactoryAware {
	
	@Autowired
	private JobService jobService;
	
	@Override
	public void setBeanFactory(BeanFactory context) throws BeansException {
		// Nothing to do
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView test() {
		return new ModelAndView("test");
	}
	
	@RequestMapping(value = "/jobs", method = RequestMethod.GET)
	public ModelAndView jobs() {		
		return new ModelAndView("jobs");
	}
	
	@RequestMapping(value="/jobsData", method = RequestMethod.GET)
	public @ResponseBody JsonChartData getJobChartData(@RequestParam(required=true) String jobsType) {
		Map<String, Long> jobData;
		
		switch (jobsType) {
		case "main": 
			jobData = jobService.findCountPerMainJob();
			break;
		case "alt":
			jobData = jobService.findCountPerAltJob();
			break;
		case "all":
			jobData = jobService.findCountPerJobAll();
			break;
		default:
			return null;
		}
		
		JsonChartData jsonData = new JsonChartData();
		jsonData.setJobStatsData(jobData);
		return jsonData;
	}
	

	
}
