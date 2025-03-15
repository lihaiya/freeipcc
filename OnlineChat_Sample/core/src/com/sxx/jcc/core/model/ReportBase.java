package com.sxx.jcc.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class ReportBase implements Serializable, Cloneable{
	@Id
	@SequenceGenerator(name = "OBJECT_ID", sequenceName = "OBJECT_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="OBJECT_ID")
	@Column(name = "OBJECT_ID")
	private Long objectId;

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	
}
