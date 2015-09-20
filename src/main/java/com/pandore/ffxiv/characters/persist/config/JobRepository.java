package com.pandore.ffxiv.characters.persist.config;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.pandore.ffxiv.characters.persist.entity.XIVJob;
import com.pandore.ffxiv.characters.persist.entity.XIVRole;

public interface JobRepository extends PagingAndSortingRepository<XIVJob, Long> {
	
	public List<XIVJob> findAllByOrderByIdAsc();
	
	public XIVJob findFirstByShortName(@Param("shortname") String shortName);
	
	@Query("select j from XIVJob j where j.role = :r and j.isClass = false order by j.id")
	public List<XIVJob> findByRoleIgnoreClasses(@Param("r") XIVRole role);
	
	@Query("select c.mainJob, count(c.id) from XIVCharacter c, XIVJob j where c.mainJob = j and j.isClass = false group by c.mainJob.id")
	public List<Object[]> findCountPerMainJob();
	
	@Query(value = "select j.shortname, count(cj.character_id) from job j, char_jobs cj, character c "
			+ "where j.id = cj.job_id and j.is_class = false "
			+ "and c.id = cj.character_id and c.main_job_id <> cj.job_id "
			+ "group by cj.job_id",
			nativeQuery = true)
	public List<Object[]> findCountPerAltJob();
	
	@Query(value = "select j.shortname, count(cj.character_id) from job j, char_jobs cj "
			+ "where j.id = cj.job_id and j.is_class = false group by cj.job_id",
			nativeQuery = true)
	public List<Object[]> findCountPerJobAll();
}
