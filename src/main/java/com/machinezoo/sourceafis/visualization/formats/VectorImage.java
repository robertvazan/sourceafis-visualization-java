// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.formats;

import java.nio.charset.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.visualization.*;

public interface VectorImage extends TransparencyImage {
	DomElement tree();
	@Override
	default String mime() {
		return "image/svg+xml";
	}
	@Override
	default String extension() {
		return ".svg";
	}
	default String source() {
		return tree().toString();
	}
	@Override
	default byte[] bytes() {
		return source().getBytes(StandardCharsets.UTF_8);
	}
}
