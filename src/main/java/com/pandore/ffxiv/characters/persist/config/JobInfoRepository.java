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

	List<XIVJobInfoHistory> findByCharacterAndJobOrderByDateAsc(@Param("character") XIVCharacter character, @Param("job") XIVJob job);
	
	XIVJobInfoHistory findFirstByCharacterAndJobOrderByDateDesc(@Param("character") XIVCharacter character, @Param("job") XIVJob job);
	
	
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
	List<BigInteger> findAllCurrentIds();
	
	List<XIVJobInfoHistory> findByIdInOrderByLevelDescILevelDescDateAsc(List<Long> idList);
}
