package com.machinezoo.sourceafis.visualization;

import static java.util.stream.Collectors.*;
import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class EdgeHashImage {
	private final EdgeHash edgeHash;
	private final Template template;
	private final byte[] background;
	public EdgeHashImage(EdgeHash edgeHash, Template template, byte[] background) {
		this.edgeHash = edgeHash;
		this.template = template;
		this.background = background;
	}
	public DomElement document() {
		DomFragment content = new DomFragment();
		for (IndexedEdge edge : edgeHash.edges.stream().sorted(Comparator.comparing(e -> -e.length)).collect(toList())) {
			if (edge.reference < edge.neighbor) {
				content.add(new EdgeShapeMarker()
					.with(m -> {
						m.shape(edge);
						m.minutiae(template, edge);
						m.width = 0.6;
					})
					.svg());
			}
		}
		for (TemplateMinutia minutia : template.minutiae)
			content.add(new MinutiaPositionMarker(minutia).svg());
		return new SvgImage()
			.size(template.size)
			.background(background)
			.content(content)
			.document();
	}
}
