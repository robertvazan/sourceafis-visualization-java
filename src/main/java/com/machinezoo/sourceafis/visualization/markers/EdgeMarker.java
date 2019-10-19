// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class EdgeMarker {
	private double referenceX;
	public EdgeMarker referenceX(double referenceX) {
		this.referenceX = referenceX;
		return this;
	}
	private double referenceY;
	public EdgeMarker referenceY(double referenceY) {
		this.referenceY = referenceY;
		return this;
	}
	public EdgeMarker reference(double x, double y) {
		return this
			.referenceX(x)
			.referenceY(y);
	}
	private double neighborX;
	public EdgeMarker neighborX(double neighborX) {
		this.neighborX = neighborX;
		return this;
	}
	private double neighborY;
	public EdgeMarker neighborY(double neighborY) {
		this.neighborY = neighborY;
		return this;
	}
	public EdgeMarker neighbor(double x, double y) {
		return this
			.neighborX(x)
			.neighborY(y);
	}
	public EdgeMarker reference(DoublePoint point) {
		return reference(point.x, point.y);
	}
	public EdgeMarker neighbor(DoublePoint point) {
		return neighbor(point.x, point.y);
	}
	public EdgeMarker reference(TemplateMinutia minutia) {
		return reference(minutia.center());
	}
	public EdgeMarker neighbor(TemplateMinutia minutia) {
		return neighbor(minutia.center());
	}
	public EdgeMarker edge(PairingEdge edge, MatchSide side, Template template) {
		return this
			.reference(template.minutiae[edge.from().side(side)])
			.neighbor(template.minutiae[edge.to().side(side)]);
	}
	public DomElement svg() {
		return Svg.line()
			.x1(referenceX)
			.y1(referenceY)
			.x2(neighborX)
			.y2(neighborY);
	}
	public static DomElement of(PairingEdge edge, MatchSide side, Template template) {
		return new EdgeMarker()
			.edge(edge, side, template)
			.svg();
	}
}
