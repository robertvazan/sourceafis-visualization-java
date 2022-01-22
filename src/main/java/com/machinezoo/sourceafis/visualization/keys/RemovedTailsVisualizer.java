// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.transparency.types.*;

public record RemovedTailsVisualizer(SkeletonType skeleton) implements SkeletonFilterVisualizer {
	@Override
	public RemovedTailsKey key() {
		return new RemovedTailsKey(skeleton);
	}
	@Override
	public RemovedGapsKey baseline() {
		return new RemovedGapsKey(skeleton);
	}
}
