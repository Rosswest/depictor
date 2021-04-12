package com.westross.depictor.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel
public class DepictionConfig {
	private String smiles;
	private DepictionType depictionType;
	private Integer size;
	
	public static DepictionConfig fromRawConfig(RawDepictionConfig original) {
		DepictionType depictionType = rawDepictionTypeToFullDepictionType(original.getDepictionType());
		DepictionConfig config = DepictionConfig.builder()
		.smiles(original.getSmiles())
		.size(original.getSize())
		.depictionType(depictionType)
		.build();
		return config;
	}
	
	public static DepictionType rawDepictionTypeToFullDepictionType(RawDepictionType rawType) {
		switch (rawType) {
		case PNG:
			return DepictionType.PNG;			
		case JPEG:
			return DepictionType.JPEG;			
		case GIF:
			return DepictionType.GIF;
		default:
			break;
	}
	
	return null;
	}
	
}