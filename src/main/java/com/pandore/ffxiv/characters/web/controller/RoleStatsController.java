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
import com.pandore.ffxiv.characters.persist.service.RoleService;

@Controller
public class RoleStatsController implements BeanFactoryAware  {

	@Autowired
	private RoleService roleService;
		
	@Override
	public void setBeanFactory(BeanFactory context) throws BeansException {
		// Nothing to do
	}
	
	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public ModelAndView roles() {
		return new ModelAndView("roles");
	}
	
	@RequestMapping(value="/rolesData", method = RequestMethod.GET)
	public @ResponseBody JsonChartData getRoleChartData(@RequestParam(required=true) String rolesType) {
		Map<String, Long> rolesData;
		
		switch (rolesType) {
		case "main":
			rolesData = roleService.findCountPerMainRole();
			break;
		case "alt":
			rolesData = roleService.findCountPerAltRole();
			break;
		case "all":
			rolesData = roleService.findCountPerRoleAll();
			break;
		default:
			return null;
		}
		
		JsonChartData jsonData = new JsonChartData();
		jsonData.setRoleStatsData(rolesData);
		return jsonData;
	}
	

}
