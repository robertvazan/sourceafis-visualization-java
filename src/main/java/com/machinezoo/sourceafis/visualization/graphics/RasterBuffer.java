// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.graphics;

import java.io.*;
import java.util.*;
import javax.imageio.*;
import com.machinezoo.noexception.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.common.*;
import com.machinezoo.stagean.*;

public class RasterBuffer implements RasterModel {
	public final int width;
	public final int height;
	public final @IntColor int[] pixels;
	public RasterBuffer(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		Arrays.fill(pixels, 0xFF_FF_FF_FF);
	}
	public RasterBuffer(IntPoint size) {
		this(size.x(), size.y());
	}
	@DraftCode("Support all image decoders, especially WSQ.")
	public RasterBuffer(byte[] image) {
		var buffered = Exceptions.wrap().get(() -> ImageIO.read(new ByteArrayInputStream(image)));
		if (buffered == null)
			throw new IllegalArgumentException("Unsupported image format.");
		width = buffered.getWidth();
		height = buffered.getHeight();
		pixels = new int[width * height];
		buffered.getRGB(0, 0, width, height, pixels, 0, width);
	}
	public void put(int x, int y, @IntColor int c) {
		pixels[y * width + x] = c;
	}
	public void put(IntPoint at, @IntColor int c) {
		put(at.x(), at.y(), c);
	}
	@Override
	public RasterImage render() {
		return new RasterFrame(width, height, pixels);
	}
}
