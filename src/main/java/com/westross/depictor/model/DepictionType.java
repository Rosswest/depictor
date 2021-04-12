package com.westross.depictor.model;

import io.swagger.annotations.ApiModel;

@ApiModel
public enum DepictionType {
	SVG("svg"),
	GIF("gif"),
	JPEG("jpg"),
	PNG("png");
	
	DepictionType(String code) {
		this.code = code;
	}
	
	private String code;
	public String getCode() {
		return code;
	}
	
}
