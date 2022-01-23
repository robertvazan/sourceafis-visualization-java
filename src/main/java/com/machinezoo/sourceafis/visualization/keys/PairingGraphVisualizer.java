// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.layers.*;
import com.machinezoo.sourceafis.visualization.rendering.*;

public interface PairingGraphVisualizer extends VectorVisualizer {
	SerializedObjectKey<PairingGraph> key();
	@Override
	default Set<TransparencyKey<?>> dependencies() {
		return Set.of(
			key(),
			new ProbeTemplateKey(), new ProbeImageKey(), new ProbeGrayscaleKey(),
			new CandidateTemplateKey(), new CandidateImageKey(), new CandidateGrayscaleKey());
	}
	@Override
	default VectorImage visualize(TransparencyArchive archive) {
		var probe = archive.deserialize(new ProbeTemplateKey()).orElseThrow().unpack();
		var candidate = archive.deserialize(new CandidateTemplateKey()).orElseThrow().unpack();
		var pairing = archive.deserialize(key()).orElseThrow();
		return new SplitBuffer(probe.size(), candidate.size())
			.select(MatchSide.PROBE)
			.add(BackgroundImageLayer.deserialize(probe.size(), archive, MatchSide.PROBE).orElse(null))
			.add(new PairingGraphLayer(pairing, MatchSide.PROBE, probe))
			.select(MatchSide.CANDIDATE)
			.add(BackgroundImageLayer.deserialize(candidate.size(), archive, MatchSide.CANDIDATE).orElse(null))
			.add(new PairingGraphLayer(pairing, MatchSide.CANDIDATE, candidate))
			.render();
	}
}
