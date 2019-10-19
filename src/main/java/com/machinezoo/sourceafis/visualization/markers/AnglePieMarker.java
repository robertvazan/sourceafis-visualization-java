// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import java.util.function.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class AnglePieMarker {
	public double angle;
	public double x;
	public double y;
	public double radius = 10;
	public String color = "yellow";
	public AnglePieMarker with(Consumer<AnglePieMarker> consumer) {
		consumer.accept(this);
		return this;
	}
	public void at(DoublePoint at) {
		x = at.x;
		y = at.y;
	}
	public DomContent svg() {
		double normalized = angle;
		while (normalized > 2 * Math.PI + 0.001)
			normalized -= 2 * Math.PI;
		while (normalized < 0)
			normalized += 2 * Math.PI;
		double slice = 0.5 * Math.PI - normalized;
		double ax = radius * Math.cos(slice);
		double ay = radius * Math.sin(slice);
		if (normalized >= 2 * Math.PI - 0.001) {
			return Svg.circle()
				.cx(x)
				.cy(y)
				.r(radius)
				.stroke(color)
				.strokeWidth(0.5)
				.fill(color)
				.fillOpacity(0.3);
		}
		return new SvgPath()
			.move(x, y - radius)
			.arc(radius, radius, 0, normalized >= Math.PI, true, x + ax, y - ay)
			.line(x, y)
			.close()
			.svg()
			.stroke(color)
			.strokeWidth(0.5)
			.fill(color)
			.fillOpacity(0.3);
	}
}
