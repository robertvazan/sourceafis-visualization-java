// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.formats;

import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.*;

public interface VectorVisualizer extends TransparencyVisualizer {
	VectorImage visualize(TransparencyArchive archive);
	@Override
	default String mime() {
		return "image/svg+xml";
	}
}
