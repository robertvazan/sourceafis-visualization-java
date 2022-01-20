// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.layers.*;
import com.machinezoo.sourceafis.visualization.rendering.*;

public record HistogramVisualizer() implements VectorVisualizer {
	@Override
	public HistogramKey key() {
		return new HistogramKey();
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
			.add(new HistogramLayer(blocks.primary(), archive.deserialize(key()).orElseThrow()))
			.render();
	}
}
