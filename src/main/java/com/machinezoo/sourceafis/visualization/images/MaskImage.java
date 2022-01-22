// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.images;

import java.util.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.rendering.*;

public record MaskImage(BooleanMatrix matrix, boolean inverted) implements BinaryModel<MaskPalette> {
	public MaskImage {
		Objects.requireNonNull(matrix);
	}
	public MaskImage(BooleanMatrix matrix) {
		this(matrix, false);
	}
	@Override
	public BinaryImage<MaskPalette> render() {
		var buffer = new BinaryBuffer<>(MaskPalette.class, matrix);
		/*
		 * Mask pixels are usually 1 for interior and 0 for exterior. MaskPalette is the other way round.
		 */
		if (!inverted)
			buffer.invert();
		return buffer.render();
	}
}
