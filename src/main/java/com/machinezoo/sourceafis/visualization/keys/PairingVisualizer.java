// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.keys.*;

public record PairingVisualizer() implements PairingGraphVisualizer {
	@Override
	public PairingKey key() {
		return new PairingKey();
	}
}
