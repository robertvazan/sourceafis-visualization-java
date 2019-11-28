// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class TransparencySplit extends TransparencyImage {
	private final double rightX;
	public TransparencySplit(IntPoint left, IntPoint right) {
		super(left.x + right.x, Math.max(left.y, right.y));
		rightX = left.x;
	}
	public TransparencySplit(TransparencyImage left, TransparencyImage right) {
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
		return new DoublePoint(rightX(at.x), rightY(at.y));
	}
	public TransparencySplit add(DomContent content) {
		super.add(content);
		return this;
	}
	public TransparencySplit image(byte[] image) {
		super.image(image);
		return this;
	}
	public TransparencySplit png(TransparencyPixmap pixmap) {
		super.png(pixmap);
		return this;
	}
	public TransparencySplit jpeg(TransparencyPixmap pixmap) {
		super.jpeg(pixmap);
		return this;
	}
	public TransparencySplit left(DomContent content) {
		return add(Svg.g().add(content));
	}
	public TransparencySplit right(DomContent content) {
		return add(Svg.g()
			.transform("translate(" + rightX + ",0)")
			.add(content));
	}
	public TransparencySplit left(TransparencyImage image) {
		return left(image.content());
	}
	public TransparencySplit right(TransparencyImage image) {
		return right(image.content());
	}
}
