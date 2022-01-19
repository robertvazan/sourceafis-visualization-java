// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

import com.machinezoo.sourceafis.transparency.*;

public interface VectorVisualizer extends TransparencyVisualizer {
	VectorImage visualize(TransparencyArchive archive);
	@Override
	default String mime() {
		return "image/svg+xml";
	}
	@Override
	default String extension() {
		return ".svg";
	}
}
