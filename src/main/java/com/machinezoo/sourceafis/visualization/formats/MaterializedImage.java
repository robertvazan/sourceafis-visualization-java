// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.formats;

import java.io.*;
import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.sourceafis.visualization.*;

public record MaterializedImage(int width, int height, String mime, byte[] bytes) implements TransparencyImage, Serializable {
	public MaterializedImage {
		Validate.isTrue(width > 0 && height > 0, "Image dimensions must be positive.");
		Validate.notBlank(mime, "Unspecified MIME type.");
		Objects.requireNonNull(bytes, "Missing image data.");
	}
	@Override
	public MaterializedImage materialize() {
		return this;
	}
}
