// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.visualization.common.*;
import com.machinezoo.sourceafis.visualization.images.*;

public record ParallelSmoothingImage() implements GrayscaleVisualizer {
	@Override
	public ParallelSmoothingKey key() {
		return new ParallelSmoothingKey();
	}
	@Override
	public GrayscaleVisualization render(TransparencyArchive archive) {
		return new DoubleMatrixImage(archive.deserialize(key()).orElseThrow(), -1, 1).render();
	}
}
