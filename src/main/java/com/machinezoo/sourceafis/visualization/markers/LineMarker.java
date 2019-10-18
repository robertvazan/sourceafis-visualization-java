package com.machinezoo.sourceafis.visualization.markers;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class LineMarker {
	private double x1;
	public LineMarker x1(double x1) {
		this.x1 = x1;
		return this;
	}
	private double y1;
	public LineMarker y1(double y1) {
		this.y1 = y1;
		return this;
	}
	private double x2;
	public LineMarker x2(double x2) {
		this.x2 = x2;
		return this;
	}
	private double y2;
	public LineMarker y2(double y2) {
		this.y2 = y2;
		return this;
	}
	private boolean relative;
	public LineMarker relative(boolean relative) {
		this.relative = relative;
		return this;
	}
	public LineMarker from(DoublePoint point) {
		return this
			.x1(point.x)
			.y1(point.y);
	}
	public LineMarker to(DoublePoint point) {
		return this
			.x2(point.x)
			.y2(point.y);
	}
	public DomElement svg() {
		if (!relative) {
			return Svg.line()
				.x1(x1)
				.y1(y1)
				.x2(x2)
				.y2(y2);
		} else {
			return Svg.line()
				.x1(x1)
				.y1(y1)
				.x2(x1 + x2)
				.y2(y1 + y2);
		}
	}
	public static DomElement between(DoublePoint from, DoublePoint to) {
		return new LineMarker()
			.from(from)
			.to(to)
			.svg();
	}
	public static DomElement relative(DoublePoint center, DoublePoint arm) {
		return new LineMarker()
			.from(center)
			.to(arm)
			.relative(true)
			.svg();
	}
}
