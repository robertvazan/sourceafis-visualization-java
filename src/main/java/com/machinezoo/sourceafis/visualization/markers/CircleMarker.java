// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import java.util.function.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class CircleMarker {
	public double x;
	public double y;
	public CircleMarker with(Consumer<CircleMarker> consumer) {
		consumer.accept(this);
		return this;
	}
	public void at(DoublePoint at) {
		x = at.x;
		y = at.y;
	}
	public DomElement svg() {
		return Svg.circle()
			.cx(x)
			.cy(y);
	}
}
