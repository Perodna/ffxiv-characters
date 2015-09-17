package com.pandore.ffxiv.characters.persist.config;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.pandore.ffxiv.characters.persist.entity.XIVCharacter;

public interface CharacterRepository extends PagingAndSortingRepository<XIVCharacter, Long> {
	
	List<XIVCharacter> findAllByOrderByFirstNameAscLastNameAsc();
	List<XIVCharacter> findByFirstNameLikeIgnoreCase(@Param("firstName") String firstName);
	List<XIVCharacter> findByLastNameLikeIgnoreCase(@Param("lastName") String lastName);
	List<XIVCharacter> findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);
	XIVCharacter findFirstByLodestoneId(@Param("lodestoneId") String lodestoneId);
	
	@Query("select c from XIVCharacter c where c.mainJob.id = :mainJobId")
	List<XIVCharacter> findByMainJob(@Param("mainJobId") long mainJobId);
	
}
