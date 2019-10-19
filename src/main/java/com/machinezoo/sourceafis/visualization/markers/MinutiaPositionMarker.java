// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import java.util.function.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class MinutiaPositionMarker {
	private final double x;
	private final double y;
	public double radius = 2.5;
	public String color = "red";
	public MinutiaPositionMarker with(Consumer<MinutiaPositionMarker> consumer) {
		consumer.accept(this);
		return this;
	}
	public MinutiaPositionMarker(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public MinutiaPositionMarker(DoublePoint point) {
		this(point.x, point.y);
	}
	public MinutiaPositionMarker(TemplateMinutia minutia) {
		this(minutia.center());
	}
	public DomElement svg() {
		return Svg.circle()
			.cx(x)
			.cy(y)
			.r(radius)
			.fill(color);
	}
}
