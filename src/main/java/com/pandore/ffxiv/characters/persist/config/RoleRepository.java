package com.pandore.ffxiv.characters.persist.config;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.pandore.ffxiv.characters.persist.entity.XIVRole;


public interface RoleRepository extends PagingAndSortingRepository<XIVRole, Long> {

	List<XIVRole> findByName(@Param("name") String name);
	
	@Query("select c.mainJob.role, count(c.id) from XIVCharacter c group by c.mainJob.role.id")
	List<Object[]> findCountPerMainRole();
	
	@Query(value = "select r.name, count(cj.character_id) from job j, char_jobs cj, role r, character c "
			+ "where j.id = cj.job_id and j.role_id = r.id "
			+ "and c.id = cj.character_id and c.main_job_id <> cj.job_id "
			+ "group by r.id", nativeQuery = true)
	List<Object[]> findCountPerAltRole();
	
	@Query(value = "select r.name, count(cj.character_id) from job j, char_jobs cj, role r "
			+ "where j.id = cj.job_id and j.role_id = r.id "
			+ "group by r.id",
			nativeQuery = true)
	List<Object[]> findCountPerRoleAll();
}
