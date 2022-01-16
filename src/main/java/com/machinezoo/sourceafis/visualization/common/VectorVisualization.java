// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

import java.nio.charset.*;
import com.machinezoo.pushmode.dom.*;

public interface VectorVisualization extends TransparencyVisualization {
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
