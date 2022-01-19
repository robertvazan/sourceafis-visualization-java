// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

import com.machinezoo.stagean.*;

@DraftCode("Support native grayscale output images.")
public interface GrayscaleImage extends RasterImage {
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
