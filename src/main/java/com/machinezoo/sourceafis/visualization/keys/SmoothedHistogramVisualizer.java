// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.visualization.common.*;
import com.machinezoo.sourceafis.visualization.graphics.*;
import com.machinezoo.sourceafis.visualization.layers.*;

public record SmoothedHistogramVisualizer() implements VectorVisualizer {
	@Override
	public SmoothedHistogramKey key() {
		return new SmoothedHistogramKey();
	}
	@Override
	public Set<TransparencyKey<?>> dependencies(TransparentOperation operation) {
		return Set.of(key(), new BlocksKey(), new InputImageKey(), new InputGrayscaleKey());
	}
	@Override
	public VectorImage visualize(TransparencyArchive archive) {
		var blocks = archive.deserialize(new BlocksKey()).orElseThrow();
		return new VectorBuffer(blocks.pixels())
			.background(archive)
			.add(new HistogramLayer(blocks.secondary(), archive.deserialize(key()).orElseThrow()))
			.render();
	}
}
