// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.keys.*;

public record BestPairingVisualizer() implements PairingGraphVisualizer {
	@Override
	public BestPairingKey key() {
		return new BestPairingKey();
	}
}
