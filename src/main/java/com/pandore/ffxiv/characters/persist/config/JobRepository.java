package com.pandore.ffxiv.characters.persist.config;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.pandore.ffxiv.characters.persist.entity.XIVJob;

//@RepositoryRestResource(collectionResourceRel="jobs", path="jobs")
public interface JobRepository extends PagingAndSortingRepository<XIVJob, Long> {

	List<XIVJob> findByName(@Param("name") String name);
	List<XIVJob> findByShortName(@Param("shortname") String shortName);
	
	@Query("select c.mainJob, count(c.id) from XIVCharacter c group by c.mainJob.id")
	List<Object[]> findCountPerMainJob();
	
	@Query(value = "select j.shortname, count(aj.character_id) from job j, alt_jobs aj where j.id = aj.job_id group by aj.job_id", nativeQuery = true)
	List<Object[]> findCountPerAltJob();
	
	@Query(value = "select jobs.shortname, sum(jobs.count) "
			+ "from "
			+ "(select j.shortname, count(c.id) count from character c, job j where c.main_job_id = j.id group by c.main_job_id "
			+ "union all "
			+ "select j.shortname, count(aj.character_id) count from job j, alt_jobs aj where j.id = aj.job_id group by aj.job_id) jobs "
			+ "group by jobs.shortname", 
		nativeQuery = true)
	List<Object[]> findCountPerJobAll();

}
