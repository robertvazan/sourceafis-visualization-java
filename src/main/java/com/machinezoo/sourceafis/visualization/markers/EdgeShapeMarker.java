// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import java.awt.*;
import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.rendering.*;
import com.machinezoo.sourceafis.visualization.types.*;
import com.machinezoo.stagean.*;

public record EdgeShapeMarker(EdgeShape shape, MinutiaPoint reference, MinutiaPoint neighbor, double thickness) implements LayerModel {
	public EdgeShapeMarker {
		Objects.requireNonNull(shape);
		Objects.requireNonNull(reference);
		Objects.requireNonNull(neighbor);
	}
	public EdgeShapeMarker(IndexedEdge edge, Template template, double thickness) {
		this(edge, template.minutiae()[edge.reference()], template.minutiae()[edge.neighbor()], thickness);
	}
	@DraftCode("")
	private static String color(double length, double angle) {
		double stretch = Math.min(1, Math.log1p(length) / Math.log1p(300));
		int color = Color.HSBtoRGB((float)(angle / DoubleAnglesEx.PI2), 1.0f, (float)(1 - 0.5 * stretch));
		return String.format("#%06x", color & 0xffffff);
	}
	@Override
	public ImageLayer render() {
		var rend = MinutiaPoints.center(reference);
		var nend = MinutiaPoints.center(neighbor);
		var middle = DoublePoints.sum(DoublePoints.multiply(0.5, DoublePoints.difference(nend, rend)), rend);
		return new LayerFrame(new DomFragment()
			.add(Svg.line()
				.x1(rend.x())
				.y1(rend.y())
				.x2(middle.x())
				.y2(middle.y())
				.stroke(color(shape.length(), shape.referenceAngle()))
				.strokeWidth(thickness))
			.add(Svg.line()
				.x1(nend.x())
				.y1(nend.y())
				.x2(middle.x())
				.y2(middle.y())
				.stroke(color(shape.length(), shape.neighborAngle()))
				.strokeWidth(thickness)));
	}
}
