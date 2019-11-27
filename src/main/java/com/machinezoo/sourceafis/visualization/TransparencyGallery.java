// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static com.machinezoo.sourceafis.visualization.TransparencyMarkers.*;
import java.util.*;
import com.machinezoo.pushmode.dom.*;
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
	public byte[] decoded() {
		return paintDecoded(archive.decoded()).png();
	}
	public byte[] scaled() {
		return paintScaled(archive.scaled()).png();
	}
	public byte[] blocksPrimary() {
		BlockMap blocks = archive.blocks();
		return new TransparencyImage(blocks)
			.padding(1)
			.image(context.image(TransparencyRole.EXTRACTED))
			.add(markBlockMap(blocks))
			.bytes();
	}
	public byte[] blocksSecondary() {
		BlockMap blocks = archive.blocks();
		return new TransparencyImage(blocks)
			.padding(1)
			.image(context.image(TransparencyRole.EXTRACTED))
			.add(markSecondaryBlockMap(blocks))
			.bytes();
	}
	private byte[] overlay(DomContent content) {
		return new TransparencyImage(archive.blocks())
			.image(context.image(TransparencyRole.EXTRACTED))
			.add(content)
			.bytes();
	}
	public byte[] histogram() {
		return overlay(markHistogram(archive.histogram(), archive.blocks()));
	}
	public byte[] smoothedHistogram() {
		return overlay(markSmoothedHistogram(archive.smoothedHistogram(), archive.blocks()));
	}
	public byte[] contrast() {
		return overlay(markClippedContrast(archive.contrast(), archive.blocks()));
	}
	private byte[] overlayPng(TransparencyPixmap pixmap) {
		return new TransparencyImage(pixmap.size())
			.image(context.image(TransparencyRole.EXTRACTED))
			.png(pixmap)
			.bytes();
	}
	public byte[] absoluteMask() {
		return overlayPng(overlayAbsoluteContrastMask(archive.absoluteMask(), archive.blocks()));
	}
	public byte[] relativeMask() {
		return overlayPng(overlayRelativeContrastMask(archive.relativeMask(), archive.blocks()));
	}
	public byte[] combinedMask() {
		return overlayPng(overlayCombinedMask(archive.combinedMask(), archive.blocks()));
	}
	public byte[] filteredMask() {
		return overlayPng(overlayFilteredMask(archive.filteredMask(), archive.blocks()));
	}
	public byte[] equalized() {
		return paintEqualizedImage(archive.equalized()).png();
	}
	public byte[] pixelwiseOrientation() {
		return paintPixelwiseOrientation(archive.pixelwiseOrientation()).png();
	}
	public byte[] blockOrientation() {
		return overlay(markBlockOrientation(archive.blockOrientation(), archive.blocks(), archive.filteredMask()));
	}
	public byte[] smoothedOrientation() {
		return overlay(markSmoothedOrientation(archive.smoothedOrientation(), archive.blocks(), archive.filteredMask()));
	}
	public byte[] parallelSmoothing() {
		return paintParallelSmoothing(archive.parallelSmoothing()).png();
	}
	public byte[] orthogonalSmoothing() {
		return paintOrthogonalSmoothing(archive.orthogonalSmoothing()).png();
	}
	public byte[] binarized() {
		return overlayPng(overlayBinarizedImage(archive.binarized()));
	}
	public byte[] filteredBinary() {
		return paintFilteredBinary(archive.filteredBinary()).png();
	}
	public byte[] filteredBinaryDiff() {
		return paintFilteredBinaryDiff(archive.filteredBinary(), archive.binarized()).png();
	}
	public byte[] pixelMask() {
		return overlayPng(overlayPixelMask(archive.pixelMask()));
	}
	public byte[] innerMask() {
		return overlayPng(overlayInnerMask(archive.innerMask()));
	}
	public byte[] binarizedSkeleton(SkeletonType skeleton) {
		return overlayPng(overlayBinarizedSkeleton(archive.binarizedSkeleton(skeleton)));
	}
	public byte[] binarizedSkeleton() {
		return overlayPng(overlayBinarizedSkeleton(archive.binarizedSkeleton()));
	}
	public byte[] thinned(SkeletonType skeleton) {
		return overlayPng(overlayThinnedSkeleton(archive.thinned(skeleton)));
	}
	public byte[] thinned() {
		return overlayPng(overlayThinnedSkeleton(archive.thinned()));
	}
	public byte[] traced(SkeletonType skeleton) {
		return overlay(markTracedSkeleton(archive.traced(skeleton)));
	}
	public byte[] traced() {
		return overlay(markTracedSkeleton(archive.traced()));
	}
	public byte[] removedDots(SkeletonType skeleton) {
		return overlay(markRemovedDots(archive.removedDots(skeleton)));
	}
	public byte[] removedDots() {
		return overlay(markRemovedDots(archive.removedDots()));
	}
	private byte[] solo(DomContent content) {
		return new TransparencyImage(archive.blocks())
			.add(content)
			.bytes();
	}
	public byte[] removedDotsDiff(SkeletonType skeleton) {
		return solo(paintRemovedDotsDiff(archive.removedDots(skeleton), archive.traced(skeleton)));
	}
	public byte[] removedDotsDiff() {
		return solo(paintRemovedDotsDiff(archive.removedDots(), archive.traced()));
	}
	public byte[] removedPores(SkeletonType skeleton) {
		return overlay(markRemovedPores(archive.removedPores(skeleton)));
	}
	public byte[] removedPores() {
		return overlay(markRemovedPores(archive.removedPores()));
	}
	public byte[] removedPoresDiff(SkeletonType skeleton) {
		return solo(paintRemovedPoresDiff(archive.removedPores(skeleton), archive.removedDots(skeleton)));
	}
	public byte[] removedPoresDiff() {
		return solo(paintRemovedPoresDiff(archive.removedPores(), archive.removedDots()));
	}
	public byte[] removedGaps(SkeletonType skeleton) {
		return overlay(markRemovedGaps(archive.removedGaps(skeleton)));
	}
	public byte[] removedGaps() {
		return overlay(markRemovedGaps(archive.removedGaps()));
	}
	public byte[] removedGapsDiff(SkeletonType skeleton) {
		return solo(paintRemovedGapsDiff(archive.removedGaps(skeleton), archive.removedPores(skeleton)));
	}
	public byte[] removedGapsDiff() {
		return solo(paintRemovedGapsDiff(archive.removedGaps(), archive.removedPores()));
	}
	public byte[] removedTails(SkeletonType skeleton) {
		return overlay(markRemovedTails(archive.removedTails(skeleton)));
	}
	public byte[] removedTails() {
		return overlay(markRemovedTails(archive.removedTails()));
	}
	public byte[] removedTailsDiff(SkeletonType skeleton) {
		return solo(paintRemovedTailsDiff(archive.removedTails(skeleton), archive.removedGaps(skeleton)));
	}
	public byte[] removedTailsDiff() {
		return solo(paintRemovedTailsDiff(archive.removedTails(), archive.removedGaps()));
	}
	public byte[] removedFragments(SkeletonType skeleton) {
		return overlay(markRemovedFragments(archive.removedFragments(skeleton)));
	}
	public byte[] removedFragments() {
		return overlay(markRemovedFragments(archive.removedFragments()));
	}
	public byte[] removedFragmentsDiff(SkeletonType skeleton) {
		return solo(paintRemovedFragmentsDiff(archive.removedFragments(skeleton), archive.removedTails(skeleton)));
	}
	public byte[] removedFragmentsDiff() {
		return solo(paintRemovedFragmentsDiff(archive.removedFragments(), archive.removedTails()));
	}
	public byte[] skeletonMinutiae() {
		return overlay(markSkeletonMinutiae(archive.skeletonMinutiae()));
	}
	public byte[] innerMinutiae() {
		return overlay(markInnerMinutiae(archive.innerMinutiae()));
	}
	public byte[] innerMinutiaeDiff() {
		return overlay(markInnerMinutiaeDiff(archive.innerMinutiae(), archive.skeletonMinutiae()));
	}
	public byte[] removedMinutiaClouds() {
		return overlay(markRemovedMinutiaClouds(archive.removedMinutiaClouds()));
	}
	public byte[] removedMinutiaCloudsDiff() {
		return overlay(markRemovedMinutiaCloudsDiff(archive.removedMinutiaClouds(), archive.innerMinutiae()));
	}
	public byte[] topMinutiae() {
		return overlay(markTopMinutiae(archive.topMinutiae()));
	}
	public byte[] topMinutiaeDiff() {
		return overlay(markTopMinutiaeDiff(archive.topMinutiae(), archive.removedMinutiaClouds()));
	}
	public byte[] shuffledMinutiae() {
		return overlay(markShuffledMinutiae(archive.shuffledMinutiae()));
	}
	public byte[] edgeTable() {
		return overlay(markEdgeTable(archive.edgeTable(), context.template(TransparencyRole.EXTRACTED)));
	}
	public byte[] edgeHash() {
		Template template = context.template(TransparencyRole.PROBE);
		return new TransparencyImage(template.size)
			.image(context.image(TransparencyRole.PROBE))
			.add(markEdgeHash(archive.edgeHash(), template))
			.bytes();
	}
	public byte[] rootPairs() {
		Template probe = context.template(TransparencyRole.PROBE);
		Template candidate = context.template(TransparencyRole.CANDIDATE);
		TransparencyImage left = new TransparencyImage(probe.size)
			.image(context.image(TransparencyRole.PROBE));
		TransparencyImage right = new TransparencyImage(candidate.size)
			.image(context.image(TransparencyRole.CANDIDATE));
		return new TransparencySplit(left, right)
			.add(markRootPairs(archive.rootPairs(), probe, candidate))
			.left(markMinutiaPositions(probe))
			.right(markMinutiaPositions(candidate))
			.bytes();
	}
	public byte[] pairing(int offset, MatchSide side) {
		Template template;
		byte[] image;
		switch (side) {
		case PROBE:
			template = context.template(TransparencyRole.PROBE);
			image = context.image(TransparencyRole.PROBE);
			break;
		case CANDIDATE:
			template = context.template(TransparencyRole.CANDIDATE);
			image = context.image(TransparencyRole.CANDIDATE);
			break;
		default:
			throw new IllegalStateException();
		}
		return new TransparencyImage(template.size)
			.image(image)
			.add(markPairing(archive.pairing(offset), side, template))
			.bytes();
	}
	public byte[] pairing(MatchSide side) {
		return pairing(archive.bestMatch().orElse(0), side);
	}
	public byte[] pairing(int offset) {
		MatchPairing pairing = archive.pairing(offset);
		Template probe = context.template(TransparencyRole.PROBE);
		TransparencyImage left = new TransparencyImage(probe.size)
			.image(context.image(TransparencyRole.PROBE))
			.add(markPairing(pairing, MatchSide.PROBE, probe));
		Template candidate = context.template(TransparencyRole.CANDIDATE);
		TransparencyImage right = new TransparencyImage(candidate.size)
			.image(context.image(TransparencyRole.CANDIDATE))
			.add(markPairing(pairing, MatchSide.CANDIDATE, candidate));
		return new TransparencySplit(left, right).bytes();
	}
	public byte[] pairing() {
		return pairing(archive.bestMatch().orElse(0));
	}
}
