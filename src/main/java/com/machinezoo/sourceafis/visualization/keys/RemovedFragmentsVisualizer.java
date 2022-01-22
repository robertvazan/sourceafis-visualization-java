// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.transparency.types.*;

public record RemovedFragmentsVisualizer(SkeletonType skeleton) implements SkeletonFilterVisualizer {
	@Override
	public RemovedFragmentsKey key() {
		return new RemovedFragmentsKey(skeleton);
	}
	@Override
	public RemovedTailsKey baseline() {
		return new RemovedTailsKey(skeleton);
	}
}
