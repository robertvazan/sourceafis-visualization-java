// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.formats;

import java.io.*;
import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.sourceafis.visualization.*;

/*
 * This can be also instantiated by application, but unsupported MIME types will have blank extension().
 */
public record MaterializedImage(int width, int height, String mime, byte[] bytes) implements TransparencyImage, Serializable {
	private static final String[] SUPPORTED_MIMES = new String[] {
		"image/jpeg",
		"image/png",
		"image/svg+xml",
	};
	public MaterializedImage {
		Validate.isTrue(width > 0 && height > 0, "Image dimensions must be positive.");
		Validate.isTrue(List.of(SUPPORTED_MIMES).contains(mime), "Unsupported MIME type.");
		Objects.requireNonNull(bytes, "Missing image data.");
	}
	@Override
	public String extension() {
		return switch (mime) {
			case "image/jpeg" -> ".jpeg";
			case "image/png" -> ".png";
			case "image/svg+xml" -> ".svg";
			default -> "";
		};
	}
	@Override
	public MaterializedImage materialize() {
		return this;
	}
}
