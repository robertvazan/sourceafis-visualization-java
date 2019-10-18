package com.machinezoo.sourceafis.visualization.markers;

import org.apache.sanselan.color.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class EdgeShapeMarker {
	private double length;
	public EdgeShapeMarker length(double length) {
		this.length = length;
		return this;
	}
	private double referenceAngle;
	public EdgeShapeMarker referenceAngle(double referenceAngle) {
		this.referenceAngle = referenceAngle;
		return this;
	}
	private double neighborAngle;
	public EdgeShapeMarker neighborAngle(double neighborAngle) {
		this.neighborAngle = neighborAngle;
		return this;
	}
	private double referenceX;
	public EdgeShapeMarker referenceX(double referenceX) {
		this.referenceX = referenceX;
		return this;
	}
	private double referenceY;
	public EdgeShapeMarker referenceY(double referenceY) {
		this.referenceY = referenceY;
		return this;
	}
	private double neighborX;
	public EdgeShapeMarker neighborX(double neighborX) {
		this.neighborX = neighborX;
		return this;
	}
	private double neighborY;
	public EdgeShapeMarker neighborY(double neighborY) {
		this.neighborY = neighborY;
		return this;
	}
	private double width = 1;
	public EdgeShapeMarker width(double width) {
		this.width = width;
		return this;
	}
	public EdgeShapeMarker shape(EdgeShape shape) {
		return this
			.length(shape.length)
			.referenceAngle(shape.referenceAngle)
			.neighborAngle(shape.neighborAngle);
	}
	public EdgeShapeMarker reference(double x, double y) {
		return this
			.referenceX(x)
			.referenceY(y);
	}
	public EdgeShapeMarker neighbor(double x, double y) {
		return this
			.neighborX(x)
			.neighborY(y);
	}
	public EdgeShapeMarker reference(DoublePoint point) {
		return reference(point.x, point.y);
	}
	public EdgeShapeMarker neighbor(DoublePoint point) {
		return neighbor(point.x, point.y);
	}
	public EdgeShapeMarker reference(TemplateMinutia minutia) {
		return reference(minutia.center());
	}
	public EdgeShapeMarker neighbor(TemplateMinutia minutia) {
		return neighbor(minutia.center());
	}
	public EdgeShapeMarker minutiae(Template template, IndexedEdge edge) {
		return this
			.reference(template.minutiae[edge.reference])
			.neighbor(template.minutiae[edge.neighbor]);
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
