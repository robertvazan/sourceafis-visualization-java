// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static com.machinezoo.sourceafis.visualization.TransparencyImages.*;
import java.util.*;
import com.machinezoo.sourceafis.transparency.*;

public class ExtractorImages {
	private final TransparencyArchive archive;
	private final TransparencyContext context;
	public ExtractorImages(TransparencyArchive archive, TransparencyContext context) {
		Objects.requireNonNull(archive);
		Objects.requireNonNull(context);
		this.archive = archive;
		this.context = context;
	}
	private Template output() {
		return Optional.ofNullable(context.output).map(Template::parse).orElse(null);
	}
	public TransparencyPixmap decoded() {
		return visualizeDecodedImage(archive.decoded());
	}
	public TransparencyPixmap scaled() {
		return visualizeDecodedImage(archive.scaled());
	}
	public VisualizationImage blocksPrimary() {
		return visualizeBlockMap(archive.blocks(), context.input);
	}
	public VisualizationImage blocksSecondary() {
		return visualizeSecondaryBlockMap(archive.blocks(), context.input);
	}
	public VisualizationImage histogram() {
		return visualizeHistogram(archive.histogram(), archive.blocks(), context.input);
	}
	public VisualizationImage smoothedHistogram() {
		return visualizeSmoothedHistogram(archive.smoothedHistogram(), archive.blocks(), context.input);
	}
	public VisualizationImage contrast() {
		return visualizeClippedContrast(archive.contrast(), archive.blocks(), context.input);
	}
	public VisualizationImage absoluteMask() {
		return visualizeAbsoluteContrastMask(archive.absoluteMask(), archive.blocks(), context.input);
	}
	public VisualizationImage relativeMask() {
		return visualizeRelativeContrastMask(archive.relativeMask(), archive.blocks(), context.input);
	}
	public VisualizationImage combinedMask() {
		return visualizeCombinedMask(archive.combinedMask(), archive.blocks(), context.input);
	}
	public VisualizationImage filteredMask() {
		return visualizeFilteredMask(archive.filteredMask(), archive.blocks(), context.input);
	}
	public TransparencyPixmap equalized() {
		return visualizeEqualizedImage(archive.equalized());
	}
	public TransparencyPixmap pixelwiseOrientation() {
		return visualizePixelwiseOrientation(archive.pixelwiseOrientation());
	}
	public VisualizationImage blockOrientation() {
		return visualizeBlockOrientation(archive.blockOrientation(), archive.blocks(), archive.filteredMask(), context.input);
	}
	public VisualizationImage smoothedOrientation() {
		return visualizeSmoothedOrientation(archive.smoothedOrientation(), archive.blocks(), archive.filteredMask(), context.input);
	}
	public TransparencyPixmap parallelSmoothing() {
		return visualizeParallelSmoothing(archive.parallelSmoothing());
	}
	public TransparencyPixmap orthogonalSmoothing() {
		return visualizeParallelSmoothing(archive.orthogonalSmoothing());
	}
	public VisualizationImage binarized() {
		return visualizeBinarizedImage(archive.binarized(), context.input);
	}
	public TransparencyPixmap filteredBinary() {
		return visualizeFilteredBinaryImage(archive.filteredBinary());
	}
	public TransparencyPixmap filteredBinaryDiff() {
		return visualizeFilteredBinaryImageDiff(archive.filteredBinary(), archive.binarized());
	}
	public VisualizationImage pixelMask() {
		return visualizePixelMask(archive.pixelMask(), context.input);
	}
	public VisualizationImage innerMask() {
		return visualizePixelMask(archive.innerMask(), context.input);
	}
	public VisualizationImage binarizedSkeleton(SkeletonType skeleton) {
		return visualizeBinarizedSkeleton(archive.binarizedSkeleton(skeleton), context.input);
	}
	public VisualizationImage binarizedSkeleton() {
		return visualizeBinarizedSkeleton(archive.binarizedSkeleton(), context.input);
	}
	public VisualizationImage thinned(SkeletonType skeleton) {
		return visualizeThinnedSkeleton(archive.thinned(skeleton), context.input);
	}
	public VisualizationImage thinned() {
		return visualizeThinnedSkeleton(archive.thinned(), context.input);
	}
	public VisualizationImage traced(SkeletonType skeleton) {
		return visualizeTracedSkeleton(archive.traced(skeleton), context.input);
	}
	public VisualizationImage traced() {
		return visualizeTracedSkeleton(archive.traced(), context.input);
	}
	public VisualizationImage removedDots(SkeletonType skeleton) {
		return visualizeRemovedDots(archive.removedDots(skeleton), context.input);
	}
	public VisualizationImage removedDots() {
		return visualizeRemovedDots(archive.removedDots(), context.input);
	}
	public VisualizationImage removedDotsDiff(SkeletonType skeleton) {
		return visualizeRemovedDotsDiff(archive.removedDots(skeleton), archive.traced(skeleton));
	}
	public VisualizationImage removedDotsDiff() {
		return visualizeRemovedDotsDiff(archive.removedDots(), archive.traced());
	}
	public VisualizationImage removedPores(SkeletonType skeleton) {
		return visualizeRemovedPores(archive.removedPores(skeleton), context.input);
	}
	public VisualizationImage removedPores() {
		return visualizeRemovedPores(archive.removedPores(), context.input);
	}
	public VisualizationImage removedPoresDiff(SkeletonType skeleton) {
		return visualizeRemovedPoresDiff(archive.removedPores(skeleton), archive.removedDots(skeleton));
	}
	public VisualizationImage removedPoresDiff() {
		return visualizeRemovedPoresDiff(archive.removedPores(), archive.removedDots());
	}
	public VisualizationImage removedGaps(SkeletonType skeleton) {
		return visualizeRemovedGaps(archive.removedGaps(skeleton), context.input);
	}
	public VisualizationImage removedGaps() {
		return visualizeRemovedGaps(archive.removedGaps(), context.input);
	}
	public VisualizationImage removedGapsDiff(SkeletonType skeleton) {
		return visualizeRemovedGapsDiff(archive.removedGaps(skeleton), archive.removedPores(skeleton));
	}
	public VisualizationImage removedGapsDiff() {
		return visualizeRemovedGapsDiff(archive.removedGaps(), archive.removedPores());
	}
	public VisualizationImage removedTails(SkeletonType skeleton) {
		return visualizeRemovedTails(archive.removedTails(skeleton), context.input);
	}
	public VisualizationImage removedTails() {
		return visualizeRemovedTails(archive.removedTails(), context.input);
	}
	public VisualizationImage removedTailsDiff(SkeletonType skeleton) {
		return visualizeRemovedTailsDiff(archive.removedTails(skeleton), archive.removedGaps(skeleton));
	}
	public VisualizationImage removedTailsDiff() {
		return visualizeRemovedTailsDiff(archive.removedTails(), archive.removedGaps());
	}
	public VisualizationImage removedFragments(SkeletonType skeleton) {
		return visualizeRemovedFragments(archive.removedFragments(skeleton), context.input);
	}
	public VisualizationImage removedFragments() {
		return visualizeRemovedFragments(archive.removedFragments(), context.input);
	}
	public VisualizationImage removedFragmentsDiff(SkeletonType skeleton) {
		return visualizeRemovedFragmentsDiff(archive.removedFragments(skeleton), archive.removedTails(skeleton));
	}
	public VisualizationImage removedFragmentsDiff() {
		return visualizeRemovedFragmentsDiff(archive.removedFragments(), archive.removedTails());
	}
	public VisualizationImage skeletonMinutiae() {
		return visualizeSkeletonMinutiae(archive.skeletonMinutiae(), context.input);
	}
	public VisualizationImage innerMinutiae() {
		return visualizeInnerMinutiae(archive.innerMinutiae(), context.input);
	}
	public VisualizationImage innerMinutiaeDiff() {
		return visualizeInnerMinutiaeDiff(archive.innerMinutiae(), archive.skeletonMinutiae(), context.input);
	}
	public VisualizationImage removedMinutiaClouds() {
		return visualizeRemovedMinutiaClouds(archive.removedMinutiaClouds(), context.input);
	}
	public VisualizationImage removedMinutiaCloudsDiff() {
		return visualizeRemovedMinutiaCloudsDiff(archive.removedMinutiaClouds(), archive.innerMinutiae(), context.input);
	}
	public VisualizationImage topMinutiae() {
		return visualizeTopMinutiae(archive.topMinutiae(), context.input);
	}
	public VisualizationImage topMinutiaeDiff() {
		return visualizeTopMinutiaeDiff(archive.topMinutiae(), archive.removedMinutiaClouds(), context.input);
	}
	public VisualizationImage shuffledMinutiae() {
		return visualizeShuffledMinutiae(archive.shuffledMinutiae(), context.input);
	}
	public VisualizationImage edgeTable() {
		return visualizeEdgeTable(archive.edgeTable(), output(), context.input);
	}
	public VisualizationImage template() {
		return visualizeTemplate(output(), context.input);
	}
}
