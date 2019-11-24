// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import java.io.*;
import java.lang.reflect.*;
import org.apache.commons.io.*;
import org.junit.*;
import com.machinezoo.noexception.*;
import com.machinezoo.sourceafis.*;
import com.machinezoo.sourceafis.transparency.*;

public class ArchiveImagesTest {
	private static byte[] load(String name) {
		return Exceptions.sneak().get(() -> {
			try (InputStream stream = ArchiveImagesTest.class.getResourceAsStream(name)) {
				return IOUtils.toByteArray(stream);
			}
		});
	}
	@Test public void getters() throws IOException {
		byte[] probeImage = load("probe.png");
		byte[] candidateImage = load("candidate.png");
		FingerprintTemplate candidate = new FingerprintTemplate(
			new FingerprintImage()
				.decode(candidateImage));
		TransparencyBuffer archive = new TransparencyBuffer();
		FingerprintTemplate probe;
		try (FingerprintTransparency transparency = archive.capture()) {
			probe = new FingerprintTemplate(
				new FingerprintImage()
					.decode(probeImage));
			new FingerprintMatcher()
				.index(probe)
				.match(candidate);
		}
		ExtractorImages extractor = new ExtractorImages(archive)
			.input(probeImage)
			.output(probe.toByteArray());
		MatcherImages matcher = new MatcherImages(archive)
			.probe(probe.toByteArray())
			.candidate(candidate.toByteArray())
			.probeImage(probeImage)
			.candidateImage(candidateImage);
		for (Object object : new Object[] { extractor, matcher }) {
			int count = 0;
			for (Method method : object.getClass().getMethods()) {
				if (method.getParameterCount() == 0 && method.getDeclaringClass() != Object.class) {
					++count;
					assertNotNull(Exceptions.sneak().get(() -> method.invoke(object)));
				}
			}
			assertThat(count, greaterThanOrEqualTo(3));
		}
	}
}
