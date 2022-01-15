// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.lang.reflect.*;
import org.apache.commons.io.*;
import org.junit.jupiter.api.*;
import com.machinezoo.noexception.*;
import com.machinezoo.sourceafis.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;

public class TransparencyGalleryTest {
	private static byte[] load(String name) {
		return Exceptions.sneak().get(() -> {
			try (InputStream stream = TransparencyGalleryTest.class.getResourceAsStream(name)) {
				return IOUtils.toByteArray(stream);
			}
		});
	}
	@Test
	public void getters() throws IOException {
		byte[] probeImage = load("probe.png");
		byte[] candidateImage = load("candidate.png");
		var candidate = new FingerprintTemplate(
			new FingerprintImage(candidateImage));
		var buffer = new TransparencyBuffer()
			.write(new InputImageKey(), probeImage)
			.write(new ProbeImageKey(), probeImage)
			.write(new CandidateImageKey(), candidateImage)
			.write(new CandidateTemplateKey(), candidate.toByteArray());
		FingerprintTemplate probe;
		double score;
		try (var transparency = buffer.open()) {
			probe = new FingerprintTemplate(
				new FingerprintImage(probeImage));
			score = new FingerprintMatcher(probe)
				.match(candidate);
		}
		var archive = buffer
			.write(new OutputTemplateKey(), probe.toByteArray())
			.write(new InputTemplateKey(), probe.toByteArray())
			.write(new ProbeTemplateKey(), probe.toByteArray())
			.serialize(new OutputScoreKey(), score)
			.toArchive();
		TransparencyGallery gallery = new TransparencyGallery(archive);
		int count = 0;
		for (Method method : gallery.getClass().getMethods()) {
			if (method.getParameterCount() == 0 && method.getDeclaringClass() != Object.class) {
				++count;
				assertNotNull(Exceptions.sneak().get(() -> method.invoke(gallery)));
			}
		}
		assertThat(count, greaterThanOrEqualTo(3));
	}
}
