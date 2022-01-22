// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.keys.*;

public record RelativeContrastMaskVisualizer() implements BlockMaskVisualizer {
	@Override
	public RelativeContrastMaskKey key() {
		return new RelativeContrastMaskKey();
	}
	@Override
	public boolean inverted() {
		return true;
	}
}
