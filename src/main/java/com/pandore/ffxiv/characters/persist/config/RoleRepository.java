package com.pandore.ffxiv.characters.persist.config;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.pandore.ffxiv.characters.persist.entity.XIVRole;


//@RepositoryRestResource(collectionResourceRel="roles", path="roles")
public interface RoleRepository extends PagingAndSortingRepository<XIVRole, Long> {

	List<XIVRole> findByName(@Param("name") String name);
	
	@Query("select c.mainJob.role, count(c.id) from XIVCharacter c group by c.mainJob.role.id")
	List<Object[]> findCountPerMainRole();
	
	@Query(value = "select r.name, count(aj.character_id) from job j, alt_jobs aj, role r "
			+ "where j.id = aj.job_id and j.role_id = r.id "
			+ "group by r.id", nativeQuery = true)
	List<Object[]> findCountPerAltRole();
	
	@Query(value = "select roles.name, sum(roles.count) "
			+ "from "
			+ "(select r.name, count(r.id) count from character c, job j, role r "
			+ "where c.main_job_id = j.id and j.role_id = r.id group by r.id "
			+ "union all "
			+ "select r.name, count(aj.character_id) from job j, alt_jobs aj, role r "
			+ "where j.id = aj.job_id and j.role_id = r.id "
			+ "group by aj.job_id"
			+ ") roles "
			+ "group by roles.name", 
		nativeQuery = true)
	List<Object[]> findCountPerRoleAll();
}
