// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.common;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;

public interface TransparencyVisualizer {
	TransparencyKey<?> key();
	String mime();
	String extension();
	default Set<TransparencyKey<?>> dependencies(TransparentOperation operation) {
		return Set.of(key());
	}
	TransparencyVisualization render(TransparencyArchive archive);
}
