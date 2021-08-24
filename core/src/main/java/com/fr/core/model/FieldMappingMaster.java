package com.fr.core.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="WF_API_FIELD_MASTER")
public class FieldMappingMaster {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long fmId	;

	private long wfApiId;

	private String field;
	
	private String jsonField;
	
	public String getJsonField() {
		return jsonField;
	}

	public void setJsonField(String jsonField) {
		this.jsonField = jsonField;
	}

	public long getFmId() {
		return fmId;
	}

	public void setFmId(long fmId) {
		this.fmId = fmId;
	}

	public long getWfApiId() {
		return wfApiId;
	}

	public void setWfApiId(long wfApiId) {
		this.wfApiId = wfApiId;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
}
