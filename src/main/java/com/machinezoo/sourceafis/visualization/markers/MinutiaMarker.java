// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.rendering.*;
import com.machinezoo.sourceafis.visualization.svg.*;
import com.machinezoo.sourceafis.visualization.types.*;
import com.machinezoo.stagean.*;

public record MinutiaMarker(MinutiaPoint minutia, boolean removed) implements LayerModel {
	public MinutiaMarker {
		Objects.requireNonNull(minutia);
	}
	public MinutiaMarker(MinutiaPoint minutia) {
		this(minutia, false);
	}
	@Override
	@DraftCode("Use SVG defs.")
	public ImageLayer render() {
		var at = MinutiaPoints.center(minutia);
		var color = removed ? "red" : switch (minutia.type()) {
			case ENDING -> "blue";
			case BIFURCATION -> "green";
		};
		return new LayerFrame(Svg.g()
			.add(Svg.circle()
				.cx(0)
				.cy(0)
				.r(3.5)
				.fill("none")
				.stroke(color))
			.add(Svg.line()
				.x1(3.5)
				.y1(0)
				.x2(10)
				.y2(0)
				.stroke(color))
			.transform(new SvgTransform()
				.translate(at.x(), at.y())
				.rotate(FloatAngles.degrees(minutia.direction()))
				.toString()));
	}
}
