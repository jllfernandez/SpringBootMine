package com.example.demo.modelos.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ROLES", schema = "MASTER")
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String code;
	private String descr;

	// @ManyToMany
	// @JoinTable(name = "CapabilityRol", joinColumns = { @JoinColumn(name =
	// "idRol", referencedColumnName = "ID") }, inverseJoinColumns = {
	// @JoinColumn(name = "idCapability", referencedColumnName = "ID") })

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "CAPABILITYANDROLE", schema = "MASTER", joinColumns = {
			@JoinColumn(name = "id_Role") }, inverseJoinColumns = { @JoinColumn(name = "id_Capability") })
	private List<Capability> capabilitys;

	public Long getId() {
		return id;
	}

	/*
	 * https://www.callicoder.com/hibernate-spring-boot-jpa-many-to-many-mapping-
	 * example/
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	//@JsonIgnore
	public List<Capability> getCapabilitys() {
		return capabilitys;
	}

	public void setCapabilitys(List<Capability> capabilitys) {
		this.capabilitys = capabilitys;
	}

	private static final long serialVersionUID = 1L;

}
