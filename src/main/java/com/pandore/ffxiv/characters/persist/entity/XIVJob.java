package com.pandore.ffxiv.characters.persist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "job")
public class XIVJob {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id", nullable = false)
	private long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "shortname", nullable = false)
	private String shortName;
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private XIVRole role;
	
	protected XIVJob() {
	}
	
	public XIVJob(String name, String shortname, XIVRole role) {
		this.name = name;
		this.shortName = shortname;
		this.role = role;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public XIVRole getRole() {
		return role;
	}

	public void setRole(XIVRole role) {
		this.role = role;
	}
}
