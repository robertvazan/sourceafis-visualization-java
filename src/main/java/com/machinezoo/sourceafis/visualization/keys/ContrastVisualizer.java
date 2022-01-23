// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.rendering.*;
import com.machinezoo.sourceafis.visualization.types.*;

public record ContrastVisualizer() implements VectorVisualizer {
	@Override
	public ContrastKey key() {
		return new ContrastKey();
	}
	@Override
	public Set<TransparencyKey<?>> dependencies() {
		return Set.of(key(), new BlocksKey(), new InputImageKey(), new InputGrayscaleKey());
	}
	@Override
	public VectorImage visualize(TransparencyArchive archive) {
		var blocks = archive.deserialize(new BlocksKey()).orElseThrow();
		var matrix = archive.deserialize(key()).orElseThrow();
		var buffer = new VectorBuffer(blocks.pixels())
			.background(archive);
		for (var at : IntPoints.stream(blocks.primary().blocks()))
			buffer.add(new ContrastMarker(blocks.primary().block(at), matrix.get(at)));
		return buffer.render();
	}
}
