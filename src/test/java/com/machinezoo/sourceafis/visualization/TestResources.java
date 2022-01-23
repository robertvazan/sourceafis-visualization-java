// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import org.apache.commons.io.*;
import com.machinezoo.noexception.*;

public class TestResources {
	private static byte[] load(String name) {
		return Exceptions.sneak().get(() -> {
			try (var stream = TestResources.class.getResourceAsStream(name)) {
				return IOUtils.toByteArray(stream);
			}
		});
	}
	public static byte[] probe() {
		return load("probe.png");
	}
	public static byte[] candidate() {
		return load("candidate.png");
	}
}
