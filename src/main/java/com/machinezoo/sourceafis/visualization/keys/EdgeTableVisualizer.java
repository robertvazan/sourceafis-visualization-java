// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.layers.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.rendering.*;
import one.util.streamex.*;

public record EdgeTableVisualizer() implements VectorVisualizer {
	@Override
	public EdgeTableKey key() {
		return new EdgeTableKey();
	}
	@Override
	public Set<TransparencyKey<?>> dependencies(TransparentOperation operation) {
		return Set.of(key(), new OutputTemplateKey(), new InputTemplateKey(), new InputImageKey(), new InputGrayscaleKey());
	}
	@Override
	public VectorImage visualize(TransparencyArchive archive) {
		var template = archive.get(new OutputTemplateKey()).or(() -> archive.get(new InputTemplateKey())).orElseThrow().deserialize().unpack();
		var buffer = new VectorBuffer(template.size())
			.background(archive);
		var edges = archive.deserialize(key()).orElseThrow();
		var sorted = IntStreamEx.range(edges.length)
			.flatMapToObj(r -> Arrays.stream(edges[r])
				.map(e -> new IndexedEdge(e.length(), e.referenceAngle(), e.neighborAngle(), r, e.neighbor())))
			.sorted(Comparator.comparing(e -> -e.length()))
			.toList();
		for (var edge : sorted) {
			boolean symmetrical = Arrays.stream(edges[edge.neighbor()]).anyMatch(e -> e.neighbor() == edge.reference());
			buffer.add(new EdgeShapeMarker(edge, template, symmetrical ? 1.2 : 0.8));
		}
		return buffer
			.add(new MinutiaPositionsLayer(template))
			.render();
	}
}
