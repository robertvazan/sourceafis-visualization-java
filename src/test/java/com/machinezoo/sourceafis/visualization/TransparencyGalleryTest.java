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

public class TransparencyGalleryTest {
	private static byte[] load(String name) {
		return Exceptions.sneak().get(() -> {
			try (InputStream stream = TransparencyGalleryTest.class.getResourceAsStream(name)) {
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
		TransparencyContext context = new TransparencyContext()
			.image(TransparencyRole.EXTRACTED, probeImage)
			.template(TransparencyRole.EXTRACTED, probe.toByteArray())
			.image(TransparencyRole.PROBE, probeImage)
			.template(TransparencyRole.PROBE, probe.toByteArray())
			.image(TransparencyRole.CANDIDATE, candidateImage)
			.template(TransparencyRole.CANDIDATE, candidate.toByteArray());
		TransparencyGallery gallery = new TransparencyGallery(archive, context);
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
