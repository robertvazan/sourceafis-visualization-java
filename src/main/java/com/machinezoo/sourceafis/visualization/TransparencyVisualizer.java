// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.keys.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public interface TransparencyVisualizer {
	TransparencyKey<?> key();
	default TransparentOperation operation() {
		return key().operation();
	}
	/*
	 * Specifying MIME/extension on visualizer in addition to specifying it on generated visualization is a tradeoff.
	 * Calling code can know upfront what output to expect, but visualizers lose freedom to switch MIME based on content.
	 * This hardcoding of output type also manifests in specialized visualizer interfaces.
	 * Fortunately, visualizers are specialized enough to know their output type with certainty.
	 * Switching MIME type is possible only in some degenerate cases when optional dependencies are missing
	 * and output is consequently greatly simplified. These corner cases are not worth the effort and complexity.
	 */
	String mime();
	default String extension() {
		return ImageMime.extension(mime());
	}
	default Set<TransparencyKey<?>> dependencies(TransparentOperation operation) {
		return Set.of(key());
	}
	TransparencyImage visualize(TransparencyArchive archive);
	static List<TransparencyVisualizer> all() {
		var all = new ArrayList<TransparencyVisualizer>();
		all.addAll(List.of(
			new DecodedImageVisualizer(),
			new ScaledImageVisualizer(),
			new PrimaryBlocksVisualizer(),
			new SecondaryBlocksVisualizer(),
			new HistogramVisualizer(),
			new SmoothedHistogramVisualizer(),
			new ContrastVisualizer(),
			new AbsoluteContrastMaskVisualizer(),
			new RelativeContrastMaskVisualizer(),
			new CombinedMaskVisualizer(),
			new FilteredMaskVisualizer(),
			new EqualizedImageVisualizer(),
			new PixelwiseOrientationVisualizer(),
			new BlockOrientationVisualizer(),
			new SmoothedOrientationVisualizer(),
			new ParallelSmoothingVisualizer(),
			new OrthogonalSmoothingVisualizer(),
			new BinarizedImageVisualizer(),
			new FilteredBinaryImageVisualizer(),
			new PixelMaskVisualizer(),
			new InnerMaskVisualizer()));
		for (var skeleton : SkeletonType.values()) {
			all.addAll(List.of(
				new BinarizedSkeletonVisualizer(skeleton),
				new ThinnedSkeletonVisualizer(skeleton),
				new TracedSkeletonVisualizer(skeleton),
				new RemovedDotsVisualizer(skeleton),
				new RemovedPoresVisualizer(skeleton),
				new RemovedGapsVisualizer(skeleton),
				new RemovedTailsVisualizer(skeleton),
				new RemovedFragmentsVisualizer(skeleton)));
		}
		all.addAll(List.of(
			new SkeletonMinutiaeVisualizer(),
			new InnerMinutiaeVisualizer(),
			new RemovedMinutiaCloudsVisualizer(),
			new TopMinutiaeVisualizer(),
			new ShuffledMinutiaeVisualizer(),
			new EdgeTableVisualizer(),
			new EdgeHashVisualizer(),
			new RootsVisualizer(),
			new PairingVisualizer()));
		return Collections.unmodifiableList(all);
	}
}
