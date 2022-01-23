// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.layers.*;
import com.machinezoo.sourceafis.visualization.rendering.*;

public record OutputTemplateVisualizer() implements VectorVisualizer {
	@Override
	public OutputTemplateKey key() {
		return new OutputTemplateKey();
	}
	@Override
	public Set<TransparencyKey<?>> dependencies() {
		return Set.of(key(), new InputImageKey(), new InputGrayscaleKey());
	}
	@Override
	public VectorImage visualize(TransparencyArchive archive) {
		var template = archive.deserialize(key()).orElseThrow().unpack();
		return new VectorBuffer(template.size())
			.background(archive)
			.add(new MinutiaLayer(template))
			.render();
	}
}
