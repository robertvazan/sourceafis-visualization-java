// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.rendering;

import java.util.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.formats.*;

public class GrayscaleBuffer implements GrayscaleModel {
	public final int width;
	public final int height;
	public final @ByteColor byte[] shades;
	public GrayscaleBuffer(int width, int height) {
		this.width = width;
		this.height = height;
		shades = new byte[width * height];
		Arrays.fill(shades, (byte)0xFF);
	}
	public GrayscaleBuffer(IntPoint size) {
		this(size.x(), size.y());
	}
	public void set(int x, int y, @ByteColor int g) {
		shades[y * width + x] = (byte)g;
	}
	public void set(IntPoint at, @ByteColor int g) {
		set(at.x(), at.y(), g);
	}
	@Override
	public GrayscaleImage render() {
		return new GrayscaleFrame(width, height, shades);
	}
}
