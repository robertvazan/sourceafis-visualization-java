// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.keys.*;

public record TopMinutiaeVisualizer() implements MinutiaFilterVisualizer {
	@Override
	public TopMinutiaeKey key() {
		return new TopMinutiaeKey();
	}
	@Override
	public RemovedMinutiaCloudsKey baseline() {
		return new RemovedMinutiaCloudsKey();
	}
}
