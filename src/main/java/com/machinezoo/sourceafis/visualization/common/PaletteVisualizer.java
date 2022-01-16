// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

import com.machinezoo.sourceafis.transparency.*;

public interface PaletteVisualizer<T extends Enum<T> & ColorCode> extends RasterVisualizer {
	Class<T> type();
	PaletteVisualization<T> render(TransparencyArchive archive);
	@Override
	default String mime() {
		return "image/png";
	}
	@Override
	default String extension() {
		return ".png";
	}
}
