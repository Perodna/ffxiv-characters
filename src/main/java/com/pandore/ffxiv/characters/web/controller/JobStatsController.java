package com.pandore.ffxiv.characters.web.controller;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pandore.ffxiv.characters.charts.JsonChartData;
import com.pandore.ffxiv.characters.persist.config.JobRepository;
import com.pandore.ffxiv.characters.persist.entity.XIVJob;

@Controller
public class JobStatsController implements BeanFactoryAware {
	JobRepository jobRepo;
	
	@Override
	public void setBeanFactory(BeanFactory context) throws BeansException {
		jobRepo = context.getBean(JobRepository.class);
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView test() {
		return new ModelAndView("test");
	}
	
	@RequestMapping(value = "/jobs", method = RequestMethod.GET)
	public ModelAndView jobs() {
//		List<Object[]> mainJobs = jobRepo.findCountPerMainJob();
//		List<Object[]> altJobs = jobRepo.findCountPerAltJob();
//		List<Object[]> allJobs = jobRepo.findCountPerJobAll();
		
		return new ModelAndView("jobs");
//			.addObject("mainJobs", transformMainToMap(mainJobs))
//			.addObject("altJobs", transformAltToMap(altJobs))
//			.addObject("allJobs", transformAllToMap(allJobs));
	}
	
	@RequestMapping(value="/jobsData", method = RequestMethod.GET)
	public @ResponseBody JsonChartData getJobChartData(@RequestParam(required=true) String jobsType) {
		Map<String, Long> jobData;
		
		switch (jobsType) {
		case "main":
			List<Object[]> mainJobs = jobRepo.findCountPerMainJob();
			jobData = transformMainToMap(mainJobs);
			break;
		case "alt":
			List<Object[]> altJobs = jobRepo.findCountPerAltJob();
			jobData = transformAltToMap(altJobs);
			break;
		case "all":
			List<Object[]> allJobs = jobRepo.findCountPerJobAll();
			jobData = transformAllToMap(allJobs);
			break;
		default:
			return null;
		}
		
		JsonChartData jsonData = new JsonChartData();
		jsonData.setJobStatsData(jobData);
		return jsonData;
	}
	

	private Map<String, Long> transformMainToMap(List<Object[]> distribution) {
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
	
	private Map<String, Long> transformAltToMap(List<Object[]> distribution) {
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
	
	private Map<String, Long> transformAllToMap(List<Object[]> distribution) {
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
			res.put(((String) line[0]), Long.valueOf(((BigDecimal) line[1]).longValue()));
		}
		return res;
	}
}
