// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.imageio.plugins.jpeg.*;
import javax.imageio.stream.*;
import com.machinezoo.noexception.*;

public interface RasterVisualization extends TransparencyVisualization {
	@IntColor
	int[] pixels();
	@Override
	default String mime() {
		return "image/jpeg";
	}
	@Override
	default String extension() {
		return ".jpeg";
	}
	default byte[] png() {
		var image = new BufferedImage(width(), height(), BufferedImage.TYPE_INT_ARGB);
		image.setRGB(0, 0, width(), height(), pixels(), 0, width());
		var stream = new ByteArrayOutputStream();
		boolean success = Exceptions.sneak().getAsBoolean(() -> ImageIO.write(image, "PNG", stream));
		if (!success)
			throw new IllegalStateException("PNG image encoding is not supported.");
		return stream.toByteArray();
	}
	default byte[] jpeg() {
		float quality = 0.9f;
		var image = new BufferedImage(width(), height(), BufferedImage.TYPE_INT_RGB);
		var opaque = pixels().clone();
		for (int i = 0; i < opaque.length; ++i)
			opaque[i] |= 0xff_00_00_00;
		image.setRGB(0, 0, width(), height(), opaque, 0, width());
		var params = new JPEGImageWriteParam(null);
		params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		params.setCompressionQuality(quality);
		var writers = ImageIO.getImageWritersByFormatName("JPEG");
		if (!writers.hasNext())
			throw new IllegalStateException("JPEG image encoding is not supported.");
		var writer = writers.next();
		var stream = new ByteArrayOutputStream();
		writer.setOutput(new MemoryCacheImageOutputStream(stream));
		Exceptions.sneak().run(() -> writer.write(null, new IIOImage(image, null, null), params));
		return stream.toByteArray();
	}
	@Override
	default byte[] bytes() {
		return jpeg();
	}
}
