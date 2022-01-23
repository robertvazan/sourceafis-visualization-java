// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;

public interface TransparencyDiffVisualizer extends TransparencyVisualizer {
	TransparencyKey<?> baseline();
	@Override
	default Set<TransparencyKey<?>> dependencies() {
		return Set.of(key(), baseline());
	}
}
