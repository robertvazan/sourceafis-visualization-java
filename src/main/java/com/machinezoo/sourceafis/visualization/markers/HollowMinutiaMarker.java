// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class HollowMinutiaMarker {
	private double x;
	public HollowMinutiaMarker x(double x) {
		this.x = x;
		return this;
	}
	private double y;
	public HollowMinutiaMarker y(double y) {
		this.y = y;
		return this;
	}
	public HollowMinutiaMarker at(double x, double y) {
		return this
			.x(x)
			.y(y);
	}
	public HollowMinutiaMarker at(DoublePoint point) {
		return at(point.x, point.y);
	}
	private double radius = 4;
	public HollowMinutiaMarker radius(double radius) {
		this.radius = radius;
		return this;
	}
	private String color = "blue";
	public HollowMinutiaMarker color(String color) {
		this.color = color;
		return this;
	}
	public HollowMinutiaMarker minutia(SkeletonMinutia minutia) {
		return this
			.at(minutia.center())
			.color(minutia.ridges.size() == 1 ? "blue" : "cyan");
	}
	public DomElement svg() {
		return Svg.circle()
			.cx(x)
			.cy(y)
			.r(radius)
			.fill("none")
			.stroke(color)
			.strokeWidth(0.7);
	}
	public static DomElement of(SkeletonMinutia minutia) {
		return new HollowMinutiaMarker()
			.minutia(minutia)
			.svg();
	}
}
