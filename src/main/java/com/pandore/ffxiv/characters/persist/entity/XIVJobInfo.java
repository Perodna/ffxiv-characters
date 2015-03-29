package com.pandore.ffxiv.characters.persist.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "job_info")
public class XIVJobInfo {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="character_id")
	XIVCharacter character;
	
	@ManyToOne
	@JoinColumn(name="job_id")
	XIVJob job;
	
	@Column(name = "date", nullable = false)
	Date date;
	
	@Column(name = "ilevel", nullable = false)
	Integer iLevel;
	
	@Column(name = "level", nullable = false)
	Integer level;
	
	
	protected XIVJobInfo() {
	}
	
	public XIVJobInfo(XIVCharacter character, XIVJob job) {
		this.character = character;
		this.job = job;
		this.date = new Date();
	}
	
	public XIVJobInfo(XIVCharacter character, XIVJob job, Date date) {
		this.character = character;
		this.job = job;
		this.date = date;
	}

	public XIVJobInfo(XIVCharacter character, XIVJob job, Date date, Integer iLevel, Integer level) {
		super();
		this.character = character;
		this.job = job;
		this.date = date;
		this.iLevel = iLevel;
		this.level = level;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public XIVCharacter getCharacter() {
		return character;
	}

	public void setCharacter(XIVCharacter character) {
		this.character = character;
	}

	public XIVJob getJob() {
		return job;
	}

	public void setJob(XIVJob job) {
		this.job = job;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getiLevel() {
		return iLevel;
	}

	public void setiLevel(Integer iLevel) {
		this.iLevel = iLevel;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
}
