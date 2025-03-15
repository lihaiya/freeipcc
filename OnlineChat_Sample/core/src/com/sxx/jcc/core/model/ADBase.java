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
public abstract class ADBase implements Serializable, Cloneable{
	@Id
	@SequenceGenerator(name = "OBJECT_ID", sequenceName = "OBJECT_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="OBJECT_ID")
	@Column(name = "OBJECT_ID")
	private Long objectId;
	
	@Column(name="IS_ACTIVE")
	protected String isActive="Y";

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive ? "Y" : "N";
	}
	
	public Boolean getIsActive(){
		return "Y".equalsIgnoreCase(this.isActive) ? true : false; 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (this.getObjectId() == null) return super.equals(obj);  
		if (!(obj instanceof ADBase)) return false;
		ADBase o = (ADBase) obj;
		return this.getObjectId().equals(o.getObjectId());
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		ADBase base = (ADBase) super.clone();
		base.setObjectId(null);
		return base;
	}
}
