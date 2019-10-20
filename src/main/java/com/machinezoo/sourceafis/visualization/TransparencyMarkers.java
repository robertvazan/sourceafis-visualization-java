// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import org.apache.sanselan.color.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class TransparencyMarkers {
	public static DomContent markRectWeight(double weight, IntRect rect) {
		double radius = Math.sqrt(weight) * rect.radius();
		DoublePoint center = rect.center();
		return Svg.circle()
			.cx(center.x)
			.cy(center.y)
			.r(radius)
			.stroke("#080")
			.strokeWidth(0.3)
			.fill("#0f0")
			.fillOpacity(0.2);
	}
	public static DomContent markRectOrientation(DoublePoint orientation, IntRect rect) {
		DoublePoint center = rect.center();
		DoublePoint direction = DoubleAngle.toVector(DoubleAngle.fromOrientation(DoubleAngle.atan(orientation)));
		DoublePoint arm = direction.multiply(0.5 * Math.min(rect.width, rect.height));
		DoublePoint from = center.add(arm);
		DoublePoint to = center.minus(arm);
		return Svg.line()
			.x1(from.x)
			.y1(from.y)
			.x2(to.x)
			.y2(to.y)
			.stroke("red");
	}
	private static DomContent markSkeletonMinutia(SkeletonMinutia minutia, String color) {
		DoublePoint at = minutia.center();
		return Svg.circle()
			.cx(at.x)
			.cy(at.y)
			.r(4)
			.fill("none")
			.stroke(color)
			.strokeWidth(0.7);
	}
	public static DomContent markSkeletonMinutia(SkeletonMinutia minutia) {
		return markSkeletonMinutia(minutia, minutia.ridges.size() == 1 ? "blue" : "cyan");
	}
	public static DomContent markAddedSkeletonMinutia(SkeletonMinutia minutia) {
		return markSkeletonMinutia(minutia, "green");
	}
	public static DomContent markRemovedSkeletonMinutia(SkeletonMinutia minutia) {
		return markSkeletonMinutia(minutia, "red");
	}
	private static DomContent markTemplateMinutia(TemplateMinutia minutia, String color) {
		DoublePoint at = minutia.center();
		return Svg.g()
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
			.transform("translate(" + at.x + " " + at.y + ") rotate(" + DoubleAngle.degrees(minutia.direction) + ")");
	}
	public static DomContent markTemplateMinutia(TemplateMinutia minutia) {
		return markTemplateMinutia(minutia, minutia.type == MinutiaType.ENDING ? "blue" : "green");
	}
	public static DomContent markRemovedTemplateMinutia(TemplateMinutia minutia) {
		return markTemplateMinutia(minutia, "red");
	}
	public static DomContent markMinutiaPosition(TemplateMinutia minutia) {
		DoublePoint at = minutia.center();
		return Svg.circle()
			.cx(at.x)
			.cy(at.y)
			.r(2.5)
			.fill("red");
	}
	public static DomContent markRootMinutiaPosition(TemplateMinutia minutia) {
		DoublePoint at = minutia.center();
		return Svg.circle()
			.cx(at.x)
			.cy(at.y)
			.r(3.5)
			.fill("blue");
	}
	private static String colorEdgeShape(double length, double angle) {
		double stretch = Math.min(1, Math.log1p(length) / Math.log1p(300));
		int color = ColorConversions.convertHSLtoRGB(angle / DoubleAngle.PI2, 1, 0.9 - 0.8 * stretch);
		return String.format("#%06x", color & 0xffffff);
	}
	private static DomContent markEdgeShape(EdgeShape shape, TemplateMinutia reference, TemplateMinutia neighbor, double width) {
		DoublePoint referencePos = reference.center();
		DoublePoint neighborPos = neighbor.center();
		DoublePoint middle = neighborPos.minus(referencePos).multiply(0.5).add(referencePos);
		return new DomFragment()
			.add(Svg.line()
				.x1(referencePos.x)
				.y1(referencePos.y)
				.x2(middle.x)
				.y2(middle.y)
				.stroke(colorEdgeShape(shape.length, shape.referenceAngle))
				.strokeWidth(width))
			.add(Svg.line()
				.x1(neighborPos.x)
				.y1(neighborPos.y)
				.x2(middle.x)
				.y2(middle.y)
				.stroke(colorEdgeShape(shape.length, shape.neighborAngle))
				.strokeWidth(width));
	}
	public static DomContent markNeighborEdge(NeighborEdge edge, int reference, Template template, boolean symmetrical) {
		return markEdgeShape(edge, template.minutiae[reference], template.minutiae[edge.neighbor], symmetrical ? 1.2 : 0.8);
	}
	public static DomContent markIndexedEdge(IndexedEdge edge, Template template) {
		return markEdgeShape(edge, template.minutiae[edge.reference], template.minutiae[edge.neighbor], 0.6);
	}
	private static DomElement markPairingEdge(PairingEdge edge, MatchSide side, Template template) {
		DoublePoint reference = template.minutiae[edge.from().side(side)].center();
		DoublePoint neighbor = template.minutiae[edge.to().side(side)].center();
		return Svg.line()
			.x1(reference.x)
			.y1(reference.y)
			.x2(neighbor.x)
			.y2(neighbor.y);
	}
	public static DomContent markPairingTreeEdge(PairingEdge edge, MatchSide side, Template template) {
		return markPairingEdge(edge, side, template)
			.strokeWidth(2)
			.stroke("green");
	}
	public static DomContent markPairingSupportEdge(PairingEdge edge, MatchSide side, Template template) {
		return markPairingEdge(edge, side, template)
			.stroke("yellow");
	}
}
