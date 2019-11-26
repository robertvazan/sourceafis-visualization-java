// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static com.machinezoo.sourceafis.visualization.TransparencyImages.*;
import java.util.*;
import com.machinezoo.sourceafis.transparency.*;

public class TransparencyGallery {
	private final TransparencyArchive archive;
	private final TransparencyContext context;
	public TransparencyGallery(TransparencyArchive archive, TransparencyContext context) {
		Objects.requireNonNull(archive);
		Objects.requireNonNull(context);
		this.archive = archive;
		this.context = context;
	}
	public TransparencyPixmap decoded() {
		return visualizeDecodedImage(archive.decoded());
	}
	public TransparencyPixmap scaled() {
		return visualizeDecodedImage(archive.scaled());
	}
	public VisualizationImage blocksPrimary() {
		return visualizeBlockMap(archive.blocks(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage blocksSecondary() {
		return visualizeSecondaryBlockMap(archive.blocks(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage histogram() {
		return visualizeHistogram(archive.histogram(), archive.blocks(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage smoothedHistogram() {
		return visualizeSmoothedHistogram(archive.smoothedHistogram(), archive.blocks(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage contrast() {
		return visualizeClippedContrast(archive.contrast(), archive.blocks(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage absoluteMask() {
		return visualizeAbsoluteContrastMask(archive.absoluteMask(), archive.blocks(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage relativeMask() {
		return visualizeRelativeContrastMask(archive.relativeMask(), archive.blocks(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage combinedMask() {
		return visualizeCombinedMask(archive.combinedMask(), archive.blocks(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage filteredMask() {
		return visualizeFilteredMask(archive.filteredMask(), archive.blocks(), context.image(TransparencyRole.EXTRACTED));
	}
	public TransparencyPixmap equalized() {
		return visualizeEqualizedImage(archive.equalized());
	}
	public TransparencyPixmap pixelwiseOrientation() {
		return visualizePixelwiseOrientation(archive.pixelwiseOrientation());
	}
	public VisualizationImage blockOrientation() {
		return visualizeBlockOrientation(archive.blockOrientation(), archive.blocks(), archive.filteredMask(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage smoothedOrientation() {
		return visualizeSmoothedOrientation(archive.smoothedOrientation(), archive.blocks(), archive.filteredMask(), context.image(TransparencyRole.EXTRACTED));
	}
	public TransparencyPixmap parallelSmoothing() {
		return visualizeParallelSmoothing(archive.parallelSmoothing());
	}
	public TransparencyPixmap orthogonalSmoothing() {
		return visualizeParallelSmoothing(archive.orthogonalSmoothing());
	}
	public VisualizationImage binarized() {
		return visualizeBinarizedImage(archive.binarized(), context.image(TransparencyRole.EXTRACTED));
	}
	public TransparencyPixmap filteredBinary() {
		return visualizeFilteredBinaryImage(archive.filteredBinary());
	}
	public TransparencyPixmap filteredBinaryDiff() {
		return visualizeFilteredBinaryImageDiff(archive.filteredBinary(), archive.binarized());
	}
	public VisualizationImage pixelMask() {
		return visualizePixelMask(archive.pixelMask(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage innerMask() {
		return visualizePixelMask(archive.innerMask(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage binarizedSkeleton(SkeletonType skeleton) {
		return visualizeBinarizedSkeleton(archive.binarizedSkeleton(skeleton), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage binarizedSkeleton() {
		return visualizeBinarizedSkeleton(archive.binarizedSkeleton(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage thinned(SkeletonType skeleton) {
		return visualizeThinnedSkeleton(archive.thinned(skeleton), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage thinned() {
		return visualizeThinnedSkeleton(archive.thinned(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage traced(SkeletonType skeleton) {
		return visualizeTracedSkeleton(archive.traced(skeleton), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage traced() {
		return visualizeTracedSkeleton(archive.traced(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage removedDots(SkeletonType skeleton) {
		return visualizeRemovedDots(archive.removedDots(skeleton), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage removedDots() {
		return visualizeRemovedDots(archive.removedDots(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage removedDotsDiff(SkeletonType skeleton) {
		return visualizeRemovedDotsDiff(archive.removedDots(skeleton), archive.traced(skeleton));
	}
	public VisualizationImage removedDotsDiff() {
		return visualizeRemovedDotsDiff(archive.removedDots(), archive.traced());
	}
	public VisualizationImage removedPores(SkeletonType skeleton) {
		return visualizeRemovedPores(archive.removedPores(skeleton), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage removedPores() {
		return visualizeRemovedPores(archive.removedPores(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage removedPoresDiff(SkeletonType skeleton) {
		return visualizeRemovedPoresDiff(archive.removedPores(skeleton), archive.removedDots(skeleton));
	}
	public VisualizationImage removedPoresDiff() {
		return visualizeRemovedPoresDiff(archive.removedPores(), archive.removedDots());
	}
	public VisualizationImage removedGaps(SkeletonType skeleton) {
		return visualizeRemovedGaps(archive.removedGaps(skeleton), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage removedGaps() {
		return visualizeRemovedGaps(archive.removedGaps(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage removedGapsDiff(SkeletonType skeleton) {
		return visualizeRemovedGapsDiff(archive.removedGaps(skeleton), archive.removedPores(skeleton));
	}
	public VisualizationImage removedGapsDiff() {
		return visualizeRemovedGapsDiff(archive.removedGaps(), archive.removedPores());
	}
	public VisualizationImage removedTails(SkeletonType skeleton) {
		return visualizeRemovedTails(archive.removedTails(skeleton), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage removedTails() {
		return visualizeRemovedTails(archive.removedTails(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage removedTailsDiff(SkeletonType skeleton) {
		return visualizeRemovedTailsDiff(archive.removedTails(skeleton), archive.removedGaps(skeleton));
	}
	public VisualizationImage removedTailsDiff() {
		return visualizeRemovedTailsDiff(archive.removedTails(), archive.removedGaps());
	}
	public VisualizationImage removedFragments(SkeletonType skeleton) {
		return visualizeRemovedFragments(archive.removedFragments(skeleton), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage removedFragments() {
		return visualizeRemovedFragments(archive.removedFragments(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage removedFragmentsDiff(SkeletonType skeleton) {
		return visualizeRemovedFragmentsDiff(archive.removedFragments(skeleton), archive.removedTails(skeleton));
	}
	public VisualizationImage removedFragmentsDiff() {
		return visualizeRemovedFragmentsDiff(archive.removedFragments(), archive.removedTails());
	}
	public VisualizationImage skeletonMinutiae() {
		return visualizeSkeletonMinutiae(archive.skeletonMinutiae(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage innerMinutiae() {
		return visualizeInnerMinutiae(archive.innerMinutiae(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage innerMinutiaeDiff() {
		return visualizeInnerMinutiaeDiff(archive.innerMinutiae(), archive.skeletonMinutiae(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage removedMinutiaClouds() {
		return visualizeRemovedMinutiaClouds(archive.removedMinutiaClouds(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage removedMinutiaCloudsDiff() {
		return visualizeRemovedMinutiaCloudsDiff(archive.removedMinutiaClouds(), archive.innerMinutiae(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage topMinutiae() {
		return visualizeTopMinutiae(archive.topMinutiae(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage topMinutiaeDiff() {
		return visualizeTopMinutiaeDiff(archive.topMinutiae(), archive.removedMinutiaClouds(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage shuffledMinutiae() {
		return visualizeShuffledMinutiae(archive.shuffledMinutiae(), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage edgeTable() {
		return visualizeEdgeTable(archive.edgeTable(), context.template(TransparencyRole.EXTRACTED), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage template() {
		return visualizeTemplate(context.template(TransparencyRole.EXTRACTED), context.image(TransparencyRole.EXTRACTED));
	}
	public VisualizationImage edgeHash() {
		return visualizeEdgeHash(archive.edgeHash(), context.template(TransparencyRole.PROBE), context.image(TransparencyRole.PROBE));
	}
	public VisualizationImage rootPairs() {
		return visualizeRootPairs(
			archive.rootPairs(),
			context.template(TransparencyRole.PROBE),
			context.template(TransparencyRole.CANDIDATE),
			context.image(TransparencyRole.PROBE),
			context.image(TransparencyRole.CANDIDATE));
	}
	public VisualizationImage pairing(int offset, MatchSide side) {
		switch (side) {
		case PROBE:
			return visualizePairing(archive.pairing(offset), side, context.template(TransparencyRole.PROBE), context.image(TransparencyRole.PROBE));
		case CANDIDATE:
			return visualizePairing(archive.pairing(offset), side, context.template(TransparencyRole.CANDIDATE), context.image(TransparencyRole.CANDIDATE));
		default:
			throw new IllegalStateException();
		}
	}
	public VisualizationImage pairing(MatchSide side) {
		return pairing(archive.bestMatch().orElse(0), side);
	}
	public VisualizationImage pairing(int offset) {
		return visualizePairing(
			archive.pairing(offset),
			context.template(TransparencyRole.PROBE),
			context.template(TransparencyRole.CANDIDATE),
			context.image(TransparencyRole.PROBE),
			context.image(TransparencyRole.CANDIDATE));
	}
	public VisualizationImage pairing() {
		return pairing(archive.bestMatch().orElse(0));
	}
}
