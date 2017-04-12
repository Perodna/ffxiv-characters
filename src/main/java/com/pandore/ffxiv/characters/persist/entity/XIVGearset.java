package com.pandore.ffxiv.characters.persist.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "gearset",
	uniqueConstraints = @UniqueConstraint(name = "constraint_unique_char_job", columnNames = {"character_id", "job_id" }))
public class XIVGearset {

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

	@Column(name = "weapon_id", nullable = true)
	private String weaponId;

	@Column(name = "head_id", nullable = true)
	private String headId;

	@Column(name = "body_id", nullable = true)
	private String bodyId;

	@Column(name = "hands_id", nullable = true)
	private String handsId;

	@Column(name = "waist_id", nullable = true)
	private String waistId;

	@Column(name = "legs_id", nullable = true)
	private String legsId;

	@Column(name = "feet_id", nullable = true)
	private String feetId;

	@Column(name = "offhand_id", nullable = true)
	private String offHandId;

	@Column(name = "earrings_id", nullable = true)
	private String earringsId;

	@Column(name = "necklace_id", nullable = true)
	private String necklaceId;

	@Column(name = "bracelets_id", nullable = true)
	private String braceletsId;

	@Column(name = "ring1_id", nullable = true)
	private String ring1Id;

	@Column(name = "ring2_id", nullable = true)
	private String ring2Id;

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

	public String getWeaponId() {
		return weaponId;
	}
	public void setWeaponId(String weaponId) {
		this.weaponId = weaponId;
	}

	public String getHeadId() {
		return headId;
	}
	public void setHeadId(String headId) {
		this.headId = headId;
	}

	public String getBodyId() {
		return bodyId;
	}
	public void setBodyId(String bodyId) {
		this.bodyId = bodyId;
	}

	public String getHandsId() {
		return handsId;
	}
	public void setHandsId(String handsId) {
		this.handsId = handsId;
	}

	public String getWaistId() {
		return waistId;
	}
	public void setWaistId(String waistId) {
		this.waistId = waistId;
	}

	public String getLegsId() {
		return legsId;
	}
	public void setLegsId(String legsId) {
		this.legsId = legsId;
	}

	public String getFeetId() {
		return feetId;
	}
	public void setFeetId(String feetId) {
		this.feetId = feetId;
	}

	public String getOffHandId() {
		return offHandId;
	}
	public void setOffHandId(String offHandId) {
		this.offHandId = offHandId;
	}

	public String getEarringsId() {
		return earringsId;
	}
	public void setEarringsId(String earringsId) {
		this.earringsId = earringsId;
	}

	public String getNecklaceId() {
		return necklaceId;
	}
	public void setNecklaceId(String necklaceId) {
		this.necklaceId = necklaceId;
	}

	public String getBraceletsId() {
		return braceletsId;
	}
	public void setBraceletsId(String braceletsId) {
		this.braceletsId = braceletsId;
	}

	public String getRing1Id() {
		return ring1Id;
	}
	public void setRing1Id(String ring1Id) {
		this.ring1Id = ring1Id;
	}

	public String getRing2Id() {
		return ring2Id;
	}
	public void setRing2Id(String ring2Id) {
		this.ring2Id = ring2Id;
	}
}
