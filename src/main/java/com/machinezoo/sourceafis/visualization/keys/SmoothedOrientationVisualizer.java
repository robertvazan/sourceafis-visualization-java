// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.keys.*;

public record SmoothedOrientationVisualizer() implements BlockLevelOrientationVisualizer {
	@Override
	public SmoothedOrientationKey key() {
		return new SmoothedOrientationKey();
	}
}
