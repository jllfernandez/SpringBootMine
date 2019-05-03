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
@Table(name = "CAPABILITYES", schema = "MASTER")
public class Capability implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String code;
	private String descr;

	// @ManyToMany
	// @JoinTable(name = "CapabilityRol", joinColumns = { @JoinColumn(name =
	// "idCapability", referencedColumnName = "ID") }, inverseJoinColumns = {
	// @JoinColumn(name = "idRol", referencedColumnName = "ID") })

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "CAPABILITYANDROLE", schema = "MASTER", joinColumns = {
			@JoinColumn(name = "id_Capability") }, inverseJoinColumns = { @JoinColumn(name = "id_Role") })

	private List<Role> roles;

	public Long getId() {
		return id;
	}

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

	@JsonIgnore
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	private static final long serialVersionUID = 1L;

}
