// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static com.machinezoo.sourceafis.visualization.TransparencyImages.*;
import java.util.*;
import com.machinezoo.sourceafis.transparency.*;

public class MatcherImages {
	private final TransparencyArchive archive;
	public MatcherImages(TransparencyArchive archive) {
		Objects.requireNonNull(archive);
		this.archive = archive;
	}
	private byte[] probe;
	public MatcherImages probe(byte[] template) {
		probe = template;
		return this;
	}
	private Template probe() {
		return Optional.ofNullable(probe).map(Template::parse).orElse(null);
	}
	private byte[] candidate;
	public MatcherImages candidate(byte[] template) {
		candidate = template;
		return this;
	}
	private Template candidate() {
		return Optional.ofNullable(candidate).map(Template::parse).orElse(null);
	}
	private byte[] probeImage;
	public MatcherImages probeImage(byte[] image) {
		probeImage = image;
		return this;
	}
	private byte[] candidateImage;
	public MatcherImages candidateImage(byte[] image) {
		candidateImage = image;
		return this;
	}
	public VisualizationImage edgeHash() {
		return visualizeEdgeHash(archive.edgeHash(), probe(), probeImage);
	}
	public VisualizationImage rootPairs() {
		return visualizeRootPairs(archive.rootPairs(), probe(), candidate(), probeImage, candidateImage);
	}
	public VisualizationImage pairing(int offset, MatchSide side) {
		switch (side) {
		case PROBE:
			return visualizePairing(archive.pairing(offset), side, probe(), probeImage);
		case CANDIDATE:
			return visualizePairing(archive.pairing(offset), side, candidate(), candidateImage);
		default:
			throw new IllegalStateException();
		}
	}
	public VisualizationImage pairing(MatchSide side) {
		return pairing(archive.bestMatch().orElse(0), side);
	}
	public VisualizationImage pairing(int offset) {
		return visualizePairing(archive.pairing(offset), probe(), candidate(), probeImage, candidateImage);
	}
	public VisualizationImage pairing() {
		return pairing(archive.bestMatch().orElse(0));
	}
}
