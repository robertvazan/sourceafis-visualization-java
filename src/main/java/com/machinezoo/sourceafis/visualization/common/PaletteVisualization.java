// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

public interface PaletteVisualization<T extends Enum<T> & ColorCode> extends RasterVisualization {
	Class<T> type();
	byte[] codes();
	default @IntColor int[] pixels() {
		var constants = type().getEnumConstants();
		var codes = codes();
		var pixels = new int[codes.length];
		for (int i = 0; i < pixels.length; ++i)
			pixels[i] = constants[codes[i]].color();
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
