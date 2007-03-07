package org.sakaiproject.evaluation.model;

// Generated 07-Mar-2007 14:04:08 by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * EvalEmailTemplate generated by hbm2java
 */
public class EvalEmailTemplate implements java.io.Serializable {

	private Long id;

	private Date lastModified;

	private String owner;

	private String message;

	private String defaultType;

	public EvalEmailTemplate() {
	}

	public EvalEmailTemplate(Date lastModified, String owner, String message) {
		this.lastModified = lastModified;
		this.owner = owner;
		this.message = message;
	}

	public EvalEmailTemplate(Date lastModified, String owner, String message, String defaultType) {
		this.lastModified = lastModified;
		this.owner = owner;
		this.message = message;
		this.defaultType = defaultType;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDefaultType() {
		return this.defaultType;
	}

	public void setDefaultType(String defaultType) {
		this.defaultType = defaultType;
	}

}
