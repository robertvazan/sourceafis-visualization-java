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
		return paintDecoded(archive.decoded()).jpeg();
	}
	public byte[] scaled() {
		return paintScaled(archive.scaled()).jpeg();
	}
	public byte[] blocksPrimary() {
		BlockMap blocks = archive.blocks();
		return new TransparencyImage(blocks)
			.padding(1)
			.image(context.image(TransparencyRole.EXTRACTED))
			.add(markBlocks(blocks))
			.bytes();
	}
	public byte[] blocksSecondary() {
		BlockMap blocks = archive.blocks();
		return new TransparencyImage(blocks)
			.padding(1)
			.image(context.image(TransparencyRole.EXTRACTED))
			.add(markSecondaryBlocks(blocks))
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
		return overlay(markContrast(archive.contrast(), archive.blocks()));
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
		return paintEqualized(archive.equalized()).jpeg();
	}
	public byte[] pixelwiseOrientation() {
		return paintPixelwiseOrientation(archive.pixelwiseOrientation()).jpeg();
	}
	public byte[] blockOrientation() {
		return overlay(markBlockOrientation(archive.blockOrientation(), archive.blocks(), archive.filteredMask()));
	}
	public byte[] smoothedOrientation() {
		return overlay(markSmoothedOrientation(archive.smoothedOrientation(), archive.blocks(), archive.filteredMask()));
	}
	public byte[] parallel() {
		return paintParallel(archive.parallel()).jpeg();
	}
	public byte[] orthogonal() {
		return paintOrthogonal(archive.orthogonal()).jpeg();
	}
	public byte[] binarized() {
		return overlayPng(overlayBinarized(archive.binarized()));
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
		return overlayPng(overlayThinned(archive.thinned(skeleton)));
	}
	public byte[] thinned() {
		return overlayPng(overlayThinned(archive.thinned()));
	}
	public byte[] traced(SkeletonType skeleton) {
		return overlay(markTraced(archive.traced(skeleton)));
	}
	public byte[] traced() {
		return overlay(markTraced(archive.traced()));
	}
	public byte[] dots(SkeletonType skeleton) {
		return overlay(markDots(archive.dots(skeleton)));
	}
	public byte[] dots() {
		return overlay(markDots(archive.dots()));
	}
	private byte[] solo(DomContent content) {
		return new TransparencyImage(archive.blocks())
			.add(content)
			.bytes();
	}
	public byte[] dotsDiff(SkeletonType skeleton) {
		return solo(paintDotsDiff(archive.dots(skeleton), archive.traced(skeleton)));
	}
	public byte[] dotsDiff() {
		return solo(paintDotsDiff(archive.dots(), archive.traced()));
	}
	public byte[] pores(SkeletonType skeleton) {
		return overlay(markPores(archive.pores(skeleton)));
	}
	public byte[] pores() {
		return overlay(markPores(archive.pores()));
	}
	public byte[] poresDiff(SkeletonType skeleton) {
		return solo(paintPoresDiff(archive.pores(skeleton), archive.dots(skeleton)));
	}
	public byte[] poresDiff() {
		return solo(paintPoresDiff(archive.pores(), archive.dots()));
	}
	public byte[] gaps(SkeletonType skeleton) {
		return overlay(markGaps(archive.gaps(skeleton)));
	}
	public byte[] gaps() {
		return overlay(markGaps(archive.gaps()));
	}
	public byte[] gapsDiff(SkeletonType skeleton) {
		return solo(paintGapsDiff(archive.gaps(skeleton), archive.pores(skeleton)));
	}
	public byte[] gapsDiff() {
		return solo(paintGapsDiff(archive.gaps(), archive.pores()));
	}
	public byte[] tails(SkeletonType skeleton) {
		return overlay(markTails(archive.tails(skeleton)));
	}
	public byte[] tails() {
		return overlay(markTails(archive.tails()));
	}
	public byte[] tailsDiff(SkeletonType skeleton) {
		return solo(paintTailsDiff(archive.tails(skeleton), archive.gaps(skeleton)));
	}
	public byte[] tailsDiff() {
		return solo(paintTailsDiff(archive.tails(), archive.gaps()));
	}
	public byte[] fragments(SkeletonType skeleton) {
		return overlay(markFragments(archive.fragments(skeleton)));
	}
	public byte[] fragments() {
		return overlay(markFragments(archive.fragments()));
	}
	public byte[] fragmentsDiff(SkeletonType skeleton) {
		return solo(paintFragmentsDiff(archive.fragments(skeleton), archive.tails(skeleton)));
	}
	public byte[] fragmentsDiff() {
		return solo(paintFragmentsDiff(archive.fragments(), archive.tails()));
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
	public byte[] clouds() {
		return overlay(markClouds(archive.clouds()));
	}
	public byte[] cloudsDiff() {
		return overlay(markCloudsDiff(archive.clouds(), archive.innerMinutiae()));
	}
	public byte[] topMinutiae() {
		return overlay(markTopMinutiae(archive.topMinutiae()));
	}
	public byte[] topMinutiaeDiff() {
		return overlay(markTopMinutiaeDiff(archive.topMinutiae(), archive.clouds()));
	}
	public byte[] shuffled() {
		return overlay(markShuffled(archive.shuffled()));
	}
	public byte[] edges() {
		return overlay(markEdges(archive.edges(), context.template(TransparencyRole.EXTRACTED)));
	}
	public byte[] hash() {
		Template template = context.template(TransparencyRole.PROBE);
		return new TransparencyImage(template.size)
			.image(context.image(TransparencyRole.PROBE))
			.add(markHash(archive.hash(), template))
			.bytes();
	}
	public byte[] roots() {
		Template probe = context.template(TransparencyRole.PROBE);
		Template candidate = context.template(TransparencyRole.CANDIDATE);
		TransparencyImage left = new TransparencyImage(probe.size)
			.image(context.image(TransparencyRole.PROBE));
		TransparencyImage right = new TransparencyImage(candidate.size)
			.image(context.image(TransparencyRole.CANDIDATE));
		return new TransparencySplit(left, right)
			.add(markRoots(archive.roots(), probe, candidate))
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
		return pairing(archive.best().orElse(0), side);
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
		return pairing(archive.best().orElse(0));
	}
}
