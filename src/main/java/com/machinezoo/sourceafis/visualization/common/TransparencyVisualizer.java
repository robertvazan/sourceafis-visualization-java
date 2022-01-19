// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;

public interface TransparencyVisualizer {
	TransparencyKey<?> key();
	default TransparentOperation operation() {
		return key().operation();
	}
	/*
	 * Specifying MIME/extension on visualizer in addition to specifying it on generated visualization is a tradeoff.
	 * Calling code can know upfront what output to expect, but visualizers lose freedom to switch MIME based on content.
	 * This hardcoding of output type also manifests in specialized visualizer interfaces.
	 * Fortunately, visualizers are specialized enough to know their output type with certainty.
	 * Switching MIME type is possible only in some degenerate cases when optional dependencies are missing
	 * and output is consequently greatly simplified. These corner cases are not worth the effort and complexity.
	 */
	String mime();
	String extension();
	default Set<TransparencyKey<?>> dependencies(TransparentOperation operation) {
		return Set.of(key());
	}
	TransparencyImage visualize(TransparencyArchive archive);
}
