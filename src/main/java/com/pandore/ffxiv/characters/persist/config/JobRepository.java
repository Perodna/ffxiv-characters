package com.pandore.ffxiv.characters.persist.config;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.pandore.ffxiv.characters.persist.entity.XIVJob;

public interface JobRepository extends PagingAndSortingRepository<XIVJob, Long> {
	
	List<XIVJob> findByShortName(@Param("shortname") String shortName);
	
	@Query("select c.mainJob, count(c.id) from XIVCharacter c, XIVJob j where c.mainJob = j and j.isClass = false group by c.mainJob.id")
	List<Object[]> findCountPerMainJob();
	
	@Query(value = "select j.shortname, count(cj.character_id) from job j, char_jobs cj, character c "
			+ "where j.id = cj.job_id and j.is_class = false "
			+ "and c.id = cj.character_id and c.main_job_id <> cj.job_id "
			+ "group by cj.job_id",
			nativeQuery = true)
	List<Object[]> findCountPerAltJob();
	
	@Query(value = "select j.shortname, count(cj.character_id) from job j, char_jobs cj "
			+ "where j.id = cj.job_id and j.is_class = false group by cj.job_id",
			nativeQuery = true)
	List<Object[]> findCountPerJobAll();
}
