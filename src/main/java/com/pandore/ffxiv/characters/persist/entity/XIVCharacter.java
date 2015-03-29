package com.pandore.ffxiv.characters.persist.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "character")
public class XIVCharacter {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "firstname", nullable = false)
	private String firstName;

	@Column(name = "lastname", nullable = false)
	private String lastName;
	
	@ManyToOne
	@JoinColumn(name="main_job_id")
	private XIVJob mainJob;
	
	@ManyToMany
	@JoinTable(
		name = "alt_jobs",
		joinColumns = @JoinColumn(name = "character_id"),
		inverseJoinColumns = @JoinColumn(name = "job_id")
	)
	private List<XIVJob> altJobs = new ArrayList<XIVJob>(9);
	
	protected XIVCharacter() {
	}

	public XIVCharacter(final String firstName, final String lastName, XIVJob mainJob) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.mainJob = mainJob;
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}

	public XIVJob getMainJob() {
		return mainJob;
	}

	public void setMainJob(XIVJob mainJob) {
		this.mainJob = mainJob;
	}

	public List<XIVJob> getAltJobs() {
		return altJobs;
	}

	public void setAltJobs(List<XIVJob> altJobs) {
		this.altJobs = altJobs;
	}
	
	public void addAltJob(XIVJob job) {
		this.altJobs.add(job);
	}
	
	public void addAltJobs(XIVJob... jobs) {
		for (XIVJob job : jobs) {
			this.altJobs.add(job);
		}
	}

	@Override
	public String toString() {
		return String.format("Character[id=%d, firstName='%s', lastName='%s']", id, firstName, lastName);
	}
}