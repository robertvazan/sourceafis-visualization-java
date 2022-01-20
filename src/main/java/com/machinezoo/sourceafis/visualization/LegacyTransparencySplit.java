// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.types.*;

public class LegacyTransparencySplit extends LegacyTransparencyImage {
	private final double rightX;
	public LegacyTransparencySplit(IntPoint left, IntPoint right) {
		super(left.x() + right.x(), Math.max(left.y(), right.y()));
		rightX = left.x();
	}
	public LegacyTransparencySplit(LegacyTransparencyImage left, LegacyTransparencyImage right) {
		super(left.width() + left.padding() + right.padding() + right.width(), Math.max(left.height(), right.height()));
		padding(Math.max(left.padding(), right.padding()));
		rightX = left.width() + left.padding() + right.padding();
		left(left);
		right(right);
	}
	public double leftX(double x) {
		return x;
	}
	public double leftY(double y) {
		return y;
	}
	public DoublePoint left(DoublePoint at) {
		return at;
	}
	public double rightX(double x) {
		return rightX + x;
	}
	public double rightY(double y) {
		return y;
	}
	public DoublePoint right(DoublePoint at) {
		return new DoublePoint(rightX(at.x()), rightY(at.y()));
	}
	public LegacyTransparencySplit add(DomContent content) {
		super.add(content);
		return this;
	}
	public LegacyTransparencySplit image(byte[] image) {
		super.image(image);
		return this;
	}
	public LegacyTransparencySplit png(LegacyTransparencyPixmap pixmap) {
		super.png(pixmap);
		return this;
	}
	public LegacyTransparencySplit jpeg(LegacyTransparencyPixmap pixmap) {
		super.jpeg(pixmap);
		return this;
	}
	public LegacyTransparencySplit left(DomContent content) {
		return add(Svg.g().add(content));
	}
	public LegacyTransparencySplit right(DomContent content) {
		return add(Svg.g()
			.transform("translate(" + rightX + ",0)")
			.add(content));
	}
	public LegacyTransparencySplit left(LegacyTransparencyImage image) {
		return left(image.content());
	}
	public LegacyTransparencySplit right(LegacyTransparencyImage image) {
		return right(image.content());
	}
}
