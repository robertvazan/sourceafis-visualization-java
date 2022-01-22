// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.layers.*;
import com.machinezoo.sourceafis.visualization.rendering.*;

public record SkeletonMinutiaeVisualizer() implements VectorVisualizer {
	@Override
	public SkeletonMinutiaeKey key() {
		return new SkeletonMinutiaeKey();
	}
	@Override
	public Set<TransparencyKey<?>> dependencies(TransparentOperation operation) {
		return Set.of(key(), new InputImageKey(), new InputGrayscaleKey());
	}
	@Override
	public VectorImage visualize(TransparencyArchive archive) {
		var template = archive.deserialize(key()).orElseThrow();
		return new VectorBuffer(template.size())
			.background(archive)
			.add(new MinutiaLayer(template))
			.render();
	}
}