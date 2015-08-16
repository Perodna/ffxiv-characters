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
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	@Column(name = "shortname", nullable = false, unique = true)
	private String shortName;
	
	@Column(name = "is_class", nullable = false)
	private boolean isClass = false;
	
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
	
	public XIVJob(String name, String shortname, XIVRole role, boolean isClass) {
		this.name = name;
		this.shortName = shortname;
		this.role = role;
		this.isClass = isClass;
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
	
	public boolean isClass() {
		return isClass;
	}

	public void setClass(boolean isClass) {
		this.isClass = isClass;
	}

	public XIVRole getRole() {
		return role;
	}

	public void setRole(XIVRole role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((shortName == null) ? 0 : shortName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XIVJob other = (XIVJob) obj;
		if (id != other.id)
			return false;
		if (shortName == null) {
			if (other.shortName != null)
				return false;
		} else if (!shortName.equals(other.shortName))
			return false;
		return true;
	}
	
}
