// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.images;

import java.util.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.rendering.*;
import com.machinezoo.sourceafis.visualization.types.*;

public record SkeletonLayerImage(BooleanMatrix matrix) implements BinaryModel<SkeletonLayerPalette> {
	public SkeletonLayerImage {
		Objects.requireNonNull(matrix);
	}
	public SkeletonLayerImage(SkeletonGraph skeleton) {
		this(SkeletonGraphs.shadow(skeleton));
	}
	@Override
	public BinaryImage<SkeletonLayerPalette> render() {
		return new BinaryBuffer<>(SkeletonLayerPalette.class, matrix).render();
	}
}
