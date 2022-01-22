// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.transparency.types.*;

public record RemovedPoresVisualizer(SkeletonType skeleton) implements SkeletonFilterVisualizer {
	@Override
	public RemovedPoresKey key() {
		return new RemovedPoresKey(skeleton);
	}
	@Override
	public RemovedDotsKey baseline() {
		return new RemovedDotsKey(skeleton);
	}
}
