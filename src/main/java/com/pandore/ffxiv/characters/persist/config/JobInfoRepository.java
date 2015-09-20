package com.pandore.ffxiv.characters.persist.config;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.pandore.ffxiv.characters.persist.entity.XIVCharacter;
import com.pandore.ffxiv.characters.persist.entity.XIVJob;
import com.pandore.ffxiv.characters.persist.entity.XIVJobInfoHistory;


public interface JobInfoRepository extends PagingAndSortingRepository<XIVJobInfoHistory, Long> {

	public List<XIVJobInfoHistory> findByCharacterAndJobOrderByDateAsc(@Param("character") XIVCharacter character, @Param("job") XIVJob job);
	
	public XIVJobInfoHistory findFirstByCharacterAndJobOrderByDateDesc(@Param("character") XIVCharacter character, @Param("job") XIVJob job);
	
	
	@Query(value = "select h.id "
			+ "from ("
			+ "  select h.character_id, h.job_id, max(h.date) maxdate "
			+ "  from job_info_history h "
			+ "  group by h.character_id, h.job_id"
			+ ") t, job_info_history h "
			+ "where h.character_id = t.character_id "
			+ "and h.job_id = t.job_id "
			+ "and h.date = t.maxdate",
			nativeQuery = true)
	public List<BigInteger> findAllCurrentIds();
	
	@Query(value = "select h.id "
			+ "from ("
			+ "  select h.character_id, h.job_id, max(h.date) maxdate "
			+ "  from job_info_history h "
			+ "  where h.job_id = :jobId "
			+ "  group by h.character_id, h.job_id"
			+ ") t, job_info_history h "
			+ "where h.character_id = t.character_id "
			+ "and h.job_id = t.job_id "
			+ "and h.date = t.maxdate",
			nativeQuery = true)
	public List<BigInteger> findAllCurrentIdsByJob(@Param("jobId") Long jobId);
	
	@Query(value = "select h.id "
			+ "from ("
			+ "  select h.character_id, h.job_id, max(h.date) maxdate "
			+ "  from job_info_history h, job j "
			+ "  where h.job_id = j.id and j.role_id = :roleId"
			+ "  group by h.character_id, h.job_id"
			+ ") t, job_info_history h "
			+ "where h.character_id = t.character_id "
			+ "and h.job_id = t.job_id "
			+ "and h.date = t.maxdate",
			nativeQuery = true)
	public List<BigInteger> findAllCurrentIdsByRole(@Param("roleId") Long roleId);
	
	public List<XIVJobInfoHistory> findByIdInOrderByLevelDescILevelDescDateAsc(List<Long> idList);
}
