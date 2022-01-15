// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import com.machinezoo.sourceafis.transparency.types.*;

public class MinutiaPoints {
	public static DoublePoint center(MinutiaPoint m) {
		return IntPoints.center(m.position());
	}
}
