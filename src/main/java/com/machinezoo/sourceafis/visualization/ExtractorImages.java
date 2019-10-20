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
	private Supplier<Template> output = () -> null;
	public ExtractorImages output(Supplier<Template> supplier) {
		Objects.requireNonNull(supplier);
		output = supplier;
		return this;
	}
	public ExtractorImages output(Template template) {
		return output(() -> template);
	}
	Template output() {
		return output.get();
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
	public SkeletonImages ridges() {
		return new SkeletonImages(this, archive.ridges());
	}
	public SkeletonImages valleys() {
		return new SkeletonImages(this, archive.valleys());
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
		return visualizeTemplate(output());
	}
}
