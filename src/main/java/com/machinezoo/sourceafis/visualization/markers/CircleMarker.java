package com.machinezoo.sourceafis.visualization.markers;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class CircleMarker {
	private double x;
	public CircleMarker x(double x) {
		this.x = x;
		return this;
	}
	private double y;
	public CircleMarker y(double y) {
		this.y = y;
		return this;
	}
	public CircleMarker at(double x, double y) {
		return this
			.x(x)
			.y(y);
	}
	public CircleMarker at(DoublePoint point) {
		return at(point.x, point.y);
	}
	public DomElement svg() {
		return Svg.circle()
			.cx(x)
			.cy(y);
	}
	public static DomElement svg(DoublePoint point) {
		return new CircleMarker().at(point).svg();
	}
}
