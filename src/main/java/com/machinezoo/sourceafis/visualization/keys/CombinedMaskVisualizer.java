// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.keys.*;

public record CombinedMaskVisualizer() implements BlockMaskVisualizer {
	@Override
	public CombinedMaskKey key() {
		return new CombinedMaskKey();
	}
	@Override
	public boolean inverted() {
		return true;
	}
}
