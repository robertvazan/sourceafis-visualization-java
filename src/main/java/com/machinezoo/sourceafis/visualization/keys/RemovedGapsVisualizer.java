// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.transparency.types.*;

public record RemovedGapsVisualizer(SkeletonType skeleton) implements SkeletonFilterVisualizer {
	@Override
	public RemovedGapsKey key() {
		return new RemovedGapsKey(skeleton);
	}
	@Override
	public RemovedPoresKey baseline() {
		return new RemovedPoresKey(skeleton);
	}
}
