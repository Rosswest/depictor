package com.westross.depictor.controller;

import java.io.IOException;

import org.openscience.cdk.exception.CDKException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.westross.depictor.model.DepictionConfig;
import com.westross.depictor.model.DepictionResult;
import com.westross.depictor.model.DepictionType;
import com.westross.depictor.model.RawDepictionConfig;
import com.westross.depictor.model.RawDepictionType;
import com.westross.depictor.service.DepictionService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("depict")
@CrossOrigin
public class DepictionController {

	@Autowired
	private DepictionService depictionService;
	
    @GetMapping(path = "data/simple")
	public DepictionResult simpleDepict(
			@RequestParam(required = true) @ApiParam(required = true) String smiles) throws IOException, CDKException {
    	DepictionConfig config = new DepictionConfig();
    	config.setSmiles(smiles);
    	DepictionResult imageData = this.depictionService.depict(config);
		return imageData;
	}
    
    @PostMapping(path = "data/full")
	public DepictionResult fullDepict(
			@RequestBody DepictionConfig config) throws IOException, CDKException {
    	DepictionResult imageData = this.depictionService.depict(config);
		return imageData;
	}
    
    @GetMapping(path = "img/simple", produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_GIF_VALUE})
	public byte[] simpleRawDepict(
			@RequestParam(required = true) @ApiParam(required = true) String smiles) throws IOException, CDKException {
    	DepictionConfig config = new DepictionConfig();
    	config.setSmiles(smiles);
    	byte[] imageData = this.depictionService.depictRaw(config);
		return imageData;
	}
    
    @PostMapping(path = "img/full", produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_GIF_VALUE})
	public byte[] fullRawDepict(
			@RequestBody RawDepictionConfig config) throws Exception {
    	RawDepictionType depictionType = config.getDepictionType();
    	if (config.getDepictionType() != null) {
        	String depictionString = depictionType.getCode();
    		if (depictionString.equals(DepictionType.SVG.getCode())) {
    			throw new RuntimeException("SVG is not supported for this endpoint");
    		}
    	}
    	DepictionConfig fullConfiguration = DepictionConfig.fromRawConfig(config);
    	byte[]  imageData = this.depictionService.depictRaw(fullConfiguration);
		return imageData;
	}
	
}
