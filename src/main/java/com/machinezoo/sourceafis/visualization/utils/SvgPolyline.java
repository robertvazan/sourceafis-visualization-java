package com.machinezoo.sourceafis.visualization.utils;

import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class SvgPolyline {
	private List<DoublePoint> points = new ArrayList<>();
	public SvgPolyline points(List<DoublePoint> points) {
		this.points = points;
		return this;
	}
	private String color;
	public SvgPolyline color(String color) {
		this.color = color;
		return this;
	}
	public SvgPolyline add(DoublePoint point) {
		points.add(point);
		return this;
	}
	public SvgPolyline add(Collection<DoublePoint> points) {
		if (points != null)
			for (DoublePoint point : points)
				add(point);
		return this;
	}
	public DomElement svg() {
		if (points.isEmpty())
			return null;
		StringBuilder buffer = new StringBuilder();
		buffer.append(format(points.get(0)));
		for (int i = 1; i < points.size(); ++i) {
			buffer.append(' ');
			buffer.append(format(points.get(i)));
		}
		return Svg.polyline()
			.stroke(color)
			.fill("none")
			.points(buffer.toString());
	}
	private static String format(DoublePoint point) {
		return String.format("%f,%f", point.x, point.y);
	}
}
