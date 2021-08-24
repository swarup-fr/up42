package com.fr.core.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="WF_REPORT_FIELD_MAPPING")
public class FieldMapping {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long wrfmId	;

	private String fieldString;

	public long getWrfmId() {
		return wrfmId;
	}

	public void setWrfmId(long wrfmId) {
		this.wrfmId = wrfmId;
	}

	public String getFieldString() {
		return fieldString;
	}

	public void setFieldString(String fieldString) {
		this.fieldString = fieldString;
	}

}
