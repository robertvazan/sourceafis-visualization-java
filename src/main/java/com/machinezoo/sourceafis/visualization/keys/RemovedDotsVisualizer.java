// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.transparency.types.*;

public record RemovedDotsVisualizer(SkeletonType skeleton) implements SkeletonFilterVisualizer {
	@Override
	public RemovedDotsKey key() {
		return new RemovedDotsKey(skeleton);
	}
	@Override
	public TracedSkeletonKey baseline() {
		return new TracedSkeletonKey(skeleton);
	}
}
