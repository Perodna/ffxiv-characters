package com.pandore.ffxiv.characters;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.pandore.ffxiv.characters.configuration.DataInit;
import com.pandore.ffxiv.characters.persist.config.CharacterRepository;
import com.pandore.ffxiv.characters.persist.config.JobInfoRepository;
import com.pandore.ffxiv.characters.persist.config.JobRepository;
import com.pandore.ffxiv.characters.persist.config.RoleRepository;
import com.pandore.ffxiv.characters.persist.entity.XIVCharacter;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		
		RoleRepository roleRepo = context.getBean(RoleRepository.class);
		JobRepository jobRepo = context.getBean(JobRepository.class);
		CharacterRepository charRepo = context.getBean(CharacterRepository.class);
		JobInfoRepository jobInfoRepo = context.getBean(JobInfoRepository.class);
		
		DataInit.init(roleRepo, jobRepo, charRepo, jobInfoRepo);

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

        // fetch Characters by last name
        List<XIVCharacter> vivis = charRepo.findByFirstName("Vivi");
        System.out.println("Character found with findByFirstName('Vivi'):");
        System.out.println("--------------------------------------------");
        for (XIVCharacter v : vivis) {
            System.out.println(v);
        }
	}
}
