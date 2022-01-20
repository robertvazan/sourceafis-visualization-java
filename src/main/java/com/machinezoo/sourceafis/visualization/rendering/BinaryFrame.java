// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.rendering;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.sourceafis.visualization.formats.*;

public record BinaryFrame<T extends Enum<T> & PaletteSymbol>(Class<T> palette, int width, int height, BitSet bits) implements BinaryImage<T> {
	public BinaryFrame {
		Objects.requireNonNull(palette);
		Validate.isTrue(palette.getEnumConstants().length == 2);
		Validate.isTrue(width > 0 && height > 0);
		Objects.requireNonNull(bits);
	}
}
