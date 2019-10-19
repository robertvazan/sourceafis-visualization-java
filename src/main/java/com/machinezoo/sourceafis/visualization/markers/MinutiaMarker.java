// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import java.util.function.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class MinutiaMarker {
	public double x;
	public double y;
	public double direction;
	public MinutiaType type;
	public String color;
	public MinutiaMarker with(Consumer<MinutiaMarker> consumer) {
		consumer.accept(this);
		return this;
	}
	public MinutiaMarker() {
	}
	public MinutiaMarker(DoublePoint point) {
		x = point.x;
		y = point.y;
	}
	public MinutiaMarker(TemplateMinutia minutia) {
		this(minutia.center());
		direction = minutia.direction;
		type = minutia.type;
	}
	public DomElement svg() {
		String stroke = color != null ? color : type == MinutiaType.ENDING ? "blue" : "green";
		return Svg.g()
			.add(Svg.circle()
				.cx(0)
				.cy(0)
				.r(3.5)
				.fill("none")
				.stroke(stroke))
			.add(Svg.line()
				.x1(3.5)
				.y1(0)
				.x2(10)
				.y2(0)
				.stroke(stroke))
			.transform("translate(" + x + " " + y + ") rotate(" + DoubleAngle.degrees(direction) + ")");
	}
}
