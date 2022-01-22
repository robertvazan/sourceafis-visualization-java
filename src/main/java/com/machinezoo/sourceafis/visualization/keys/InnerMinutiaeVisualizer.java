// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.keys.*;

public record InnerMinutiaeVisualizer() implements MinutiaFilterVisualizer {
	@Override
	public InnerMinutiaeKey key() {
		return new InnerMinutiaeKey();
	}
	@Override
	public SkeletonMinutiaeKey baseline() {
		return new SkeletonMinutiaeKey();
	}
}
