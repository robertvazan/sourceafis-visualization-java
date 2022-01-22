// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.images;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.rendering.*;

public record BinaryDiffImage(BooleanMatrix previous, BooleanMatrix next) implements PaletteModel<DiffPalette> {
	public BinaryDiffImage {
		Objects.requireNonNull(previous);
		Objects.requireNonNull(next);
		Validate.isTrue(previous.size().equals(next.size()));
	}
	@Override
	public PaletteImage<DiffPalette> render() {
		var buffer = new PaletteBuffer<>(DiffPalette.class, next.size());
		for (int y = 0; y < next.height(); ++y) {
			for (int x = 0; x < next.width(); ++x) {
				if (next.get(x, y))
					buffer.set(x, y, previous.get(x, y) ? DiffPalette.FOREGROUND : DiffPalette.ADDED);
				else
					buffer.set(x, y, previous.get(x, y) ? DiffPalette.REMOVED : DiffPalette.BACKGROUND);
			}
		}
		return buffer.render();
	}
}
