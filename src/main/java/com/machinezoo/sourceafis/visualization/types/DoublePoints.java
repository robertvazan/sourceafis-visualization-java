// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.types;

import com.machinezoo.sourceafis.transparency.types.*;

public class DoublePoints {
	public static double lengthSq(DoublePoint p) {
		return Doubles.sq(p.x()) + Doubles.sq(p.y());
	}
	public static double length(DoublePoint p) {
		return Math.sqrt(lengthSq(p));
	}
	public static DoublePoint multiply(double f, DoublePoint p) {
		return new DoublePoint(f * p.x(), f * p.y());
	}
	public static DoublePoint sum(DoublePoint a, DoublePoint b) {
		return new DoublePoint(a.x() + b.x(), a.y() + b.y());
	}
	public static DoublePoint difference(DoublePoint a, DoublePoint b) {
		return new DoublePoint(a.x() - b.x(), a.y() - b.y());
	}
}
