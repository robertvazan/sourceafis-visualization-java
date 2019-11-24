// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static com.machinezoo.sourceafis.visualization.TransparencyImages.*;
import java.util.*;
import java.util.function.*;
import com.machinezoo.sourceafis.transparency.*;

public class ExtractorImages {
	private final TransparencyArchive archive;
	public ExtractorImages(TransparencyArchive archive) {
		Objects.requireNonNull(archive);
		this.archive = archive;
	}
	private Supplier<byte[]> input = () -> null;
	public ExtractorImages input(Supplier<byte[]> supplier) {
		Objects.requireNonNull(supplier);
		input = supplier;
		return this;
	}
	public ExtractorImages input(byte[] image) {
		return input(() -> image);
	}
	byte[] input() {
		return input.get();
	}
	private Supplier<byte[]> output = () -> null;
	public ExtractorImages output(Supplier<byte[]> supplier) {
		Objects.requireNonNull(supplier);
		output = supplier;
		return this;
	}
	public ExtractorImages output(byte[] template) {
		return output(() -> template);
	}
	Template output() {
		return Optional.ofNullable(output.get()).map(Template::parse).orElse(null);
	}
	public WritableImage decoded() {
		return visualizeDecodedImage(archive.decoded());
	}
	public WritableImage scaled() {
		return visualizeDecodedImage(archive.scaled());
	}
	public VisualizationImage blocksPrimary() {
		return visualizeBlockMap(archive.blocks(), input());
	}
	public VisualizationImage blocksSecondary() {
		return visualizeSecondaryBlockMap(archive.blocks(), input());
	}
	public VisualizationImage histogram() {
		return visualizeHistogram(archive.histogram(), archive.blocks(), input());
	}
	public VisualizationImage smoothedHistogram() {
		return visualizeSmoothedHistogram(archive.smoothedHistogram(), archive.blocks(), input());
	}
	public VisualizationImage contrast() {
		return visualizeClippedContrast(archive.contrast(), archive.blocks(), input());
	}
	public VisualizationImage absoluteMask() {
		return visualizeAbsoluteContrastMask(archive.absoluteMask(), archive.blocks(), input());
	}
	public VisualizationImage relativeMask() {
		return visualizeRelativeContrastMask(archive.relativeMask(), archive.blocks(), input());
	}
	public VisualizationImage combinedMask() {
		return visualizeCombinedMask(archive.combinedMask(), archive.blocks(), input());
	}
	public VisualizationImage filteredMask() {
		return visualizeFilteredMask(archive.filteredMask(), archive.blocks(), input());
	}
	public WritableImage equalized() {
		return visualizeEqualizedImage(archive.equalized());
	}
	public WritableImage pixelwiseOrientation() {
		return visualizePixelwiseOrientation(archive.pixelwiseOrientation());
	}
	public VisualizationImage blockOrientation() {
		return visualizeBlockOrientation(archive.blockOrientation(), archive.blocks(), archive.filteredMask(), input());
	}
	public VisualizationImage smoothedOrientation() {
		return visualizeSmoothedOrientation(archive.smoothedOrientation(), archive.blocks(), archive.filteredMask(), input());
	}
	public WritableImage parallelSmoothing() {
		return visualizeParallelSmoothing(archive.parallelSmoothing());
	}
	public WritableImage orthogonalSmoothing() {
		return visualizeParallelSmoothing(archive.orthogonalSmoothing());
	}
	public VisualizationImage binarized() {
		return visualizeBinarizedImage(archive.binarized(), input());
	}
	public WritableImage filteredBinary() {
		return visualizeFilteredBinaryImage(archive.filteredBinary());
	}
	public WritableImage filteredBinaryDiff() {
		return visualizeFilteredBinaryImageDiff(archive.filteredBinary(), archive.binarized());
	}
	public VisualizationImage pixelMask() {
		return visualizePixelMask(archive.pixelMask(), input());
	}
	public VisualizationImage innerMask() {
		return visualizePixelMask(archive.innerMask(), input());
	}
	public VisualizationImage binarizedSkeleton(SkeletonType skeleton) {
		return visualizeBinarizedSkeleton(archive.binarizedSkeleton(skeleton), input());
	}
	public VisualizationImage binarizedSkeleton() {
		return visualizeBinarizedSkeleton(archive.binarizedSkeleton(), input());
	}
	public VisualizationImage thinned(SkeletonType skeleton) {
		return visualizeThinnedSkeleton(archive.thinned(skeleton), input());
	}
	public VisualizationImage thinned() {
		return visualizeThinnedSkeleton(archive.thinned(), input());
	}
	public VisualizationImage traced(SkeletonType skeleton) {
		return visualizeTracedSkeleton(archive.traced(skeleton), input());
	}
	public VisualizationImage traced() {
		return visualizeTracedSkeleton(archive.traced(), input());
	}
	public VisualizationImage removedDots(SkeletonType skeleton) {
		return visualizeRemovedDots(archive.removedDots(skeleton), input());
	}
	public VisualizationImage removedDots() {
		return visualizeRemovedDots(archive.removedDots(), input());
	}
	public VisualizationImage removedDotsDiff(SkeletonType skeleton) {
		return visualizeRemovedDotsDiff(archive.removedDots(skeleton), archive.traced(skeleton));
	}
	public VisualizationImage removedDotsDiff() {
		return visualizeRemovedDotsDiff(archive.removedDots(), archive.traced());
	}
	public VisualizationImage removedPores(SkeletonType skeleton) {
		return visualizeRemovedPores(archive.removedPores(skeleton), input());
	}
	public VisualizationImage removedPores() {
		return visualizeRemovedPores(archive.removedPores(), input());
	}
	public VisualizationImage removedPoresDiff(SkeletonType skeleton) {
		return visualizeRemovedPoresDiff(archive.removedPores(skeleton), archive.removedDots(skeleton));
	}
	public VisualizationImage removedPoresDiff() {
		return visualizeRemovedPoresDiff(archive.removedPores(), archive.removedDots());
	}
	public VisualizationImage removedGaps(SkeletonType skeleton) {
		return visualizeRemovedGaps(archive.removedGaps(skeleton), input());
	}
	public VisualizationImage removedGaps() {
		return visualizeRemovedGaps(archive.removedGaps(), input());
	}
	public VisualizationImage removedGapsDiff(SkeletonType skeleton) {
		return visualizeRemovedGapsDiff(archive.removedGaps(skeleton), archive.removedPores(skeleton));
	}
	public VisualizationImage removedGapsDiff() {
		return visualizeRemovedGapsDiff(archive.removedGaps(), archive.removedPores());
	}
	public VisualizationImage removedTails(SkeletonType skeleton) {
		return visualizeRemovedTails(archive.removedTails(skeleton), input());
	}
	public VisualizationImage removedTails() {
		return visualizeRemovedTails(archive.removedTails(), input());
	}
	public VisualizationImage removedTailsDiff(SkeletonType skeleton) {
		return visualizeRemovedTailsDiff(archive.removedTails(skeleton), archive.removedGaps(skeleton));
	}
	public VisualizationImage removedTailsDiff() {
		return visualizeRemovedTailsDiff(archive.removedTails(), archive.removedGaps());
	}
	public VisualizationImage removedFragments(SkeletonType skeleton) {
		return visualizeRemovedFragments(archive.removedFragments(skeleton), input());
	}
	public VisualizationImage removedFragments() {
		return visualizeRemovedFragments(archive.removedFragments(), input());
	}
	public VisualizationImage removedFragmentsDiff(SkeletonType skeleton) {
		return visualizeRemovedFragmentsDiff(archive.removedFragments(skeleton), archive.removedTails(skeleton));
	}
	public VisualizationImage removedFragmentsDiff() {
		return visualizeRemovedFragmentsDiff(archive.removedFragments(), archive.removedTails());
	}
	public VisualizationImage skeletonMinutiae() {
		return visualizeSkeletonMinutiae(archive.skeletonMinutiae(), input());
	}
	public VisualizationImage innerMinutiae() {
		return visualizeInnerMinutiae(archive.innerMinutiae(), input());
	}
	public VisualizationImage innerMinutiaeDiff() {
		return visualizeInnerMinutiaeDiff(archive.innerMinutiae(), archive.skeletonMinutiae(), input());
	}
	public VisualizationImage removedMinutiaClouds() {
		return visualizeRemovedMinutiaClouds(archive.removedMinutiaClouds(), input());
	}
	public VisualizationImage removedMinutiaCloudsDiff() {
		return visualizeRemovedMinutiaCloudsDiff(archive.removedMinutiaClouds(), archive.innerMinutiae(), input());
	}
	public VisualizationImage topMinutiae() {
		return visualizeTopMinutiae(archive.topMinutiae(), input());
	}
	public VisualizationImage topMinutiaeDiff() {
		return visualizeTopMinutiaeDiff(archive.topMinutiae(), archive.removedMinutiaClouds(), input());
	}
	public VisualizationImage shuffledMinutiae() {
		return visualizeShuffledMinutiae(archive.shuffledMinutiae(), input());
	}
	public VisualizationImage edgeTable() {
		return visualizeEdgeTable(archive.edgeTable(), output(), input());
	}
	public VisualizationImage template() {
		return visualizeTemplate(output(), input());
	}
}
