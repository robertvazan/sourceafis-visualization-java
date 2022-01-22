// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.images.*;
import com.machinezoo.sourceafis.visualization.rendering.*;

public interface BlockMaskVisualizer extends VectorVisualizer {
	SerializedObjectKey<BooleanMatrix> key();
	boolean inverted();
	@Override
	default Set<TransparencyKey<?>> dependencies(TransparentOperation operation) {
		return Set.of(key(), new BlocksKey(), new InputImageKey(), new InputGrayscaleKey());
	}
	@Override
	default VectorImage visualize(TransparencyArchive archive) {
		var blocks = archive.deserialize(new BlocksKey()).orElseThrow();
		return new VectorBuffer(blocks.pixels())
			.background(archive)
			.embed(new MaskImage(archive.deserialize(key()).orElseThrow().expand(blocks), inverted()))
			.render();
	}
}
