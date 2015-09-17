package com.pandore.ffxiv.characters.persist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pandore.ffxiv.characters.persist.config.CharacterRepository;
import com.pandore.ffxiv.characters.persist.entity.XIVCharacter;

@Component
public class CharacterService {

	@Autowired
	private CharacterRepository characterRepository;
	
	public XIVCharacter findOne(Long charId) {
		return characterRepository.findOne(charId);
	}
	
	public List<XIVCharacter> findAll() {
		return characterRepository.findAllByOrderByFirstNameAscLastNameAsc();
	}
	
	public List<XIVCharacter> findByFirstNameLikeIgnoreCase(String firstName) {
		return characterRepository.findByFirstNameLikeIgnoreCase(firstName);
	}
	
	public List<XIVCharacter> findByLastNameLikeIgnoreCase(String lastName) {
		return characterRepository.findByLastNameLikeIgnoreCase(lastName);
	}
	
	public List<XIVCharacter> findByFirstNameAndLastName(String firstName, String lastName) {
		return characterRepository.findByFirstNameAndLastName(firstName, lastName);
	}
	
	public XIVCharacter findFirstByLodestoneId(String lodestoneId) {
		return characterRepository.findFirstByLodestoneId(lodestoneId);
	}
	
	public List<XIVCharacter> findByMainJob(long mainJobId) {
		return characterRepository.findByMainJob(mainJobId);
	}
}
