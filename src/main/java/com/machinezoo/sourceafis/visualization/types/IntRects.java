// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.types;

import com.machinezoo.sourceafis.transparency.types.*;

public class IntRects {
	public static DoublePoint center(IntRect r) {
		return new DoublePoint(r.x() + 0.5 * r.width(), r.y() + 0.5 * r.height());
	}
	public static double radius(IntRect r) {
		return 0.5 * Math.min(r.width(), r.height());
	}
}
