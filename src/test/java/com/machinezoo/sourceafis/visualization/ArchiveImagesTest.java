// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import java.io.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.util.zip.*;
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
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		FingerprintTemplate probe;
		try (FingerprintTransparency transparency = FingerprintTransparency.zip(buffer)) {
			probe = new FingerprintTemplate(
				new FingerprintImage()
					.decode(probeImage));
			new FingerprintMatcher()
				.index(probe)
				.match(candidate);
		}
		Path temporary = Files.createTempFile("", ".zip");
		try {
			Files.write(temporary, buffer.toByteArray());
			try (ZipFile zip = new ZipFile(temporary.toFile())) {
				TransparencyArchive archive = new TransparencyZip(zip);
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
		} finally {
			Files.delete(temporary);
		}
	}
}
