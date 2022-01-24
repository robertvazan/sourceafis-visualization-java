// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.layers.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.rendering.*;
import it.unimi.dsi.fastutil.ints.*;
import one.util.streamex.*;

public record EdgeHashVisualizer() implements VectorVisualizer {
	@Override
	public EdgeHashKey key() {
		return new EdgeHashKey();
	}
	@Override
	public Set<TransparencyKey<?>> dependencies() {
		return Set.of(key(), new InputTemplateKey(), new InputImageKey(), new InputGrayscaleKey());
	}
	@Override
	public VectorImage visualize(TransparencyArchive archive) {
		var template = archive.deserialize(new InputTemplateKey()).orElseThrow().unpack();
		var buffer = new VectorBuffer(template.size())
			.background(archive);
		var hash = archive.deserialize(key()).orElseThrow();
		var seen = new IntOpenHashSet();
		var sorted = StreamEx.of(hash)
			.flatArray(e -> e.edges())
			.filter(e -> e.reference() < e.neighbor() && seen.add((e.reference() << 16) + e.neighbor()))
			.sortedByInt(e -> -e.length())
			.toList();
		for (var edge : sorted)
			if (edge.reference() < edge.neighbor())
				buffer.add(new EdgeShapeMarker(edge, template, 0.4));
		return buffer
			.add(new MinutiaPositionsLayer(template))
			.render();
	}
}
