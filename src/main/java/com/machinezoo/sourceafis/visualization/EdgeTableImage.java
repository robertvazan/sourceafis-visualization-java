package com.machinezoo.sourceafis.visualization;

import static java.util.stream.Collectors.*;
import java.util.*;
import java.util.stream.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class EdgeTableImage {
	private final NeighborEdge[][] table;
	private final Template template;
	private final byte[] background;
	public EdgeTableImage(NeighborEdge[][] table, Template template, byte[] background) {
		this.table = table;
		this.template = template;
		this.background = background;
	}
	public DomElement document() {
		DomFragment content = new DomFragment();
		List<EdgeLine> sorted = IntStream.range(0, table.length)
			.boxed()
			.flatMap(n -> Arrays.stream(table[n]).map(e -> new EdgeLine(n, e)))
			.sorted(Comparator.comparing(e -> -e.edge.length))
			.collect(toList());
		for (EdgeLine line : sorted) {
			boolean symmetrical = Arrays.stream(table[line.edge.neighbor]).anyMatch(e -> e.neighbor == line.reference);
			content.add(new EdgeShapeMarker()
				.with(m -> {
					m.shape(line.edge);
					m.reference(template.minutiae[line.reference]);
					m.neighbor(template.minutiae[line.edge.neighbor]);
					m.width = symmetrical ? 1.2 : 0.8;
				})
				.svg());
		}
		for (TemplateMinutia minutia : template.minutiae)
			content.add(new MinutiaPositionMarker(minutia).svg());
		return new SvgImage()
			.size(template.size)
			.background(background)
			.content(content)
			.document();
	}
	private static class EdgeLine {
		final int reference;
		final NeighborEdge edge;
		EdgeLine(int reference, NeighborEdge edge) {
			this.reference = reference;
			this.edge = edge;
		}
	}
}
