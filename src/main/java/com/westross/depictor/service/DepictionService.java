package com.westross.depictor.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.openscience.cdk.depict.Depiction;
import org.openscience.cdk.depict.DepictionGenerator;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import org.springframework.stereotype.Service;

import com.westross.depictor.model.DepictionConfig;
import com.westross.depictor.model.DepictionResult;
import com.westross.depictor.model.DepictionType;

import lombok.extern.slf4j.Slf4j;

@Service
public class DepictionService {

	public static final DepictionType DEFAULT_DEPICTION_TYPE = DepictionType.PNG;
	public static final int DEFAULT_SIZE = 400;

	public DepictionResult depict(DepictionConfig config) throws IOException, CDKException {

		// begin depiction
		DepictionGenerator generator = setupGenerator(config);
		String smiles = config.getSmiles();
		IAtomContainer molecule = generateMolecule(smiles);;		Depiction depiction = generator.depict(molecule);
		DepictionType depictionType = config.getDepictionType();
		if (depictionType == null) {
			depictionType = DepictionService.DEFAULT_DEPICTION_TYPE;
		}

		// create image from depiction
		DepictionResult result = null;
		if (depictionType.equals(DepictionType.SVG)) {
			result = depictAsSvg(depiction);
		} else if (depictionType.equals(DepictionType.JPEG) || depictionType.equals(DepictionType.GIF) || depictionType.equals(DepictionType.PNG)) {
			String imageType = depictionType.getCode();
			result = depictAsImg(depiction, imageType);
		}

		return result;

	}

	public byte[] depictRaw(DepictionConfig config) throws IOException, CDKException {
		DepictionGenerator generator = setupGenerator(config);
		String smiles = config.getSmiles();
		IAtomContainer molecule = generateMolecule(smiles);;
		Depiction depiction = generator.depict(molecule);
		BufferedImage image = depiction.toImg();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DepictionType depictionType = config.getDepictionType();
		if (depictionType == null) {
			depictionType = DepictionService.DEFAULT_DEPICTION_TYPE;
		}
		String imageType = depictionType.getCode();
		if (imageType == DepictionType.JPEG.getCode()) {
			image = convertToRgb(image); // account for missing jpeg encoder
		}
		ImageIO.write(image, imageType, outputStream);
		byte[] imageData = outputStream.toByteArray();
		return imageData;
	}

	/* Not using common configs due to lombok inheritance issues */
	private DepictionGenerator setupGenerator(DepictionConfig config) {
		int width = DEFAULT_SIZE;
		int height = DEFAULT_SIZE;
		Integer size = config.getSize();
		if (size != null) {
			width = size;
			height = size;
		}
		DepictionGenerator generator = new DepictionGenerator();
		generator = generator.withAromaticDisplay()
				.withAtomColors()
				.withFillToFit()
				.withSize(width, height);
		return generator;

	}

	private DepictionResult depictAsSvg(Depiction depiction) {
		String encodedString = depiction.toSvgStr("px");
		DepictionResult result = new DepictionResult(encodedString);
		return result;
	}

	private DepictionResult depictAsImg(Depiction depiction, String imageType) throws IOException {
		BufferedImage image = depiction.toImg();
		if (imageType == DepictionType.JPEG.getCode()) {
			image = convertToRgb(image); // account for missing jpeg encoder
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, imageType, outputStream);
		byte[] imageData = outputStream.toByteArray();
		String encodedString = Base64.getEncoder().encodeToString(imageData);
		DepictionResult result = new DepictionResult(encodedString);
		return result;
	}


	private BufferedImage convertToRgb(BufferedImage image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = newImage.createGraphics();
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		g.dispose();
		return newImage;
	}

	private IAtomContainer generateMolecule(String smiles) throws InvalidSmilesException {
		if (smiles != null) {
			SmilesParser parser = new SmilesParser(SilentChemObjectBuilder.getInstance());
			IAtomContainer molecule = parser.parseSmiles(smiles);
			return molecule;
		} else {
			return null;
		}


	}
}
