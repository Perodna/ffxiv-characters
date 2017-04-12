package com.pandore.ffxiv.characters;

import java.util.List;

import com.pandore.ffxiv.characters.data.DataRetriever;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.pandore.ffxiv.characters.configuration.DataInit;
import com.pandore.ffxiv.characters.persist.config.CharacterRepository;
import com.pandore.ffxiv.characters.persist.config.JobInfoRepository;
import com.pandore.ffxiv.characters.persist.config.JobRepository;
import com.pandore.ffxiv.characters.persist.config.RoleRepository;
import com.pandore.ffxiv.characters.persist.entity.XIVCharacter;
import com.pandore.ffxiv.characters.persist.entity.XIVJobInfoHistory;

@SpringBootApplication
@EnableScheduling
public class Application {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		
		RoleRepository roleRepo = context.getBean(RoleRepository.class);
		JobRepository jobRepo = context.getBean(JobRepository.class);
		CharacterRepository charRepo = context.getBean(CharacterRepository.class);
		JobInfoRepository jobInfoRepo = context.getBean(JobInfoRepository.class);
		
		DataInit.init(roleRepo, jobRepo, charRepo, jobInfoRepo);
		
		
		
		DataRetriever dataRetriever = context.getBean(DataRetriever.class);
		dataRetriever.saveFreeCompany("9237023573225276133"); // small FC
//		dataRetriever.saveInwilis();
		
//		searchCheckup(charRepo, jobRepo, jobInfoRepo);
	}

	private static void searchCheckup(CharacterRepository charRepo, JobRepository jobRepo, JobInfoRepository jobInfoRepo) {
		// fetch all Characters
		Iterable<XIVCharacter> characters = charRepo.findAll();
		System.out.println("Characters found with findAll():");
		System.out.println("-------------------------------");
		for (XIVCharacter character : characters) {
			System.out.println(character);
		}
		System.out.println();

		// fetch an individual Character by ID
		XIVCharacter character = charRepo.findOne(1L);
		System.out.println("Character found with findOne(1L):");
		System.out.println("--------------------------------");
		System.out.println(character);
		System.out.println();

		XIVJobInfoHistory jobInfo = jobInfoRepo.findFirstByCharacterAndJobOrderByDateDesc(character, jobRepo.findFirstByShortName("SCH"));
		System.out.println(jobInfo);

		// fetch Characters by last name
		List<XIVCharacter> vivis = charRepo.findByFirstNameLikeIgnoreCase("%Vivi%");
		System.out.println("Characters found with findByFirstNameLikeIgnoreCase('%Vivi%'):");
		System.out.println("--------------------------------------------");
		for (XIVCharacter v : vivis) {
			System.out.println(v);
		}
	}
}
