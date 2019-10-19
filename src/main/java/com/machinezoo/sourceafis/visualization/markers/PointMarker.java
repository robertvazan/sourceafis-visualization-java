// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class PointMarker {
	public static DomElement circle(DoublePoint at) {
		return Svg.circle()
			.cx(at.x)
			.cy(at.y);
	}
}
