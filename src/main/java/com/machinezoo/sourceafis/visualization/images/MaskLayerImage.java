// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.images;

import java.util.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.rendering.*;

public record MaskLayerImage(BooleanMatrix matrix, boolean inverted) implements BinaryModel<MaskLayerPalette> {
	public MaskLayerImage {
		Objects.requireNonNull(matrix);
	}
	public MaskLayerImage(BooleanMatrix matrix) {
		this(matrix, false);
	}
	@Override
	public BinaryImage<MaskLayerPalette> render() {
		var buffer = new BinaryBuffer<>(MaskLayerPalette.class, matrix);
		/*
		 * Mask pixels are usually 1 for interior and 0 for exterior. MaskPalette is the other way round.
		 */
		if (!inverted)
			buffer.invert();
		return buffer.render();
	}
}
