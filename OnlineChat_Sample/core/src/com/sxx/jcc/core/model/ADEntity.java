package com.sxx.jcc.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class ADEntity extends ADBase{
	@Column(name="CREATED")
	protected Date created;
	
	@Column(name="CREATED_BY")
	protected Long createdBy;
	
	@Column(name="UPDATED")
	protected Date updated;
	
	@Column(name="UPDATED_BY")
	protected Long updatedBy;
	
	@Version
	@Column(name="LOCK_VERSION")
	private Long lockVersion = 1l;
	
	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getCreatedBy() {
		return createdBy;
	}
	
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}
	
	public void setLockVersion(Long lockVersion) {
		this.lockVersion = lockVersion;
	}

	public Long getLockVersion() {
		return lockVersion;
	}
	
	@PrePersist
	@PreUpdate
	private void setupUpdated() {
		updated = new Date();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
