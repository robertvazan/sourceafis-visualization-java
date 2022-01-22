// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.images;

import java.util.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.rendering.*;

public record BinaryLayerImage(BooleanMatrix matrix) implements BinaryModel<BinaryLayerPalette> {
	public BinaryLayerImage {
		Objects.requireNonNull(matrix);
	}
	@Override
	public BinaryImage<BinaryLayerPalette> render() {
		return new BinaryBuffer<>(BinaryLayerPalette.class, matrix).render();
	}
}
