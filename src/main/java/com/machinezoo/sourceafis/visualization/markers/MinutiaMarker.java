package com.machinezoo.sourceafis.visualization.markers;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class MinutiaMarker {
	private double x;
	public MinutiaMarker x(double x) {
		this.x = x;
		return this;
	}
	private double y;
	public MinutiaMarker y(double y) {
		this.y = y;
		return this;
	}
	private double direction;
	public MinutiaMarker direction(double direction) {
		this.direction = direction;
		return this;
	}
	private MinutiaType type;
	public MinutiaMarker type(MinutiaType type) {
		this.type = type;
		return this;
	}
	private String color;
	public MinutiaMarker color(String color) {
		this.color = color;
		return this;
	}
	public MinutiaMarker at(double x, double y) {
		return this
			.x(x)
			.y(y);
	}
	public MinutiaMarker at(DoublePoint point) {
		return at(point.x, point.y);
	}
	public MinutiaMarker minutia(TemplateMinutia minutia) {
		return this
			.at(minutia.center())
			.direction(minutia.direction)
			.type(minutia.type);
	}
	public DomElement svg() {
		String stroke = color != null ? color : type == MinutiaType.ENDING ? "blue" : "green";
		return Svg.g()
			.add(Svg.circle()
				.cx(0)
				.cy(0)
				.r(3.5)
				.fill("none")
				.stroke(stroke))
			.add(Svg.line()
				.x1(3.5)
				.y1(0)
				.x2(10)
				.y2(0)
				.stroke(stroke))
			.transform("translate(" + x + " " + y + ") rotate(" + DoubleAngle.degrees(direction) + ")");
	}
}
