// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static com.machinezoo.sourceafis.visualization.LegacyTransparencyMarkers.*;
import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.transparency.types.*;

public class LegacyTransparencyGallery {
	private final TransparencyArchive archive;
	public LegacyTransparencyGallery(TransparencyArchive archive) {
		Objects.requireNonNull(archive);
		this.archive = archive;
	}
	private <T> T expect(TransparencyKey<T> key) {
		return archive.deserialize(key).get();
	}
	private <T> T nullable(TransparencyKey<T> key) {
		return archive.deserialize(key).orElse(null);
	}
	private byte[] overlay(DomContent content) {
		return new LegacyTransparencyImage(expect(new BlocksKey()))
			.image(nullable(new InputImageKey()))
			.add(content)
			.bytes();
	}
	private byte[] overlayPng(LegacyTransparencyPixmap pixmap) {
		return new LegacyTransparencyImage(pixmap.size())
			.image(nullable(new InputImageKey()))
			.png(pixmap)
			.bytes();
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
		return new LegacyTransparencyImage(expect(new BlocksKey()))
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
		return overlay(markEdges(expect(new EdgeTableKey()), expect(new OutputTemplateKey()).unpack()));
	}
	public byte[] hash() {
		var template = expect(new InputTemplateKey()).unpack();
		return new LegacyTransparencyImage(template.size())
			.image(nullable(new InputImageKey()))
			.add(markHash(expect(new EdgeHashKey()), template))
			.bytes();
	}
	public byte[] roots() {
		var probe = expect(new ProbeTemplateKey()).unpack();
		var candidate = expect(new CandidateTemplateKey()).unpack();
		LegacyTransparencyImage left = new LegacyTransparencyImage(probe.size())
			.image(nullable(new ProbeImageKey()));
		LegacyTransparencyImage right = new LegacyTransparencyImage(candidate.size())
			.image(nullable(new CandidateImageKey()));
		return new LegacyTransparencySplit(left, right)
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
			template = expect(new ProbeTemplateKey()).unpack();
			image = nullable(new ProbeImageKey());
			break;
		case CANDIDATE:
			template = expect(new CandidateTemplateKey()).unpack();
			image = nullable(new CandidateImageKey());
			break;
		default:
			throw new IllegalStateException();
		}
		return new LegacyTransparencyImage(template.size())
			.image(image)
			.add(markPairing(archive.deserialize(new PairingKey(), offset).get(), side, template))
			.bytes();
	}
	public byte[] pairing(MatchSide side) {
		return pairing(archive.deserialize(new BestMatchKey()).orElse(0), side);
	}
	public byte[] pairing(int offset) {
		var pairing = archive.deserialize(new PairingKey(), offset).get();
		var probe = expect(new ProbeTemplateKey()).unpack();
		LegacyTransparencyImage left = new LegacyTransparencyImage(probe.size())
			.image(nullable(new ProbeImageKey()))
			.add(markPairing(pairing, MatchSide.PROBE, probe));
		var candidate = expect(new CandidateTemplateKey()).unpack();
		LegacyTransparencyImage right = new LegacyTransparencyImage(candidate.size())
			.image(nullable(new CandidateImageKey()))
			.add(markPairing(pairing, MatchSide.CANDIDATE, candidate));
		return new LegacyTransparencySplit(left, right).bytes();
	}
	public byte[] pairing() {
		return pairing(archive.deserialize(new BestMatchKey()).orElse(0));
	}
}
