// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static com.machinezoo.sourceafis.visualization.TransparencyImages.*;
import java.util.*;
import java.util.function.*;
import com.machinezoo.sourceafis.transparency.*;

public class MatcherImages {
	private final TransparencyArchive archive;
	public MatcherImages(TransparencyArchive archive) {
		Objects.requireNonNull(archive);
		this.archive = archive;
	}
	private Supplier<byte[]> probe = () -> null;
	public MatcherImages probe(Supplier<byte[]> supplier) {
		Objects.requireNonNull(supplier);
		probe = supplier;
		return this;
	}
	public MatcherImages probe(byte[] template) {
		return probe(() -> template);
	}
	private Template probe() {
		return Optional.ofNullable(probe.get()).map(Template::new).orElse(null);
	}
	private Supplier<byte[]> candidate = () -> null;
	public MatcherImages candidate(Supplier<byte[]> supplier) {
		Objects.requireNonNull(supplier);
		candidate = supplier;
		return this;
	}
	public MatcherImages candidate(byte[] template) {
		return candidate(() -> template);
	}
	private Template candidate() {
		return Optional.ofNullable(candidate.get()).map(Template::new).orElse(null);
	}
	private Supplier<byte[]> probeImage = () -> null;
	public MatcherImages probeImage(Supplier<byte[]> supplier) {
		Objects.requireNonNull(supplier);
		probeImage = supplier;
		return this;
	}
	public MatcherImages probeImage(byte[] image) {
		return probeImage(() -> image);
	}
	private byte[] probeImage() {
		return probeImage.get();
	}
	private Supplier<byte[]> candidateImage = () -> null;
	public MatcherImages candidateImage(Supplier<byte[]> supplier) {
		Objects.requireNonNull(supplier);
		candidateImage = supplier;
		return this;
	}
	public MatcherImages candidateImage(byte[] image) {
		return candidateImage(() -> image);
	}
	private byte[] candidateImage() {
		return candidateImage.get();
	}
	public VisualizationImage edgeHash() {
		return visualizeEdgeHash(archive.edgeHash(), probe(), probeImage());
	}
	public VisualizationImage rootPairs() {
		return visualizeRootPairs(archive.rootPairs(), probe(), candidate(), probeImage(), candidateImage());
	}
	public VisualizationImage pairing(int offset, MatchSide side) {
		switch (side) {
		case PROBE:
			return visualizePairing(archive.pairing(offset), side, probe(), probeImage());
		case CANDIDATE:
			return visualizePairing(archive.pairing(offset), side, candidate(), candidateImage());
		default:
			throw new IllegalStateException();
		}
	}
	public VisualizationImage pairing(MatchSide side) {
		return pairing(archive.bestMatch(), side);
	}
	public VisualizationImage pairing(int offset) {
		return visualizePairing(archive.pairing(offset), probe(), candidate(), probeImage(), candidateImage());
	}
	public VisualizationImage pairing() {
		return pairing(archive.bestMatch());
	}
}
