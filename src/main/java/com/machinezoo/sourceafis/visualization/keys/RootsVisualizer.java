// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.layers.*;
import com.machinezoo.sourceafis.visualization.rendering.*;
import com.machinezoo.sourceafis.visualization.types.*;

public record RootsVisualizer() implements VectorVisualizer {
	@Override
	public RootsKey key() {
		return new RootsKey();
	}
	@Override
	public Set<TransparencyKey<?>> dependencies(TransparentOperation operation) {
		return Set.of(
			key(),
			new ProbeTemplateKey(), new ProbeImageKey(), new ProbeGrayscaleKey(),
			new CandidateTemplateKey(), new CandidateImageKey(), new CandidateGrayscaleKey());
	}
	@Override
	public VectorImage visualize(TransparencyArchive archive) {
		var probe = archive.deserialize(new ProbeTemplateKey()).orElseThrow().unpack();
		var candidate = archive.deserialize(new CandidateTemplateKey()).orElseThrow().unpack();
		var buffer = new SplitBuffer(probe.size(), candidate.size())
			.select(MatchSide.PROBE)
			.add(BackgroundImageLayer.deserialize(probe.size(), archive, MatchSide.PROBE).orElse(null))
			.select(MatchSide.CANDIDATE)
			.add(BackgroundImageLayer.deserialize(candidate.size(), archive, MatchSide.CANDIDATE).orElse(null))
			.select(null);
		for (var pair : archive.deserialize(key()).orElseThrow()) {
			var pminutia = MinutiaPoints.center(probe.minutiae()[pair.probe()]);
			var cminutia = MinutiaPoints.center(candidate.minutiae()[pair.candidate()]);
			buffer.add(Svg.line()
				.x1(buffer.leftX(pminutia.x()))
				.y1(buffer.leftY(pminutia.y()))
				.x2(buffer.rightX(cminutia.x()))
				.y2(buffer.rightY(cminutia.y()))
				.stroke("green")
				.strokeWidth(0.4));
		}
		return buffer
			.select(MatchSide.PROBE)
			.add(new MinutiaPositionsLayer(probe))
			.select(MatchSide.CANDIDATE)
			.add(new MinutiaPositionsLayer(candidate))
			.render();
	}
}
