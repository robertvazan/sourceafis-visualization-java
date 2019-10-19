// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class MinutiaPositionMarker {
	private double x;
	public MinutiaPositionMarker x(double x) {
		this.x = x;
		return this;
	}
	private double y;
	public MinutiaPositionMarker y(double y) {
		this.y = y;
		return this;
	}
	private double radius = 2.5;
	public MinutiaPositionMarker radius(double radius) {
		this.radius = radius;
		return this;
	}
	private String color = "red";
	public MinutiaPositionMarker color(String color) {
		this.color = color;
		return this;
	}
	public MinutiaPositionMarker at(double x, double y) {
		return this
			.x(x)
			.y(y);
	}
	public MinutiaPositionMarker at(DoublePoint point) {
		return at(point.x, point.y);
	}
	public MinutiaPositionMarker at(TemplateMinutia minutia) {
		return at(minutia.center());
	}
	public DomElement svg() {
		return Svg.circle()
			.cx(x)
			.cy(y)
			.r(radius)
			.fill(color);
	}
	public static DomElement of(TemplateMinutia minutia) {
		return new MinutiaPositionMarker()
			.at(minutia)
			.svg();
	}
}
