// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.images.*;

public record OrthogonalSmoothingVisualizer() implements GrayscaleVisualizer {
	@Override
	public OrthogonalSmoothingKey key() {
		return new OrthogonalSmoothingKey();
	}
	@Override
	public GrayscaleImage visualize(TransparencyArchive archive) {
		return new DoubleMatrixImage(archive.deserialize(key()).orElseThrow(), -1, 1).render();
	}
}
