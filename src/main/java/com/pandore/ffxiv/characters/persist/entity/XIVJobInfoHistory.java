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
@Table(name = "job_info_history")
public class XIVJobInfoHistory {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="character_id")
	private XIVCharacter character;
	
	@ManyToOne
	@JoinColumn(name="job_id")
	private XIVJob job;
	
	@Column(name = "date", nullable = false)
	private Date date;
	
	@Column(name = "ilevel", nullable = false)
	private int iLevel;
	
	@Column(name = "level", nullable = false)
	private int level;
	
	
	protected XIVJobInfoHistory() {
	}
	
	public XIVJobInfoHistory(XIVCharacter character, XIVJob job) {
		this.character = character;
		this.job = job;
		this.date = new Date();
	}
	
	public XIVJobInfoHistory(XIVCharacter character, XIVJob job, Date date) {
		this.character = character;
		this.job = job;
		this.date = date;
	}

	public XIVJobInfoHistory(XIVCharacter character, XIVJob job, Date date, int iLevel, int level) {
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

	public int getiLevel() {
		return iLevel;
	}

	public void setiLevel(int iLevel) {
		this.iLevel = iLevel;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "XIVJobInfo [id=" + id + ", character=" + character.getFullName() + ", job="
				+ job + ", date=" + date + ", iLevel=" + iLevel + ", level="
				+ level + "]";
	}
	
	
	
}
