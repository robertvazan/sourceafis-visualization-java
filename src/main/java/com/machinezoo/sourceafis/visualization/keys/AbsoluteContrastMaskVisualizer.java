// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.keys.*;

public record AbsoluteContrastMaskVisualizer() implements BlockMaskVisualizer {
	@Override
	public AbsoluteContrastMaskKey key() {
		return new AbsoluteContrastMaskKey();
	}
	@Override
	public boolean inverted() {
		return true;
	}
}
