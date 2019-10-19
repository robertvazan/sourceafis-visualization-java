// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class AnglePieMarker {
	private double angle;
	public AnglePieMarker angle(double angle) {
		this.angle = angle;
		return this;
	}
	private double x;
	public AnglePieMarker x(double x) {
		this.x = x;
		return this;
	}
	private double y;
	public AnglePieMarker y(double y) {
		this.y = y;
		return this;
	}
	private double radius = 10;
	public AnglePieMarker radius(double radius) {
		this.radius = radius;
		return this;
	}
	private String color = "yellow";
	public AnglePieMarker color(String color) {
		this.color = color;
		return this;
	}
	public AnglePieMarker at(DoublePoint at) {
		x = at.x;
		y = at.y;
		return this;
	}
	public DomContent svg() {
		double normalized = angle;
		while (normalized > 2 * Math.PI + 0.001)
			normalized -= 2 * Math.PI;
		while (normalized < 0)
			normalized += 2 * Math.PI;
		double slice = 0.5 * Math.PI - normalized;
		double ax = radius * Math.cos(slice);
		double ay = radius * Math.sin(slice);
		if (normalized >= 2 * Math.PI - 0.001) {
			return Svg.circle()
				.cx(x)
				.cy(y)
				.r(radius)
				.stroke(color)
				.strokeWidth(0.5)
				.fill(color)
				.fillOpacity(0.3);
		}
		return new SvgPath()
			.move(x, y - radius)
			.arc(radius, radius, 0, normalized >= Math.PI, true, x + ax, y - ay)
			.line(x, y)
			.close()
			.svg()
			.stroke(color)
			.strokeWidth(0.5)
			.fill(color)
			.fillOpacity(0.3);
	}
}
