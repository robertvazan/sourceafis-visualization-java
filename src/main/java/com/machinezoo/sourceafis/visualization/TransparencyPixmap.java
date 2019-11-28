// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.imageio.plugins.jpeg.*;
import javax.imageio.stream.*;
import com.machinezoo.noexception.*;
import com.machinezoo.sourceafis.transparency.*;

public class TransparencyPixmap {
	public final int width;
	public final int height;
	private final int[] pixels;
	public TransparencyPixmap(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	public TransparencyPixmap(IntPoint size) {
		this(size.x, size.y);
	}
	public IntPoint size() {
		return new IntPoint(width, height);
	}
	public int get(int x, int y) {
		return pixels[width * y + x];
	}
	public void set(int x, int y, int color) {
		pixels[width * y + x] = color;
	}
	public void set(IntPoint at, int color) {
		set(at.x, at.y, color);
	}
	public byte[] png() {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		image.setRGB(0, 0, width, height, pixels, 0, width);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Exceptions.sneak().run(() -> ImageIO.write(image, "PNG", stream));
		return stream.toByteArray();
	}
	public byte[] jpeg() {
		return jpeg(0.95f);
	}
	private byte[] jpeg(float quality) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] opaque = Arrays.copyOf(pixels, pixels.length);
		for (int i = 0; i < opaque.length; ++i)
			opaque[i] |= 0xff_00_00_00;
		image.setRGB(0, 0, width, height, opaque, 0, width);
		JPEGImageWriteParam params = new JPEGImageWriteParam(null);
		params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		params.setCompressionQuality(quality);
		ImageWriter writer = ImageIO.getImageWritersByFormatName("JPEG").next();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		writer.setOutput(new MemoryCacheImageOutputStream(stream));
		Exceptions.sneak().run(() -> writer.write(null, new IIOImage(image, null, null), params));
		return stream.toByteArray();
	}
	public void fill(int color) {
		for (int i = 0; i < pixels.length; ++i)
			pixels[i] = color;
	}
	public static int gray(int brightness) {
		return 0xff_00_00_00 | (brightness << 16) | (brightness << 8) | brightness;
	}
}
