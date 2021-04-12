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
public class RawDepictionConfig {
	private String smiles;
	private RawDepictionType depictionType;
	private Integer size;
}