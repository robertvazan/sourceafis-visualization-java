// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import com.machinezoo.pushmode.dom.*;

public class SvgPolygon {
	private final StringBuilder buffer = new StringBuilder();
	public SvgPolygon add(double x, double y) {
		if (!buffer.isEmpty())
			buffer.append(' ');
		buffer.append(Doubles.svg(x));
		buffer.append(',');
		buffer.append(Doubles.svg(y));
		return this;
	}
	public String points() {
		return buffer.toString();
	}
	public DomElement element() {
		return Svg.polygon().points(points());
	}
	@Override
	public String toString() {
		return points();
	}
}
