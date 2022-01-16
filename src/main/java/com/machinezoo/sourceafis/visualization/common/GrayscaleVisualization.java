// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

public interface GrayscaleVisualization extends RasterVisualization {
	@ByteColor
	byte[] shades();
	default @IntColor int[] pixels() {
		var shades = shades();
		var pixels = new int[shades.length];
		for (int i = 0; i < pixels.length; ++i)
			pixels[i] = IntColors.gray(shades[i]);
		return pixels;
	}
}
