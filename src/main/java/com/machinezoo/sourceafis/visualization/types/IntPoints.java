// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.types;

import com.machinezoo.sourceafis.transparency.types.*;
import one.util.streamex.*;

public class IntPoints {
	public static StreamEx<IntPoint> stream(IntPoint p) {
		return IntStreamEx.range(p.y())
			.flatMapToObj(y -> IntStreamEx.range(p.x()).mapToObj(x -> new IntPoint(x, y)));
	}
	public static DoublePoint center(IntPoint p) {
		return new DoublePoint(p.x() + 0.5, p.y() + 0.5);
	}
}
