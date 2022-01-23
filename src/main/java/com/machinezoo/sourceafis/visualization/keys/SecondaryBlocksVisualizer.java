// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.layers.*;
import com.machinezoo.sourceafis.visualization.rendering.*;

public record SecondaryBlocksVisualizer() implements VectorVisualizer {
	@Override
	public BlocksKey key() {
		return new BlocksKey();
	}
	@Override
	public Set<TransparencyKey<?>> dependencies() {
		return Set.of(key(), new InputImageKey(), new InputGrayscaleKey());
	}
	@Override
	public VectorImage visualize(TransparencyArchive archive) {
		var blocks = archive.deserialize(key()).orElseThrow();
		return new VectorBuffer(blocks.pixels())
			.padding(1)
			.background(archive)
			.add(new DoubleBlockGridLayer(blocks.pixels(), blocks.secondary(), blocks.primary(), "#080"))
			.render();
	}
}
