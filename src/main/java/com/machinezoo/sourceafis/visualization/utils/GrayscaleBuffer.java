// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import java.util.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.common.*;

public class GrayscaleBuffer {
	public final int width;
	public final int height;
	public final byte[] shades;
	public GrayscaleBuffer(int width, int height) {
		this.width = width;
		this.height = height;
		shades = new byte[width * height];
		Arrays.fill(shades, (byte)0xFF);
	}
	public GrayscaleBuffer(IntPoint size) {
		this(size.x(), size.y());
	}
	public void put(int x, int y, @ByteColor int g) {
		shades[y * width + x] = (byte)g;
	}
	public void put(IntPoint at, @ByteColor int g) {
		put(at.x(), at.y(), g);
	}
	public GrayscaleVisualization render() {
		return new GrayscaleData(width, height, shades);
	}
}
