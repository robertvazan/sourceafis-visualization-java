// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

import com.machinezoo.sourceafis.transparency.*;

public interface RasterVisualizer extends TransparencyVisualizer {
	RasterImage visualize(TransparencyArchive archive);
	@Override
	default String mime() {
		return "image/jpeg";
	}
	@Override
	default String extension() {
		return ".jpeg";
	}
}
