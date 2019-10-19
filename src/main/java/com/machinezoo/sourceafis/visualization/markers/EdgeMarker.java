// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class EdgeMarker {
	private final double referenceX;
	private final double referenceY;
	private final double neighborX;
	private final double neighborY;
	public EdgeMarker(double referenceX, double referenceY, double neighborX, double neighborY) {
		this.referenceX = referenceX;
		this.referenceY = referenceY;
		this.neighborX = neighborX;
		this.neighborY = neighborY;
	}
	public EdgeMarker(DoublePoint reference, DoublePoint neighbor) {
		this(reference.x, reference.y, neighbor.x, neighbor.y);
	}
	public EdgeMarker(TemplateMinutia reference, TemplateMinutia neighbor) {
		this(reference.center(), neighbor.center());
	}
	public EdgeMarker(PairingEdge edge, MatchSide side, Template template) {
		this(template.minutiae[edge.from().side(side)], template.minutiae[edge.to().side(side)]);
	}
	public DomElement svg() {
		return Svg.line()
			.x1(referenceX)
			.y1(referenceY)
			.x2(neighborX)
			.y2(neighborY);
	}
}
