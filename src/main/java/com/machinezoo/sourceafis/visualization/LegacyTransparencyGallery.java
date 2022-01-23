// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static com.machinezoo.sourceafis.visualization.LegacyTransparencyMarkers.*;
import java.util.*;
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
