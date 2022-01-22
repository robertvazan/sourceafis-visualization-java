// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.keys.*;

public record BlockOrientationVisualizer() implements BlockLevelOrientationVisualizer {
	@Override
	public BlockOrientationKey key() {
		return new BlockOrientationKey();
	}
}
