// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.visualization.common.*;
import com.machinezoo.sourceafis.visualization.images.*;

public record ParallelSmoothingVisualizer() implements GrayscaleVisualizer {
	@Override
	public ParallelSmoothingKey key() {
		return new ParallelSmoothingKey();
	}
	@Override
	public GrayscaleImage visualize(TransparencyArchive archive) {
		return new DoubleMatrixImage(archive.deserialize(key()).orElseThrow(), -1, 1).render();
	}
}
