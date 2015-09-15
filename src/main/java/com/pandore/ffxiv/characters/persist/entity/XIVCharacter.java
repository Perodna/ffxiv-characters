package com.pandore.ffxiv.characters.persist.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	@Column(name = "lodestone_id", nullable = false, unique = true)
	private String lodestoneId;

	@Column(name = "firstname", nullable = false)
	private String firstName;

	@Column(name = "lastname", nullable = false)
	private String lastName;
	
	@ManyToOne
	@JoinColumn(name="main_job_id")
	private XIVJob mainJob;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "char_jobs",
		joinColumns = @JoinColumn(name = "character_id"),
		inverseJoinColumns = @JoinColumn(name = "job_id")
	)
	private List<XIVJob> jobs = new ArrayList<XIVJob>(9);
	
	protected XIVCharacter() {
	}

	public XIVCharacter(final String firstName, final String lastName, XIVJob mainJob) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.mainJob = mainJob;
		this.addJob(mainJob);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLodestoneId() {
		return lodestoneId;
	}

	public void setLodestoneId(String lodestoneId) {
		this.lodestoneId = lodestoneId;
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

	public List<XIVJob> getJobs() {
		return jobs;
	}

	public void setJobs(List<XIVJob> jobs) {
		this.jobs = jobs;
	}
	
	public void addJob(XIVJob job) {
		if (!jobs.contains(job)) {
			this.jobs.add(job);
		}
	}
	
	public void addJobs(XIVJob... jobs) {
		for (XIVJob job : jobs) {
			addJob(job);
		}
	}
	
	public boolean hasJob(XIVJob job) {
		return jobs.contains(job);
	}

	@Override
	public String toString() {
		return String.format("Character[id=%d, firstName='%s', lastName='%s']", id, firstName, lastName);
	}
}