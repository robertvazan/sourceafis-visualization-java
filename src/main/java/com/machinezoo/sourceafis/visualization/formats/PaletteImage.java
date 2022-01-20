// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.formats;

import java.util.*;

public interface PaletteImage<T extends Enum<T> & PaletteSymbol> extends RasterImage {
	Class<T> palette();
	@PaletteColor
	byte[] codes();
	default @IntColor int[] pixels() {
		var colors = Arrays.stream(palette().getEnumConstants()).mapToInt(s -> s.color()).toArray();
		var codes = codes();
		var pixels = new int[codes.length];
		for (int i = 0; i < pixels.length; ++i)
			pixels[i] = colors[codes[i]];
		return pixels;
	}
	@Override
	default String mime() {
		return "image/png";
	}
	@Override
	default String extension() {
		return ".png";
	}
	@Override
	default byte[] bytes() {
		return png();
	}
}
