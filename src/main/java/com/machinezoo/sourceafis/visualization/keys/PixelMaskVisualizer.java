// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.images.*;
import com.machinezoo.sourceafis.visualization.rendering.*;

public record PixelMaskVisualizer() implements VectorVisualizer {
	@Override
	public PixelMaskKey key() {
		return new PixelMaskKey();
	}
	@Override
	public Set<TransparencyKey<?>> dependencies(TransparentOperation operation) {
		return Set.of(key(), new InputImageKey(), new InputGrayscaleKey());
	}
	@Override
	public VectorImage visualize(TransparencyArchive archive) {
		var matrix = archive.deserialize(key()).orElseThrow();
		return new VectorBuffer(matrix.size())
			.background(archive)
			.embed(new MaskImage(matrix))
			.render();
	}
}
