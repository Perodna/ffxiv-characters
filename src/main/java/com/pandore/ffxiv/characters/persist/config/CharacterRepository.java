package com.pandore.ffxiv.characters.persist.config;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.pandore.ffxiv.characters.persist.entity.XIVCharacter;

//@RepositoryRestResource(collectionResourceRel="characters", path="characters")
public interface CharacterRepository extends PagingAndSortingRepository<XIVCharacter, Long> {
	
	List<XIVCharacter> findByFirstName(@Param("firstName") String lastName);
	List<XIVCharacter> findByLastName(@Param("lastName") String lastName);
	
	@Query("select c from XIVCharacter c where c.mainJob.id = :mainJobId")
	List<XIVCharacter> findByMainJob(@Param("mainJobId") long mainJobId);	
}
