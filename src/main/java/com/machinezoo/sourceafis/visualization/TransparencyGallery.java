// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static com.machinezoo.sourceafis.visualization.TransparencyMarkers.*;
import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.transparency.types.*;

public class TransparencyGallery {
	private final TransparencyArchive archive;
	private final TransparencyContext context;
	public TransparencyGallery(TransparencyArchive archive, TransparencyContext context) {
		Objects.requireNonNull(archive);
		Objects.requireNonNull(context);
		this.archive = archive;
		this.context = context;
	}
	private <T> T expect(TransparencyKey<T> key) {
		return archive.deserialize(key).get();
	}
	public byte[] decoded() {
		return paintDecoded(expect(new DecodedImageKey())).jpeg();
	}
	public byte[] scaled() {
		return paintScaled(expect(new ScaledImageKey())).jpeg();
	}
	public byte[] blocksPrimary() {
		var blocks = expect(new BlocksKey());
		return new TransparencyImage(blocks)
			.padding(1)
			.image(context.image(TransparencyRole.EXTRACTED))
			.add(markBlocks(blocks))
			.bytes();
	}
	public byte[] blocksSecondary() {
		var blocks = expect(new BlocksKey());
		return new TransparencyImage(blocks)
			.padding(1)
			.image(context.image(TransparencyRole.EXTRACTED))
			.add(markSecondaryBlocks(blocks))
			.bytes();
	}
	private byte[] overlay(DomContent content) {
		return new TransparencyImage(expect(new BlocksKey()))
			.image(context.image(TransparencyRole.EXTRACTED))
			.add(content)
			.bytes();
	}
	public byte[] histogram() {
		return overlay(markHistogram(expect(new HistogramKey()), expect(new BlocksKey())));
	}
	public byte[] smoothedHistogram() {
		return overlay(markSmoothedHistogram(expect(new SmoothedHistogramKey()), expect(new BlocksKey())));
	}
	public byte[] contrast() {
		return overlay(markContrast(expect(new ContrastKey()), expect(new BlocksKey())));
	}
	private byte[] overlayPng(TransparencyPixmap pixmap) {
		return new TransparencyImage(pixmap.size())
			.image(context.image(TransparencyRole.EXTRACTED))
			.png(pixmap)
			.bytes();
	}
	public byte[] absoluteMask() {
		return overlayPng(overlayAbsoluteContrastMask(expect(new AbsoluteContrastMaskKey()), expect(new BlocksKey())));
	}
	public byte[] relativeMask() {
		return overlayPng(overlayRelativeContrastMask(expect(new RelativeContrastMaskKey()), expect(new BlocksKey())));
	}
	public byte[] combinedMask() {
		return overlayPng(overlayCombinedMask(expect(new CombinedMaskKey()), expect(new BlocksKey())));
	}
	public byte[] filteredMask() {
		return overlayPng(overlayFilteredMask(expect(new FilteredMaskKey()), expect(new BlocksKey())));
	}
	public byte[] equalized() {
		return paintEqualized(expect(new EqualizedImageKey())).jpeg();
	}
	public byte[] pixelwiseOrientation() {
		return paintPixelwiseOrientation(expect(new PixelwiseOrientationKey())).jpeg();
	}
	public byte[] blockOrientation() {
		return overlay(markBlockOrientation(expect(new BlockOrientationKey()), expect(new BlocksKey()), expect(new FilteredMaskKey())));
	}
	public byte[] smoothedOrientation() {
		return overlay(markSmoothedOrientation(expect(new SmoothedOrientationKey()), expect(new BlocksKey()), expect(new FilteredMaskKey())));
	}
	public byte[] parallel() {
		return paintParallel(expect(new ParallelSmoothingKey())).jpeg();
	}
	public byte[] orthogonal() {
		return paintOrthogonal(expect(new OrthogonalSmoothingKey())).jpeg();
	}
	public byte[] binarized() {
		return overlayPng(overlayBinarized(expect(new BinarizedImageKey())));
	}
	public byte[] filteredBinary() {
		return paintFilteredBinary(expect(new FilteredBinaryImageKey())).png();
	}
	public byte[] filteredBinaryDiff() {
		return paintFilteredBinaryDiff(expect(new FilteredBinaryImageKey()), expect(new BinarizedImageKey())).png();
	}
	public byte[] pixelMask() {
		return overlayPng(overlayPixelMask(expect(new PixelMaskKey())));
	}
	public byte[] innerMask() {
		return overlayPng(overlayInnerMask(expect(new InnerMaskKey())));
	}
	public byte[] binarizedSkeleton(SkeletonType skeleton) {
		return overlayPng(overlayBinarizedSkeleton(expect(new BinarizedSkeletonKey(skeleton))));
	}
	public byte[] thinned(SkeletonType skeleton) {
		return overlayPng(overlayThinned(expect(new ThinnedSkeletonKey(skeleton))));
	}
	public byte[] traced(SkeletonType skeleton) {
		return overlay(markTraced(expect(new TracedSkeletonKey(skeleton))));
	}
	public byte[] dots(SkeletonType skeleton) {
		return overlay(markDots(expect(new RemovedDotsKey(skeleton))));
	}
	private byte[] solo(DomContent content) {
		return new TransparencyImage(expect(new BlocksKey()))
			.add(content)
			.bytes();
	}
	public byte[] dotsDiff(SkeletonType skeleton) {
		return solo(paintDotsDiff(expect(new RemovedDotsKey(skeleton)), expect(new TracedSkeletonKey(skeleton))));
	}
	public byte[] pores(SkeletonType skeleton) {
		return overlay(markPores(expect(new RemovedPoresKey(skeleton))));
	}
	public byte[] poresDiff(SkeletonType skeleton) {
		return solo(paintPoresDiff(expect(new RemovedPoresKey(skeleton)), expect(new RemovedDotsKey(skeleton))));
	}
	public byte[] gaps(SkeletonType skeleton) {
		return overlay(markGaps(expect(new RemovedGapsKey(skeleton))));
	}
	public byte[] gapsDiff(SkeletonType skeleton) {
		return solo(paintGapsDiff(expect(new RemovedGapsKey(skeleton)), expect(new RemovedPoresKey(skeleton))));
	}
	public byte[] tails(SkeletonType skeleton) {
		return overlay(markTails(expect(new RemovedTailsKey(skeleton))));
	}
	public byte[] tailsDiff(SkeletonType skeleton) {
		return solo(paintTailsDiff(expect(new RemovedTailsKey(skeleton)), expect(new RemovedGapsKey(skeleton))));
	}
	public byte[] fragments(SkeletonType skeleton) {
		return overlay(markFragments(expect(new RemovedFragmentsKey(skeleton))));
	}
	public byte[] fragmentsDiff(SkeletonType skeleton) {
		return solo(paintFragmentsDiff(expect(new RemovedFragmentsKey(skeleton)), expect(new RemovedTailsKey(skeleton))));
	}
	public byte[] skeletonMinutiae() {
		return overlay(markSkeletonMinutiae(expect(new SkeletonMinutiaeKey())));
	}
	public byte[] innerMinutiae() {
		return overlay(markInnerMinutiae(expect(new InnerMinutiaeKey())));
	}
	public byte[] innerMinutiaeDiff() {
		return overlay(markInnerMinutiaeDiff(expect(new InnerMinutiaeKey()), expect(new SkeletonMinutiaeKey())));
	}
	public byte[] clouds() {
		return overlay(markClouds(expect(new RemovedMinutiaCloudsKey())));
	}
	public byte[] cloudsDiff() {
		return overlay(markCloudsDiff(expect(new RemovedMinutiaCloudsKey()), expect(new InnerMinutiaeKey())));
	}
	public byte[] topMinutiae() {
		return overlay(markTopMinutiae(expect(new TopMinutiaeKey())));
	}
	public byte[] topMinutiaeDiff() {
		return overlay(markTopMinutiaeDiff(expect(new TopMinutiaeKey()), expect(new RemovedMinutiaCloudsKey())));
	}
	public byte[] shuffled() {
		return overlay(markShuffled(expect(new ShuffledMinutiaeKey())));
	}
	public byte[] edges() {
		return overlay(markEdges(expect(new EdgeTableKey()), context.template(TransparencyRole.EXTRACTED)));
	}
	public byte[] hash() {
		var template = context.template(TransparencyRole.PROBE);
		return new TransparencyImage(template.size())
			.image(context.image(TransparencyRole.PROBE))
			.add(markHash(expect(new EdgeHashKey()), template))
			.bytes();
	}
	public byte[] roots() {
		var probe = context.template(TransparencyRole.PROBE);
		var candidate = context.template(TransparencyRole.CANDIDATE);
		TransparencyImage left = new TransparencyImage(probe.size())
			.image(context.image(TransparencyRole.PROBE));
		TransparencyImage right = new TransparencyImage(candidate.size())
			.image(context.image(TransparencyRole.CANDIDATE));
		return new TransparencySplit(left, right)
			.add(markRoots(expect(new RootsKey()), probe, candidate))
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
		return new TransparencyImage(template.size())
			.image(image)
			.add(markPairing(archive.deserialize(new PairingKey(), offset).get(), side, template))
			.bytes();
	}
	public byte[] pairing(MatchSide side) {
		return pairing(archive.deserialize(new BestMatchKey()).orElse(0), side);
	}
	public byte[] pairing(int offset) {
		var pairing = archive.deserialize(new PairingKey(), offset).get();
		var probe = context.template(TransparencyRole.PROBE);
		TransparencyImage left = new TransparencyImage(probe.size())
			.image(context.image(TransparencyRole.PROBE))
			.add(markPairing(pairing, MatchSide.PROBE, probe));
		var candidate = context.template(TransparencyRole.CANDIDATE);
		TransparencyImage right = new TransparencyImage(candidate.size())
			.image(context.image(TransparencyRole.CANDIDATE))
			.add(markPairing(pairing, MatchSide.CANDIDATE, candidate));
		return new TransparencySplit(left, right).bytes();
	}
	public byte[] pairing() {
		return pairing(archive.deserialize(new BestMatchKey()).orElse(0));
	}
}
