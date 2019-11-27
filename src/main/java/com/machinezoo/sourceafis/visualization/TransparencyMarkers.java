// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static java.util.stream.Collectors.*;
import java.util.*;
import java.util.stream.*;
import org.apache.sanselan.color.*;
import com.google.common.collect.Streams;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class TransparencyMarkers {
	/*
	 * Method naming:
	 * - mark*() - discrete SVG markers with lots of transparency around them
	 * - paint*() - opaque image (usually pixmap), useful only as a base layer
	 * - overlay*() - semi-transparent image (usually pixmap)
	 * - embed*() - pixmap as an SVG image element
	 * - *Diff() - visual diff relative to previous (or other) stage
	 * - *ModelName*() - renders ModelName (e.g. BooleanMatrix)
	 * - *StageName*() - appropriately renders data from particular algorithm stage (e.g. EqualizedImage)
	 * 
	 * Methods are sorted in the same order in which corresponding data is produced by the algorithm.
	 * All generic and helper methods are defined just before the first method that uses them.
	 */
	public static TransparencyPixmap paintDoubleMatrix(DoubleMatrix matrix) {
		DoubleSummaryStatistics stats = matrix.stream().summaryStatistics();
		TransparencyPixmap writable = new TransparencyPixmap(matrix.size());
		for (int y = 0; y < matrix.height; ++y)
			for (int x = 0; x < matrix.width; ++x)
				writable.set(x, y, TransparencyPixmap.gray(255 - (int)((matrix.get(x, y) - stats.getMin()) / (stats.getMax() - stats.getMin()) * 255)));
		return writable;
	}
	public static TransparencyPixmap paintDecoded(DoubleMatrix matrix) {
		return paintDoubleMatrix(matrix);
	}
	public static TransparencyPixmap paintScaled(DoubleMatrix matrix) {
		return paintDoubleMatrix(matrix);
	}
	private static String mime(byte[] image) {
		if (image[1] == 'P' && image[2] == 'N' && image[3] == 'G')
			return "image/png";
		if (image[0] == (byte)0xff && image[1] == (byte)0xd8)
			return "image/jpeg";
		throw new IllegalArgumentException();
	}
	public static DomContent embedImage(double width, double height, byte[] image) {
		if (image == null)
			return null;
		return Svg.image()
			.width(width)
			.height(height)
			.href("data:" + mime(image) + ";base64," + Base64.getEncoder().encodeToString(image));
	}
	public static DomContent embedImage(IntPoint size, byte[] image) {
		return embedImage(size.x, size.y, image);
	}
	public static DomContent embedImage(BlockMap blocks, byte[] image) {
		return embedImage(blocks.pixels, image);
	}
	private static DomContent markBlockGrid(BlockMap blocks, BlockGrid grid, String color, double width) {
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
	private static DomContent markBlockMap(BlockMap blocks, BlockGrid foreground, BlockGrid background, String color) {
		return new DomFragment()
			.add(markBlockGrid(blocks, background, "#888", 0.1))
			.add(markBlockGrid(blocks, foreground, color, 0.25));
	}
	public static DomContent markBlockMap(BlockMap blocks) {
		return markBlockMap(blocks, blocks.primary, blocks.secondary, "#00c");
	}
	public static DomContent markSecondaryBlockMap(BlockMap blocks) {
		return markBlockMap(blocks, blocks.secondary, blocks.primary, "#080");
	}
	private static String createPolyPoint(double x, double y) {
		return x + "," + y;
	}
	private static DomContent markBlockHistogram(HistogramCube histogram, BlockMap blocks, BlockGrid grid) {
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
		return markers;
	}
	public static DomContent markHistogram(HistogramCube histogram, BlockMap blocks) {
		return markBlockHistogram(histogram, blocks, blocks.primary);
	}
	public static DomContent markSmoothedHistogram(HistogramCube histogram, BlockMap blocks) {
		return markBlockHistogram(histogram, blocks, blocks.secondary);
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
	public static DomContent markBlockWeight(DoubleMatrix matrix, BlockMap blocks) {
		DoubleSummaryStatistics stats = matrix.stream().summaryStatistics();
		DomFragment markers = new DomFragment();
		for (IntPoint at : blocks.primary.blocks) {
			double weight = (matrix.get(at) - stats.getMin()) / (stats.getMax() - stats.getMin());
			markers.add(markRectWeight(weight, blocks.primary.block(at)));
		}
		return markers;
	}
	public static DomContent markClippedContrast(DoubleMatrix contrast, BlockMap blocks) {
		return markBlockWeight(contrast, blocks);
	}
	public static TransparencyPixmap paintBooleanMatrix(BooleanMatrix matrix, int foreground, int background) {
		TransparencyPixmap writable = new TransparencyPixmap(matrix.size());
		writable.fill(background);
		for (IntPoint at : matrix.size())
			if (matrix.get(at))
				writable.set(at, foreground);
		return writable;
	}
	public static DomContent embedPngPixmap(TransparencyPixmap pixmap) {
		return Svg.image()
			.width(pixmap.width)
			.height(pixmap.height)
			.href("data:image/png;base64," + Base64.getEncoder().encodeToString(pixmap.png()));
	}
	public static TransparencyPixmap overlayMask(BooleanMatrix mask) {
		return paintBooleanMatrix(mask, 0x20_ff_ff_00, 0x20_00_ff_ff);
	}
	public static TransparencyPixmap overlayMask(BooleanMatrix mask, BlockMap blocks) {
		return overlayMask(mask.expand(blocks));
	}
	public static TransparencyPixmap overlayAbsoluteContrastMask(BooleanMatrix mask, BlockMap blocks) {
		return overlayMask(mask, blocks);
	}
	public static TransparencyPixmap overlayRelativeContrastMask(BooleanMatrix mask, BlockMap blocks) {
		return overlayMask(mask, blocks);
	}
	public static TransparencyPixmap overlayCombinedMask(BooleanMatrix mask, BlockMap blocks) {
		return overlayMask(mask, blocks);
	}
	public static TransparencyPixmap overlayFilteredMask(BooleanMatrix mask, BlockMap blocks) {
		return overlayMask(mask, blocks);
	}
	public static TransparencyPixmap paintEqualizedImage(DoubleMatrix image) {
		return paintDoubleMatrix(image);
	}
	private static TransparencyPixmap paintPixelwiseOrientation(DoublePointMatrix orientations, int opacity) {
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
	public static TransparencyPixmap paintPixelwiseOrientation(DoublePointMatrix orientations) {
		return paintPixelwiseOrientation(orientations, 0xff);
	}
	public static TransparencyPixmap overlayPixelwiseOrientation(DoublePointMatrix orientations) {
		return paintPixelwiseOrientation(orientations, 0x60);
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
	public static DomContent markBlockOrientation(DoublePointMatrix orientations, BlockMap blocks, BooleanMatrix mask) {
		DomFragment markers = new DomFragment();
		for (IntPoint at : blocks.primary.blocks)
			if (mask == null || mask.get(at))
				markers.add(markRectOrientation(orientations.get(at), blocks.primary.block(at)));
		return markers;
	}
	public static DomContent markSmoothedOrientation(DoublePointMatrix orientations, BlockMap blocks, BooleanMatrix mask) {
		return markBlockOrientation(orientations, blocks, mask);
	}
	public static TransparencyPixmap paintParallelSmoothing(DoubleMatrix image) {
		return paintDoubleMatrix(image);
	}
	public static TransparencyPixmap paintOrthogonalSmoothing(DoubleMatrix image) {
		return paintDoubleMatrix(image);
	}
	public static TransparencyPixmap paintBooleanMatrix(BooleanMatrix matrix) {
		return paintBooleanMatrix(matrix, 0xff_00_00_00, 0xff_ff_ff_ff);
	}
	public static TransparencyPixmap paintBinarizedImage(BooleanMatrix binarized) {
		return paintBooleanMatrix(binarized);
	}
	public static TransparencyPixmap overlayBooleanMatrix(BooleanMatrix matrix) {
		return paintBooleanMatrix(matrix, 0x90_00_ff_ff, 0);
	}
	public static TransparencyPixmap overlayBinarizedImage(BooleanMatrix binarized) {
		return overlayBooleanMatrix(binarized);
	}
	public static TransparencyPixmap paintBooleanMatrixDiff(BooleanMatrix previous, BooleanMatrix next) {
		TransparencyPixmap writable = new TransparencyPixmap(next.size());
		for (int y = 0; y < next.height; ++y)
			for (int x = 0; x < next.width; ++x) {
				boolean original = previous.get(x, y);
				boolean updated = next.get(x, y);
				if (updated)
					writable.set(x, y, original ? 0xff_00_00_00 : 0xff_00_ff_00);
				else
					writable.set(x, y, original ? 0xff_ff_00_00 : 0xff_ff_ff_ff);
			}
		return writable;
	}
	public static TransparencyPixmap paintFilteredBinary(BooleanMatrix filtered) {
		return paintBooleanMatrix(filtered);
	}
	public static TransparencyPixmap paintFilteredBinaryDiff(BooleanMatrix filtered, BooleanMatrix binarized) {
		return paintBooleanMatrixDiff(binarized, filtered);
	}
	public static TransparencyPixmap overlayPixelMask(BooleanMatrix mask) {
		return overlayMask(mask);
	}
	public static TransparencyPixmap overlayInnerMask(BooleanMatrix mask) {
		return overlayMask(mask);
	}
	public static TransparencyPixmap overlayBinarizedSkeleton(BooleanMatrix binarized) {
		return overlayBooleanMatrix(binarized);
	}
	public static TransparencyPixmap overlaySkeletonShadow(BooleanMatrix shadow) {
		return paintBooleanMatrix(shadow, 0xff_ff_00_00, 0);
	}
	public static TransparencyPixmap overlayThinnedSkeleton(BooleanMatrix thinned) {
		return overlaySkeletonShadow(thinned);
	}
	public static TransparencyPixmap overlaySkeletonShadow(SkeletonGraph skeleton) {
		return overlaySkeletonShadow(skeleton.shadow());
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
	public static DomContent markSkeleton(SkeletonGraph skeleton) {
		DomFragment markers = new DomFragment();
		markers.add(embedPngPixmap(overlaySkeletonShadow(skeleton)));
		for (SkeletonMinutia minutia : skeleton.minutiae)
			markers.add(markSkeletonMinutia(minutia));
		return markers;
	}
	public static DomContent markTracedSkeleton(SkeletonGraph skeleton) {
		return markSkeleton(skeleton);
	}
	public static DomContent markAddedSkeletonMinutia(SkeletonMinutia minutia) {
		return markSkeletonMinutia(minutia, "green");
	}
	public static DomContent markRemovedSkeletonMinutia(SkeletonMinutia minutia) {
		return markSkeletonMinutia(minutia, "red");
	}
	public static DomContent paintSkeletonDiff(SkeletonGraph previous, SkeletonGraph next) {
		DomFragment markers = new DomFragment();
		markers.add(embedPngPixmap(paintBooleanMatrixDiff(previous.shadow(), next.shadow())));
		Set<IntPoint> previousMinutiae = previous.minutiae.stream().map(SkeletonMinutia::position).collect(toSet());
		Set<IntPoint> currentMinutiae = next.minutiae.stream().map(SkeletonMinutia::position).collect(toSet());
		for (SkeletonMinutia minutia : previous.minutiae)
			if (!currentMinutiae.contains(minutia.position()))
				markers.add(markRemovedSkeletonMinutia(minutia));
		for (SkeletonMinutia minutia : next.minutiae) {
			if (!previousMinutiae.contains(minutia.position()))
				markers.add(markAddedSkeletonMinutia(minutia));
			else
				markers.add(markSkeletonMinutia(minutia));
		}
		return markers;
	}
	public static DomContent markRemovedDots(SkeletonGraph skeleton) {
		return markSkeleton(skeleton);
	}
	public static DomContent paintRemovedDotsDiff(SkeletonGraph removedDots, SkeletonGraph traced) {
		return paintSkeletonDiff(traced, removedDots);
	}
	public static DomContent markRemovedPores(SkeletonGraph skeleton) {
		return markSkeleton(skeleton);
	}
	public static DomContent paintRemovedPoresDiff(SkeletonGraph removedPores, SkeletonGraph removedDots) {
		return paintSkeletonDiff(removedDots, removedPores);
	}
	public static DomContent markRemovedGaps(SkeletonGraph skeleton) {
		return markSkeleton(skeleton);
	}
	public static DomContent paintRemovedGapsDiff(SkeletonGraph removedGaps, SkeletonGraph removedPores) {
		return paintSkeletonDiff(removedPores, removedGaps);
	}
	public static DomContent markRemovedTails(SkeletonGraph skeleton) {
		return markSkeleton(skeleton);
	}
	public static DomContent paintRemovedTailsDiff(SkeletonGraph removedTails, SkeletonGraph removedGaps) {
		return paintSkeletonDiff(removedGaps, removedTails);
	}
	public static DomContent markRemovedFragments(SkeletonGraph skeleton) {
		return markSkeleton(skeleton);
	}
	public static DomContent paintRemovedFragmentsDiff(SkeletonGraph removedFragments, SkeletonGraph removedTails) {
		return paintSkeletonDiff(removedTails, removedFragments);
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
	public static DomContent markTemplate(Template template) {
		DomFragment markers = new DomFragment();
		for (TemplateMinutia minutia : template.minutiae)
			markers.add(markTemplateMinutia(minutia));
		return markers;
	}
	public static DomContent markSkeletonMinutiae(Template minutiae) {
		return markTemplate(minutiae);
	}
	public static DomContent markRemovedTemplateMinutia(TemplateMinutia minutia) {
		return markTemplateMinutia(minutia, "red");
	}
	public static DomContent markTemplateDiff(Template previous, Template next) {
		DomFragment markers = new DomFragment();
		Set<IntPoint> positions = Arrays.stream(next.minutiae).map(TemplateMinutia::position).collect(toSet());
		for (TemplateMinutia minutia : previous.minutiae)
			if (!positions.contains(minutia.position()))
				markers.add(markRemovedTemplateMinutia(minutia));
		markers.add(markTemplate(next));
		return markers;
	}
	public static DomContent markInnerMinutiae(Template minutiae) {
		return markTemplate(minutiae);
	}
	public static DomContent markInnerMinutiaeDiff(Template inner, Template skeleton) {
		return markTemplateDiff(skeleton, inner);
	}
	public static DomContent markRemovedMinutiaClouds(Template minutiae) {
		return markTemplate(minutiae);
	}
	public static DomContent markRemovedMinutiaCloudsDiff(Template removedClouds, Template inner) {
		return markTemplateDiff(inner, removedClouds);
	}
	public static DomContent markTopMinutiae(Template minutiae) {
		return markTemplate(minutiae);
	}
	public static DomContent markTopMinutiaeDiff(Template top, Template removedClouds) {
		return markTemplateDiff(removedClouds, top);
	}
	public static DomContent markShuffledMinutiae(Template shuffled) {
		return markTemplate(shuffled);
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
	public static DomContent markEdgeTable(EdgeTable table, Template template) {
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
		return markers;
	}
	public static DomContent markIndexedEdge(IndexedEdge edge, Template template) {
		return markEdgeShape(edge, template.minutiae[edge.reference], template.minutiae[edge.neighbor], 0.6);
	}
	public static DomContent markEdgeHash(EdgeHash hash, Template template) {
		DomFragment markers = new DomFragment();
		List<IndexedEdge> edges = hash.edges()
			.sorted(Comparator.comparing(e -> -e.length))
			.collect(toList());
		for (IndexedEdge edge : edges)
			if (edge.reference < edge.neighbor)
				markers.add(markIndexedEdge(edge, template));
		for (TemplateMinutia minutia : template.minutiae)
			markers.add(markMinutiaPosition(minutia));
		return markers;
	}
	public static DomContent markMinutiaPositions(Template template) {
		DomFragment markers = new DomFragment();
		for (TemplateMinutia minutia : template.minutiae)
			markers.add(markMinutiaPosition(minutia));
		return markers;
	}
	public static DomContent markRootPairs(RootPairs roots, Template probe, Template candidate) {
		TransparencySplit split = new TransparencySplit(probe.size, candidate.size);
		for (MinutiaPair pair : roots.pairs) {
			DoublePoint probePos = probe.minutiae[pair.probe].center();
			DoublePoint candidatePos = candidate.minutiae[pair.candidate].center();
			split.add(Svg.line()
				.x1(split.leftX(probePos.x))
				.y1(split.leftY(probePos.y))
				.x2(split.rightX(candidatePos.x))
				.y2(split.rightY(candidatePos.y))
				.stroke("green")
				.strokeWidth(0.4));
		}
		return split.content();
	}
	public static DomContent markRootMinutiaPosition(TemplateMinutia minutia) {
		DoublePoint at = minutia.center();
		return Svg.circle()
			.cx(at.x)
			.cy(at.y)
			.r(3.5)
			.fill("blue");
	}
	public static DomContent markPairing(MatchPairing pairing, MatchSide side, Template template) {
		DomFragment markers = new DomFragment();
		for (PairingEdge edge : pairing.support)
			markers.add(markPairingSupportEdge(edge, side, template));
		for (PairingEdge edge : pairing.tree)
			markers.add(markPairingTreeEdge(edge, side, template));
		for (TemplateMinutia minutia : template.minutiae)
			markers.add(markMinutiaPosition(minutia));
		TemplateMinutia root = template.minutiae[pairing.root.side(side)];
		markers.add(markRootMinutiaPosition(root));
		return markers;
	}
}