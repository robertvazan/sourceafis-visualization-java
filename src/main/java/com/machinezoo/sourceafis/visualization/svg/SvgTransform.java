// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.svg;

public class SvgTransform {
	private final StringBuilder buffer = new StringBuilder();
	public SvgTransform translate(double x, double y) {
		if (!buffer.isEmpty())
			buffer.append(' ');
		buffer.append("translate(");
		buffer.append(SvgRounding.position(x));
		buffer.append(' ');
		buffer.append(SvgRounding.position(y));
		buffer.append(')');
		return this;
	}
	public SvgTransform rotate(double deg) {
		if (!buffer.isEmpty())
			buffer.append(' ');
		buffer.append("rotate(");
		buffer.append(SvgRounding.angle(deg));
		buffer.append(')');
		return this;
	}
	@Override
	public String toString() {
		return buffer.toString();
	}
}
