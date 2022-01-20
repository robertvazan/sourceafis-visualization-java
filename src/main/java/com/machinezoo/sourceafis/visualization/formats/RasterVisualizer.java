// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.formats;

import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.*;

public interface RasterVisualizer extends TransparencyVisualizer {
	RasterImage visualize(TransparencyArchive archive);
	@Override
	default String mime() {
		return "image/jpeg";
	}
}
