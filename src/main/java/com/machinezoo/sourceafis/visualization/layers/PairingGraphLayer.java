// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.layers;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.rendering.*;
import com.machinezoo.sourceafis.visualization.types.*;

public record PairingGraphLayer(PairingGraph pairing, MatchSide side, Template template) implements LayerModel {
	private DomElement line(EdgePair edge) {
		var reference = MinutiaPoints.center(template.minutiae()[edge.from().side(side)]);
		var neighbor = MinutiaPoints.center(template.minutiae()[edge.to().side(side)]);
		return Svg.line()
			.x1(reference.x())
			.y1(reference.y())
			.x2(neighbor.x())
			.y2(neighbor.y());
	}
	@Override
	public ImageLayer render() {
		var buffer = new LayerBuffer();
		for (var edge : pairing.support())
			buffer.add(line(edge).stroke("yellow"));
		for (var edge : pairing.tree())
			buffer.add(line(edge).strokeWidth(2).stroke("green"));
		buffer.add(new MinutiaPositionsLayer(template));
		var root = MinutiaPoints.center(template.minutiae()[pairing.root().side(side)]);
		buffer.add(Svg.circle()
			.cx(root.x())
			.cy(root.y())
			.r(3.5)
			.fill("blue"));
		return buffer.render();
	}
}
