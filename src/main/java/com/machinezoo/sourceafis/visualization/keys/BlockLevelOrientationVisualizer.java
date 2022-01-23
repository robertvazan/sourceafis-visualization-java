// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.layers.*;
import com.machinezoo.sourceafis.visualization.rendering.*;

public interface BlockLevelOrientationVisualizer extends VectorVisualizer {
	SerializedObjectKey<DoublePointMatrix> key();
	@Override
	default Set<TransparencyKey<?>> required() {
		return Set.of(key(), new BlocksKey());
	}
	@Override
	default Set<TransparencyKey<?>> dependencies() {
		return Set.of(key(), new BlocksKey(), new FilteredMaskKey(), new InputImageKey(), new InputGrayscaleKey());
	}
	@Override
	default VectorImage visualize(TransparencyArchive archive) {
		var blocks = archive.deserialize(new BlocksKey()).orElseThrow();
		return new VectorBuffer(blocks.pixels())
			.background(archive)
			.add(new BlockOrientationLayer(blocks, archive.deserialize(new FilteredMaskKey()).orElse(null), archive.deserialize(key()).orElseThrow()))
			.render();
	}
}
