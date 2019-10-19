// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.markers;

import java.util.function.*;
import org.apache.sanselan.color.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class EdgeShapeMarker {
	public double length;
	public double referenceAngle;
	public double neighborAngle;
	public double referenceX;
	public double referenceY;
	public double neighborX;
	public double neighborY;
	public double width = 1;
	public EdgeShapeMarker with(Consumer<EdgeShapeMarker> consumer) {
		consumer.accept(this);
		return this;
	}
	public void shape(EdgeShape shape) {
		length = shape.length;
		referenceAngle = shape.referenceAngle;
		neighborAngle = shape.neighborAngle;
	}
	public void reference(DoublePoint point) {
		referenceX = point.x;
		referenceY = point.y;
	}
	public void neighbor(DoublePoint point) {
		neighborX = point.x;
		neighborY = point.y;
	}
	public void reference(TemplateMinutia minutia) {
		reference(minutia.center());
	}
	public void neighbor(TemplateMinutia minutia) {
		neighbor(minutia.center());
	}
	public void minutiae(Template template, IndexedEdge edge) {
		reference(template.minutiae[edge.reference]);
		neighbor(template.minutiae[edge.neighbor]);
	}
	public DomContent svg() {
		DoublePoint referencePos = new DoublePoint(referenceX, referenceY);
		DoublePoint neighborPos = new DoublePoint(neighborX, neighborY);
		DoublePoint arm = neighborPos.minus(referencePos).multiply(0.5);
		return new DomFragment()
			.add(LineMarker.relative(referencePos, arm)
				.stroke(color(referenceAngle))
				.strokeWidth(width))
			.add(LineMarker.relative(neighborPos, arm.negate())
				.stroke(color(neighborAngle))
				.strokeWidth(width));
	}
	private String color(double angle) {
		double stretch = Math.min(1, Math.log1p(length) / Math.log1p(300));
		int color = ColorConversions.convertHSLtoRGB(angle / DoubleAngle.PI2, 1, 0.9 - 0.8 * stretch);
		return String.format("#%06x", color & 0xffffff);
	}
}
