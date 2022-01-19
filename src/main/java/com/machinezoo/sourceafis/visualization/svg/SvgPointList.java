// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.svg;

import com.machinezoo.pushmode.dom.*;

public class SvgPointList {
	private final StringBuilder buffer = new StringBuilder();
	public SvgPointList add(double x, double y) {
		if (!buffer.isEmpty())
			buffer.append(' ');
		buffer.append(SvgRounding.position(x));
		buffer.append(',');
		buffer.append(SvgRounding.position(y));
		return this;
	}
	public DomElement polygon() {
		return Svg.polygon().points(toString());
	}
	@Override
	public String toString() {
		return buffer.toString();
	}
}
