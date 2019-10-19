// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import java.util.function.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class HollowMinutiaMarker {
	public double x;
	public double y;
	public double radius = 4;
	public String color = "blue";
	public HollowMinutiaMarker with(Consumer<HollowMinutiaMarker> consumer) {
		consumer.accept(this);
		return this;
	}
	public void at(DoublePoint point) {
		x = point.x;
		y = point.y;
	}
	public void minutia(SkeletonMinutia minutia) {
		at(minutia.center());
		color = minutia.ridges.size() == 1 ? "blue" : "cyan";
	}
	public DomElement svg() {
		return Svg.circle()
			.cx(x)
			.cy(y)
			.r(radius)
			.fill("none")
			.stroke(color)
			.strokeWidth(0.7);
	}
}
