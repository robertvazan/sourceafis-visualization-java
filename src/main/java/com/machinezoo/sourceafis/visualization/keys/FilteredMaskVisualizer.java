// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.keys.*;

public record FilteredMaskVisualizer() implements BlockMaskVisualizer {
	@Override
	public FilteredMaskKey key() {
		return new FilteredMaskKey();
	}
	@Override
	public boolean inverted() {
		return false;
	}
}
