package com.westross.depictor.model;

import io.swagger.annotations.ApiModel;

@ApiModel
public enum RawDepictionType {
	SVG("svg"),
	GIF("gif"),
	JPEG("jpg"),
	PNG("png");
	
	RawDepictionType(String code) {
		this.code = code;
	}
	
	private String code;
	public String getCode() {
		return code;
	}
}
