// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class LineMarker {
	public static DomElement between(DoublePoint start, DoublePoint end) {
		return Svg.line()
			.x1(start.x)
			.y1(start.y)
			.x2(end.x)
			.y2(end.y);
	}
	public static DomElement relative(DoublePoint center, DoublePoint arm) {
		return Svg.line()
			.x1(center.x)
			.y1(center.y)
			.x2(center.x + arm.x)
			.y2(center.y + arm.y);
	}
}
