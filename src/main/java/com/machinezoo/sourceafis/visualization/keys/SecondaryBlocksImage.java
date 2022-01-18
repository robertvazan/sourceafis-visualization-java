// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.visualization.common.*;
import com.machinezoo.sourceafis.visualization.layers.*;
import com.machinezoo.sourceafis.visualization.utils.*;
import com.machinezoo.stagean.*;

public record SecondaryBlocksImage() implements VectorVisualizer {
	@Override
	public BlocksKey key() {
		return new BlocksKey();
	}
	@Override
	public Set<TransparencyKey<?>> dependencies(TransparentOperation operation) {
		return Set.of(key(), new InputImageKey());
	}
	@Override
	@DraftCode("Support grayscale input.")
	public VectorVisualization render(TransparencyArchive archive) {
		var blocks = archive.deserialize(key()).orElseThrow();
		return new VectorBuffer(blocks.pixels())
			.padding(1)
			.add(archive.read(new InputImageKey()).map(im -> EmbeddedImageLayer.jpeg(blocks.pixels(), im)).orElse(null))
			.add(new DoubleBlockGridLayer(blocks.pixels(), blocks.secondary(), blocks.primary(), "#080"))
			.render();
	}
}
