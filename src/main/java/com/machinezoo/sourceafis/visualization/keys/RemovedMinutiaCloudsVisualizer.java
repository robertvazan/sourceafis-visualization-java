// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.keys.*;

public record RemovedMinutiaCloudsVisualizer() implements MinutiaFilterVisualizer {
	@Override
	public RemovedMinutiaCloudsKey key() {
		return new RemovedMinutiaCloudsKey();
	}
	@Override
	public InnerMinutiaeKey baseline() {
		return new InnerMinutiaeKey();
	}
}
