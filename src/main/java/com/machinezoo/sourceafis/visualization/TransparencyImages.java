// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static com.machinezoo.sourceafis.visualization.TransparencyMarkers.*;
import static java.util.stream.Collectors.*;
import java.util.*;
import java.util.stream.*;
import org.apache.sanselan.color.*;
import com.google.common.collect.Streams;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;

public class TransparencyImages {
	public static WritableImage visualizeDoubleMatrix(DoubleMatrix matrix) {
		WritableImage writable = new WritableImage(matrix.size());
		for (int y = 0; y < matrix.height; ++y)
			for (int x = 0; x < matrix.width; ++x)
				writable.set(x, y, WritableImage.gray(255 - (int)((matrix.get(x, y) - matrix.stats.getMin()) / (matrix.stats.getMax() - matrix.stats.getMin()) * 255)));
		return writable;
	}
	public static WritableImage visualizeDecodedImage(DoubleMatrix image) {
		return visualizeDoubleMatrix(image);
	}
	public static WritableImage visualizeScaledImage(DoubleMatrix image) {
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
			IntBlock block = grid.block(at);
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
	public static VisualizationImage visualizeBlockWeight(DoubleMatrix matrix, BlockMap blocks, byte[] underlay) {
		DomFragment markers = new DomFragment();
		for (IntPoint at : blocks.primary.blocks) {
			double weight = (matrix.get(at) - matrix.stats.getMin()) / (matrix.stats.getMax() - matrix.stats.getMin());
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
	public static WritableImage visualizeBooleanMatrix(BooleanMatrix matrix, int foreground, int background) {
		WritableImage writable = new WritableImage(matrix.size()).fill(background);
		for (IntPoint at : matrix.size())
			if (matrix.get(at))
				writable.set(at, foreground);
		return writable;
	}
	public static VisualizationImage visualizeBooleanMatrix(BooleanMatrix matrix, int foreground, int background, byte[] underlay) {
		WritableImage writable = new WritableImage(matrix.size()).fill(background);
		for (IntPoint at : matrix.size())
			if (matrix.get(at))
				writable.set(at, foreground);
		return new VisualizationImage()
			.size(matrix.size())
			.underlay(underlay)
			.content(visualizeBooleanMatrix(matrix, foreground, background).svg());
	}
	public static WritableImage visualizeBooleanMatrix(BooleanMatrix matrix) {
		return visualizeBooleanMatrix(matrix, WritableImage.black, WritableImage.white);
	}
	public static VisualizationImage visualizeBooleanMatrix(BooleanMatrix matrix, byte[] underlay) {
		return visualizeBooleanMatrix(matrix, WritableImage.color(0, 0xff, 0xff, 0x90), 0, underlay);
	}
	public static VisualizationImage visualizeMask(BooleanMatrix mask, byte[] underlay) {
		return visualizeBooleanMatrix(mask, WritableImage.color(0xff, 0xff, 0, 0x20), WritableImage.color(0, 0xff, 0xff, 0x20), underlay);
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
	public static WritableImage visualizeEqualizedImage(DoubleMatrix image) {
		return visualizeDoubleMatrix(image);
	}
	private static WritableImage visualizePixelwiseOrientation(DoublePointMatrix orientations, int opacity) {
		opacity = opacity << 24;
		WritableImage writable = new WritableImage(orientations.size());
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
	public static WritableImage visualizePixelwiseOrientation(DoublePointMatrix orientations) {
		return visualizePixelwiseOrientation(orientations, 0xff);
	}
	public static VisualizationImage visualizePixelwiseOrientation(DoublePointMatrix orientations, byte[] underlay) {
		return new VisualizationImage()
			.size(orientations.size())
			.underlay(underlay)
			.content(visualizePixelwiseOrientation(orientations, 0x60).svg());
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
	public static WritableImage visualizeParallelSmoothing(DoubleMatrix image) {
		return visualizeDoubleMatrix(image);
	}
	public static WritableImage visualizeOrthogonalSmoothing(DoubleMatrix image) {
		return visualizeDoubleMatrix(image);
	}
	public static VisualizationImage visualizeBinarizedImage(BooleanMatrix binarized, byte[] underlay) {
		return visualizeBooleanMatrix(binarized, underlay);
	}
	public static WritableImage visualizeBooleanMatrixDiff(BooleanMatrix previous, BooleanMatrix next) {
		WritableImage writable = new WritableImage(next.size());
		for (int y = 0; y < next.height; ++y)
			for (int x = 0; x < next.width; ++x) {
				boolean original = previous.get(x, y);
				boolean updated = next.get(x, y);
				if (updated)
					writable.set(x, y, original ? WritableImage.black : WritableImage.green);
				else
					writable.set(x, y, original ? WritableImage.red : WritableImage.white);
			}
		return writable;
	}
	public static WritableImage visualizeFilteredBinaryImage(BooleanMatrix filtered) {
		return visualizeBooleanMatrix(filtered);
	}
	public static WritableImage visualizeFilteredBinaryImageDiff(BooleanMatrix filtered, BooleanMatrix binarized) {
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
		return visualizeBooleanMatrix(shadow, WritableImage.red, 0, underlay);
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
	public static VisualizationImage visualizeEdgeTable(NeighborEdge[][] table, Template template, byte[] underlay) {
		DomFragment markers = new DomFragment();
		List<EdgeLine> sorted = IntStream.range(0, table.length)
			.boxed()
			.flatMap(n -> Arrays.stream(table[n]).map(e -> new EdgeLine(n, e)))
			.sorted(Comparator.comparing(e -> -e.edge.length))
			.collect(toList());
		for (EdgeLine line : sorted) {
			boolean symmetrical = Arrays.stream(table[line.edge.neighbor]).anyMatch(e -> e.neighbor == line.reference);
			markers.add(markNeighborEdge(line.edge, line.reference, template, symmetrical));
		}
		for (TemplateMinutia minutia : template.minutiae)
			markers.add(markMinutiaPosition(minutia));
		return new VisualizationImage()
			.size(template.size)
			.underlay(underlay)
			.content(markers);
	}
	public static VisualizationImage visualizeEdgeHash(EdgeHash hash, Template template, byte[] underlay) {
		DomFragment markers = new DomFragment();
		for (IndexedEdge edge : hash.edges.stream().sorted(Comparator.comparing(e -> -e.length)).collect(toList()))
			if (edge.reference < edge.neighbor)
				markers.add(markIndexedEdge(edge, template));
		for (TemplateMinutia minutia : template.minutiae)
			markers.add(markMinutiaPosition(minutia));
		return new VisualizationImage()
			.size(template.size)
			.underlay(underlay)
			.content(markers);
	}
	private static DomElement visualizeMinutiaPositions(Template template) {
		DomElement group = Svg.g();
		for (TemplateMinutia minutia : template.minutiae)
			group.add(markMinutiaPosition(minutia));
		return group;
	}
	public static VisualizationImage visualizeMinutiaPairs(MinutiaPair[] pairs, Template probe, Template candidate, byte[] probeUnderlay, byte[] candidateUnderlay) {
		String transform = "translate(" + probe.size.x + ",0)";
		DomFragment content = new DomFragment()
			.add(new EmbeddedImage()
				.width(probe.size.x)
				.height(probe.size.y)
				.image(probeUnderlay)
				.svg())
			.add(new EmbeddedImage()
				.width(candidate.size.x)
				.height(candidate.size.y)
				.image(candidateUnderlay)
				.svg()
				.transform(transform));
		DoublePoint shift = new DoublePoint(probe.size.x, 0);
		for (MinutiaPair pair : pairs) {
			DoublePoint probePos = probe.minutiae[pair.probe].center();
			DoublePoint candidatePos = candidate.minutiae[pair.candidate].center().add(shift);
			content.add(Svg.line()
				.x1(probePos.x)
				.y1(probePos.y)
				.x2(candidatePos.x)
				.y2(candidatePos.y)
				.stroke("green")
				.strokeWidth(0.4));
		}
		content.add(visualizeMinutiaPositions(probe));
		content.add(visualizeMinutiaPositions(candidate).transform(transform));
		return new VisualizationImage()
			.size(new IntPoint(probe.size.x + candidate.size.x, Math.max(probe.size.y, candidate.size.y)))
			.content(content);
	}
	public static VisualizationImage visualizeRootPairs(MinutiaPair[] roots, Template probe, Template candidate, byte[] probeUnderlay, byte[] candidateUnderlay) {
		return visualizeMinutiaPairs(roots, probe, candidate, probeUnderlay, candidateUnderlay);
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
}
