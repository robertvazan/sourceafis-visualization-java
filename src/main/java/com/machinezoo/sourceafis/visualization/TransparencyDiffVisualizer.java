// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import com.machinezoo.sourceafis.transparency.*;

public interface TransparencyDiffVisualizer extends TransparencyVisualizer {
	TransparencyKey<?> baseline();
}
