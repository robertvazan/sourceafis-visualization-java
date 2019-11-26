// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static java.util.stream.Collectors.*;
import java.util.*;
import java.util.stream.*;
import org.apache.sanselan.color.*;
import com.google.common.collect.Streams;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class TransparencyImages {
	/*
	 * Visualization methods are sorted in the same order in which corresponding data is produced by the algorithm.
	 * Markers are defined just before the first visualization that uses them.
	 * All private helper methods are defined just before the first method that uses them.
	 */
	public static TransparencyPixmap visualizeDoubleMatrix(DoubleMatrix matrix) {
		DoubleSummaryStatistics stats = matrix.stream().summaryStatistics();
		TransparencyPixmap writable = new TransparencyPixmap(matrix.size());
		for (int y = 0; y < matrix.height; ++y)
			for (int x = 0; x < matrix.width; ++x)
				writable.set(x, y, TransparencyPixmap.gray(255 - (int)((matrix.get(x, y) - stats.getMin()) / (stats.getMax() - stats.getMin()) * 255)));
		return writable;
	}
	public static TransparencyPixmap visualizeDecodedImage(DoubleMatrix image) {
		return visualizeDoubleMatrix(image);
	}
	public static TransparencyPixmap visualizeScaledImage(DoubleMatrix image) {
		return visualizeDoubleMatrix(image);
	}
	private static DomContent visualizeBlockGrid(BlockMap blocks, BlockGrid grid, String color, double width) {
		DomFragment markers = new DomFragment();
		for (int x : grid.x) {
			markers.add(Svg.line()
				.x1(x)
				.y1(0)
				.x2(x)
				.y2(blocks.pixels.y)
				.stroke(color)
				.strokeWidth(width));
		}
		for (int y : grid.y) {
			markers.add(Svg.line()
				.x1(0)
				.y1(y)
				.x2(blocks.pixels.x)
				.y2(y)
				.stroke(color)
				.strokeWidth(width));
		}
		return markers;
	}
	private static VisualizationImage visualizeBlockMap(BlockMap blocks, BlockGrid foreground, BlockGrid background, String color, byte[] underlay) {
		return new VisualizationImage()
			.size(blocks)
			.padding(1)
			.underlay(underlay)
			.content(new DomFragment()
				.add(visualizeBlockGrid(blocks, background, "#888", 0.1))
				.add(visualizeBlockGrid(blocks, foreground, color, 0.25)));
	}
	public static VisualizationImage visualizeBlockMap(BlockMap blocks, byte[] underlay) {
		return visualizeBlockMap(blocks, blocks.primary, blocks.secondary, "#00c", underlay);
	}
	public static VisualizationImage visualizeSecondaryBlockMap(BlockMap blocks, byte[] underlay) {
		return visualizeBlockMap(blocks, blocks.secondary, blocks.primary, "#080", underlay);
	}
	private static String createPolyPoint(double x, double y) {
		return x + "," + y;
	}
	private static VisualizationImage visualizeBlockHistogram(HistogramCube histogram, BlockMap blocks, BlockGrid grid, byte[] underlay) {
		DomFragment markers = new DomFragment();
		int slots = 32;
		for (IntPoint at : grid.blocks) {
			int[] resampled = new int[slots];
			for (int z = 0; z < histogram.depth; ++z)
				resampled[z * slots / histogram.depth] += histogram.get(at, z);
			int total = IntStream.of(resampled).sum();
			IntRect block = grid.block(at);
			List<String> points = new ArrayList<>();
			double bottom = block.center().y + 0.8 * block.radius();
			double stretch = 0.9 * block.radius();
			for (int i = 0; i < slots; ++i) {
				double height = 1.6 * block.radius() * Math.log1p(resampled[i]) / Math.log1p(total);
				points.add(createPolyPoint(block.center().x + stretch * (2 * i + 1 - slots) / (slots - 1), bottom - height));
			}
			points.add(createPolyPoint(block.center().x + stretch, bottom));
			points.add(createPolyPoint(block.center().x - stretch, bottom));
			markers.add(Svg.polygon().points(String.join(" ", points)).fill("green").fillOpacity(0.4).stroke("#080").strokeWidth(0.2));
		}
		return new VisualizationImage()
			.size(blocks)
			.underlay(underlay)
			.content(markers);
	}
	public static VisualizationImage visualizeHistogram(HistogramCube histogram, BlockMap blocks, byte[] underlay) {
		return visualizeBlockHistogram(histogram, blocks, blocks.primary, underlay);
	}
	public static VisualizationImage visualizeSmoothedHistogram(HistogramCube histogram, BlockMap blocks, byte[] underlay) {
		return visualizeBlockHistogram(histogram, blocks, blocks.secondary, underlay);
	}
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
	public static VisualizationImage visualizeBlockWeight(DoubleMatrix matrix, BlockMap blocks, byte[] underlay) {
		DoubleSummaryStatistics stats = matrix.stream().summaryStatistics();
		DomFragment markers = new DomFragment();
		for (IntPoint at : blocks.primary.blocks) {
			double weight = (matrix.get(at) - stats.getMin()) / (stats.getMax() - stats.getMin());
			markers.add(markRectWeight(weight, blocks.primary.block(at)));
		}
		return new VisualizationImage()
			.size(blocks)
			.underlay(underlay)
			.content(markers);
	}
	public static VisualizationImage visualizeClippedContrast(DoubleMatrix contrast, BlockMap blocks, byte[] underlay) {
		return visualizeBlockWeight(contrast, blocks, underlay);
	}
	public static TransparencyPixmap visualizeBooleanMatrix(BooleanMatrix matrix, int foreground, int background) {
		TransparencyPixmap writable = new TransparencyPixmap(matrix.size()).fill(background);
		for (IntPoint at : matrix.size())
			if (matrix.get(at))
				writable.set(at, foreground);
		return writable;
	}
	public static VisualizationImage visualizeBooleanMatrix(BooleanMatrix matrix, int foreground, int background, byte[] underlay) {
		TransparencyPixmap writable = new TransparencyPixmap(matrix.size()).fill(background);
		for (IntPoint at : matrix.size())
			if (matrix.get(at))
				writable.set(at, foreground);
		return new VisualizationImage()
			.size(matrix.size())
			.underlay(underlay)
			.content(visualizeBooleanMatrix(matrix, foreground, background).svg());
	}
	public static TransparencyPixmap visualizeBooleanMatrix(BooleanMatrix matrix) {
		return visualizeBooleanMatrix(matrix, TransparencyPixmap.black, TransparencyPixmap.white);
	}
	public static VisualizationImage visualizeBooleanMatrix(BooleanMatrix matrix, byte[] underlay) {
		return visualizeBooleanMatrix(matrix, TransparencyPixmap.color(0, 0xff, 0xff, 0x90), 0, underlay);
	}
	public static VisualizationImage visualizeMask(BooleanMatrix mask, byte[] underlay) {
		return visualizeBooleanMatrix(mask, TransparencyPixmap.color(0xff, 0xff, 0, 0x20), TransparencyPixmap.color(0, 0xff, 0xff, 0x20), underlay);
	}
	public static VisualizationImage visualizeMask(BooleanMatrix mask, BlockMap blocks, byte[] underlay) {
		return visualizeMask(mask.expand(blocks), underlay);
	}
	public static VisualizationImage visualizeAbsoluteContrastMask(BooleanMatrix mask, BlockMap blocks, byte[] underlay) {
		return visualizeMask(mask, blocks, underlay);
	}
	public static VisualizationImage visualizeRelativeContrastMask(BooleanMatrix mask, BlockMap blocks, byte[] underlay) {
		return visualizeMask(mask, blocks, underlay);
	}
	public static VisualizationImage visualizeCombinedMask(BooleanMatrix mask, BlockMap blocks, byte[] underlay) {
		return visualizeMask(mask, blocks, underlay);
	}
	public static VisualizationImage visualizeFilteredMask(BooleanMatrix mask, BlockMap blocks, byte[] underlay) {
		return visualizeMask(mask, blocks, underlay);
	}
	public static TransparencyPixmap visualizeEqualizedImage(DoubleMatrix image) {
		return visualizeDoubleMatrix(image);
	}
	private static TransparencyPixmap visualizePixelwiseOrientation(DoublePointMatrix orientations, int opacity) {
		opacity = opacity << 24;
		TransparencyPixmap writable = new TransparencyPixmap(orientations.size());
		double max = Math.log1p(Streams.stream(orientations.size()).map(orientations::get).mapToDouble(DoublePoint::length).max().orElse(1));
		for (IntPoint at : orientations.size()) {
			DoublePoint vector = orientations.get(at);
			if (vector.x != 0 || vector.y != 0) {
				double angle = DoubleAngle.atan(vector);
				double strength = Math.log1p(vector.length()) / max;
				writable.set(at, ColorConversions.convertHSLtoRGB(angle / DoubleAngle.PI2, 0.2 + 0.8 * strength, 0.5) & 0xffffff | opacity);
			}
		}
		return writable;
	}
	public static TransparencyPixmap visualizePixelwiseOrientation(DoublePointMatrix orientations) {
		return visualizePixelwiseOrientation(orientations, 0xff);
	}
	public static VisualizationImage visualizePixelwiseOrientation(DoublePointMatrix orientations, byte[] underlay) {
		return new VisualizationImage()
			.size(orientations.size())
			.underlay(underlay)
			.content(visualizePixelwiseOrientation(orientations, 0x60).svg());
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
	public static VisualizationImage visualizeBlockOrientation(DoublePointMatrix orientations, BlockMap blocks, BooleanMatrix mask, byte[] underlay) {
		DomFragment markers = new DomFragment();
		for (IntPoint at : blocks.primary.blocks)
			if (mask == null || mask.get(at))
				markers.add(markRectOrientation(orientations.get(at), blocks.primary.block(at)));
		return new VisualizationImage()
			.size(blocks)
			.underlay(underlay)
			.content(markers);
	}
	public static VisualizationImage visualizeSmoothedOrientation(DoublePointMatrix orientations, BlockMap blocks, BooleanMatrix mask, byte[] underlay) {
		return visualizeBlockOrientation(orientations, blocks, mask, underlay);
	}
	public static TransparencyPixmap visualizeParallelSmoothing(DoubleMatrix image) {
		return visualizeDoubleMatrix(image);
	}
	public static TransparencyPixmap visualizeOrthogonalSmoothing(DoubleMatrix image) {
		return visualizeDoubleMatrix(image);
	}
	public static VisualizationImage visualizeBinarizedImage(BooleanMatrix binarized, byte[] underlay) {
		return visualizeBooleanMatrix(binarized, underlay);
	}
	public static TransparencyPixmap visualizeBooleanMatrixDiff(BooleanMatrix previous, BooleanMatrix next) {
		TransparencyPixmap writable = new TransparencyPixmap(next.size());
		for (int y = 0; y < next.height; ++y)
			for (int x = 0; x < next.width; ++x) {
				boolean original = previous.get(x, y);
				boolean updated = next.get(x, y);
				if (updated)
					writable.set(x, y, original ? TransparencyPixmap.black : TransparencyPixmap.green);
				else
					writable.set(x, y, original ? TransparencyPixmap.red : TransparencyPixmap.white);
			}
		return writable;
	}
	public static TransparencyPixmap visualizeFilteredBinaryImage(BooleanMatrix filtered) {
		return visualizeBooleanMatrix(filtered);
	}
	public static TransparencyPixmap visualizeFilteredBinaryImageDiff(BooleanMatrix filtered, BooleanMatrix binarized) {
		return visualizeBooleanMatrixDiff(binarized, filtered);
	}
	public static VisualizationImage visualizePixelMask(BooleanMatrix mask, byte[] underlay) {
		return visualizeMask(mask, underlay);
	}
	public static VisualizationImage visualizeInnerMask(BooleanMatrix mask, byte[] underlay) {
		return visualizeMask(mask, underlay);
	}
	public static VisualizationImage visualizeBinarizedSkeleton(BooleanMatrix binarized, byte[] underlay) {
		return visualizeBooleanMatrix(binarized, underlay);
	}
	public static VisualizationImage visualizeSkeletonShadow(BooleanMatrix shadow, byte[] underlay) {
		return visualizeBooleanMatrix(shadow, TransparencyPixmap.red, 0, underlay);
	}
	public static VisualizationImage visualizeThinnedSkeleton(BooleanMatrix thinned, byte[] underlay) {
		return visualizeSkeletonShadow(thinned, underlay);
	}
	public static VisualizationImage visualizeSkeletonShadow(BooleanMatrix shadow) {
		return visualizeSkeletonShadow(shadow, null);
	}
	public static VisualizationImage visualizeSkeletonShadow(SkeletonGraph skeleton, byte[] underlay) {
		return visualizeSkeletonShadow(skeleton.shadow(), underlay);
	}
	public static VisualizationImage visualizeSkeletonShadow(SkeletonGraph skeleton) {
		return visualizeSkeletonShadow(skeleton, null);
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
	public static VisualizationImage visualizeSkeleton(SkeletonGraph skeleton, byte[] underlay) {
		DomFragment markers = new DomFragment();
		markers.add(visualizeSkeletonShadow(skeleton).svg());
		for (SkeletonMinutia minutia : skeleton.minutiae)
			markers.add(markSkeletonMinutia(minutia));
		return new VisualizationImage()
			.size(skeleton.size)
			.underlay(underlay)
			.content(markers);
	}
	public static VisualizationImage visualizeTracedSkeleton(SkeletonGraph skeleton, byte[] underlay) {
		return visualizeSkeleton(skeleton, underlay);
	}
	public static DomContent markAddedSkeletonMinutia(SkeletonMinutia minutia) {
		return markSkeletonMinutia(minutia, "green");
	}
	public static DomContent markRemovedSkeletonMinutia(SkeletonMinutia minutia) {
		return markSkeletonMinutia(minutia, "red");
	}
	public static VisualizationImage visualizeSkeletonDiff(SkeletonGraph previous, SkeletonGraph next) {
		Set<IntPoint> previousMinutiae = previous.minutiae.stream().map(SkeletonMinutia::position).collect(toSet());
		Set<IntPoint> currentMinutiae = next.minutiae.stream().map(SkeletonMinutia::position).collect(toSet());
		DomFragment markers = new DomFragment();
		for (SkeletonMinutia minutia : previous.minutiae)
			if (!currentMinutiae.contains(minutia.position()))
				markers.add(markRemovedSkeletonMinutia(minutia));
		for (SkeletonMinutia minutia : next.minutiae) {
			if (!previousMinutiae.contains(minutia.position()))
				markers.add(markAddedSkeletonMinutia(minutia));
			else
				markers.add(markSkeletonMinutia(minutia));
		}
		return new VisualizationImage()
			.size(next.size)
			.underlay(visualizeBooleanMatrixDiff(previous.shadow(), next.shadow()).png())
			.content(markers);
	}
	public static VisualizationImage visualizeRemovedDots(SkeletonGraph skeleton, byte[] underlay) {
		return visualizeSkeleton(skeleton, underlay);
	}
	public static VisualizationImage visualizeRemovedDotsDiff(SkeletonGraph removedDots, SkeletonGraph traced) {
		return visualizeSkeletonDiff(traced, removedDots);
	}
	public static VisualizationImage visualizeRemovedPores(SkeletonGraph skeleton, byte[] underlay) {
		return visualizeSkeleton(skeleton, underlay);
	}
	public static VisualizationImage visualizeRemovedPoresDiff(SkeletonGraph removedPores, SkeletonGraph removedDots) {
		return visualizeSkeletonDiff(removedDots, removedPores);
	}
	public static VisualizationImage visualizeRemovedGaps(SkeletonGraph skeleton, byte[] underlay) {
		return visualizeSkeleton(skeleton, underlay);
	}
	public static VisualizationImage visualizeRemovedGapsDiff(SkeletonGraph removedGaps, SkeletonGraph removedPores) {
		return visualizeSkeletonDiff(removedPores, removedGaps);
	}
	public static VisualizationImage visualizeRemovedTails(SkeletonGraph skeleton, byte[] underlay) {
		return visualizeSkeleton(skeleton, underlay);
	}
	public static VisualizationImage visualizeRemovedTailsDiff(SkeletonGraph removedTails, SkeletonGraph removedGaps) {
		return visualizeSkeletonDiff(removedGaps, removedTails);
	}
	public static VisualizationImage visualizeRemovedFragments(SkeletonGraph skeleton, byte[] underlay) {
		return visualizeSkeleton(skeleton, underlay);
	}
	public static VisualizationImage visualizeRemovedFragmentsDiff(SkeletonGraph removedFragments, SkeletonGraph removedTails) {
		return visualizeSkeletonDiff(removedTails, removedFragments);
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
	public static VisualizationImage visualizeTemplate(Template template, byte[] underlay) {
		DomFragment markers = new DomFragment();
		for (TemplateMinutia minutia : template.minutiae)
			markers.add(markTemplateMinutia(minutia));
		return new VisualizationImage()
			.size(template.size)
			.underlay(underlay)
			.content(markers);
	}
	public static VisualizationImage visualizeTemplate(Template template) {
		return visualizeTemplate(template, null);
	}
	public static VisualizationImage visualizeSkeletonMinutiae(Template minutiae, byte[] underlay) {
		return visualizeTemplate(minutiae, underlay);
	}
	public static DomContent markRemovedTemplateMinutia(TemplateMinutia minutia) {
		return markTemplateMinutia(minutia, "red");
	}
	public static VisualizationImage visualizeTemplateDiff(Template previous, Template next, byte[] underlay) {
		DomFragment markers = new DomFragment();
		Set<IntPoint> positions = Arrays.stream(next.minutiae).map(TemplateMinutia::position).collect(toSet());
		for (TemplateMinutia minutia : previous.minutiae)
			if (!positions.contains(minutia.position()))
				markers.add(markRemovedTemplateMinutia(minutia));
		markers.add(visualizeTemplate(next).fragment());
		return new VisualizationImage()
			.size(next.size)
			.underlay(underlay)
			.content(markers);
	}
	public static VisualizationImage visualizeInnerMinutiae(Template minutiae, byte[] underlay) {
		return visualizeTemplate(minutiae, underlay);
	}
	public static VisualizationImage visualizeInnerMinutiaeDiff(Template inner, Template skeleton, byte[] underlay) {
		return visualizeTemplateDiff(skeleton, inner, underlay);
	}
	public static VisualizationImage visualizeRemovedMinutiaClouds(Template minutiae, byte[] underlay) {
		return visualizeTemplate(minutiae, underlay);
	}
	public static VisualizationImage visualizeRemovedMinutiaCloudsDiff(Template removedClouds, Template inner, byte[] underlay) {
		return visualizeTemplateDiff(inner, removedClouds, underlay);
	}
	public static VisualizationImage visualizeTopMinutiae(Template minutiae, byte[] underlay) {
		return visualizeTemplate(minutiae, underlay);
	}
	public static VisualizationImage visualizeTopMinutiaeDiff(Template top, Template removedClouds, byte[] underlay) {
		return visualizeTemplateDiff(removedClouds, top, underlay);
	}
	public static VisualizationImage visualizeShuffledMinutiae(Template shuffled, byte[] underlay) {
		return visualizeTemplate(shuffled, underlay);
	}
	private static class EdgeLine {
		final int reference;
		final NeighborEdge edge;
		EdgeLine(int reference, NeighborEdge edge) {
			this.reference = reference;
			this.edge = edge;
		}
	}
	public static DomContent markMinutiaPosition(TemplateMinutia minutia) {
		DoublePoint at = minutia.center();
		return Svg.circle()
			.cx(at.x)
			.cy(at.y)
			.r(2.5)
			.fill("red");
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
	public static VisualizationImage visualizeEdgeTable(EdgeTable table, Template template, byte[] underlay) {
		DomFragment markers = new DomFragment();
		List<EdgeLine> sorted = IntStream.range(0, table.edges.length)
			.boxed()
			.flatMap(n -> Arrays.stream(table.edges[n]).map(e -> new EdgeLine(n, e)))
			.sorted(Comparator.comparing(e -> -e.edge.length))
			.collect(toList());
		for (EdgeLine line : sorted) {
			boolean symmetrical = Arrays.stream(table.edges[line.edge.neighbor]).anyMatch(e -> e.neighbor == line.reference);
			markers.add(markNeighborEdge(line.edge, line.reference, template, symmetrical));
		}
		for (TemplateMinutia minutia : template.minutiae)
			markers.add(markMinutiaPosition(minutia));
		return new VisualizationImage()
			.size(template.size)
			.underlay(underlay)
			.content(markers);
	}
	public static DomContent markIndexedEdge(IndexedEdge edge, Template template) {
		return markEdgeShape(edge, template.minutiae[edge.reference], template.minutiae[edge.neighbor], 0.6);
	}
	public static VisualizationImage visualizeEdgeHash(EdgeHash hash, Template template, byte[] underlay) {
		DomFragment markers = new DomFragment();
		List<IndexedEdge> edges = hash.edges()
			.sorted(Comparator.comparing(e -> -e.length))
			.collect(toList());
		for (IndexedEdge edge : edges)
			if (edge.reference < edge.neighbor)
				markers.add(markIndexedEdge(edge, template));
		for (TemplateMinutia minutia : template.minutiae)
			markers.add(markMinutiaPosition(minutia));
		return new VisualizationImage()
			.size(template.size)
			.underlay(underlay)
			.content(markers);
	}
	private static VisualizationImage visualizeSideBySide(VisualizationImage left, VisualizationImage right) {
		return new VisualizationImage()
			.width(left.width() + right.width())
			.height(Math.max(left.height(), right.height()))
			.content(new DomFragment()
				.add(Svg.g()
					.add(left.fragment()))
				.add(Svg.g()
					.transform("translate(" + left.width() + ",0)")
					.add(right.fragment())));
	}
	private static DomContent visualizeMinutiaPositions(Template template) {
		DomFragment markers = new DomFragment();
		for (TemplateMinutia minutia : template.minutiae)
			markers.add(markMinutiaPosition(minutia));
		return markers;
	}
	public static VisualizationImage visualizeRootPairs(RootPairs roots, Template probe, Template candidate, byte[] probeUnderlay, byte[] candidateUnderlay) {
		DomFragment links = new DomFragment();
		for (MinutiaPair pair : roots.pairs) {
			DoublePoint probePos = probe.minutiae[pair.probe].center();
			DoublePoint candidatePos = candidate.minutiae[pair.candidate].center();
			links.add(Svg.line()
				.x1(probePos.x)
				.y1(probePos.y)
				.x2(candidatePos.x + probe.size.x)
				.y2(candidatePos.y)
				.stroke("green")
				.strokeWidth(0.4));
		}
		VisualizationImage leftSpace = new VisualizationImage().size(probe.size);
		VisualizationImage rightSpace = new VisualizationImage().size(candidate.size);
		VisualizationImage leftUnderlay = new VisualizationImage()
			.size(probe.size)
			.underlay(probeUnderlay);
		VisualizationImage rightUnderlay = new VisualizationImage()
			.size(candidate.size)
			.underlay(candidateUnderlay);
		VisualizationImage leftMarkers = new VisualizationImage()
			.size(probe.size)
			.content(visualizeMinutiaPositions(probe));
		VisualizationImage rightMarkers = new VisualizationImage()
			.size(candidate.size)
			.content(visualizeMinutiaPositions(candidate));
		return visualizeSideBySide(leftSpace, rightSpace)
			.content(new DomFragment()
				.add(visualizeSideBySide(leftUnderlay, rightUnderlay).fragment())
				.add(links)
				.add(visualizeSideBySide(leftMarkers, rightMarkers).fragment()));
	}
	public static DomContent markRootMinutiaPosition(TemplateMinutia minutia) {
		DoublePoint at = minutia.center();
		return Svg.circle()
			.cx(at.x)
			.cy(at.y)
			.r(3.5)
			.fill("blue");
	}
	public static VisualizationImage visualizePairing(MatchPairing pairing, MatchSide side, Template template, byte[] underlay) {
		DomFragment markers = new DomFragment();
		for (PairingEdge edge : pairing.support)
			markers.add(markPairingSupportEdge(edge, side, template));
		for (PairingEdge edge : pairing.tree)
			markers.add(markPairingTreeEdge(edge, side, template));
		for (TemplateMinutia minutia : template.minutiae)
			markers.add(markMinutiaPosition(minutia));
		TemplateMinutia root = template.minutiae[pairing.root.side(side)];
		markers.add(markRootMinutiaPosition(root));
		return new VisualizationImage()
			.size(template.size)
			.underlay(underlay)
			.content(markers);
	}
	public static VisualizationImage visualizePairing(MatchPairing pairing, Template probe, Template candidate, byte[] probeUnderlay, byte[] candidateUnderlay) {
		return visualizeSideBySide(
			visualizePairing(pairing, MatchSide.PROBE, probe, probeUnderlay),
			visualizePairing(pairing, MatchSide.CANDIDATE, candidate, candidateUnderlay));
	}
}
