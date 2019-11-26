// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static com.machinezoo.sourceafis.visualization.TransparencyImages.*;
import java.util.*;
import com.machinezoo.sourceafis.transparency.*;

public class MatcherImages {
	private final TransparencyArchive archive;
	private final TransparencyContext context;
	public MatcherImages(TransparencyArchive archive, TransparencyContext context) {
		Objects.requireNonNull(archive);
		Objects.requireNonNull(context);
		this.archive = archive;
		this.context = context;
	}
	private Template probe() {
		return Optional.ofNullable(context.probe).map(Template::parse).orElse(null);
	}
	private Template candidate() {
		return Optional.ofNullable(context.candidate).map(Template::parse).orElse(null);
	}
	public VisualizationImage edgeHash() {
		return visualizeEdgeHash(archive.edgeHash(), probe(), context.probeImage);
	}
	public VisualizationImage rootPairs() {
		return visualizeRootPairs(archive.rootPairs(), probe(), candidate(), context.probeImage, context.candidateImage);
	}
	public VisualizationImage pairing(int offset, MatchSide side) {
		switch (side) {
		case PROBE:
			return visualizePairing(archive.pairing(offset), side, probe(), context.probeImage);
		case CANDIDATE:
			return visualizePairing(archive.pairing(offset), side, candidate(), context.candidateImage);
		default:
			throw new IllegalStateException();
		}
	}
	public VisualizationImage pairing(MatchSide side) {
		return pairing(archive.bestMatch().orElse(0), side);
	}
	public VisualizationImage pairing(int offset) {
		return visualizePairing(archive.pairing(offset), probe(), candidate(), context.probeImage, context.candidateImage);
	}
	public VisualizationImage pairing() {
		return pairing(archive.bestMatch().orElse(0));
	}
}
