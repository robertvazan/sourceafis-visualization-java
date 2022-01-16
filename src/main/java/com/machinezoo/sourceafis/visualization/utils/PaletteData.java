// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.sourceafis.visualization.common.*;

public record PaletteData<T extends Enum<T> & ColorCode>(Class<T> type, int width, int height, byte[] codes) implements PaletteVisualization<T> {
	public PaletteData {
		Objects.requireNonNull(type);
		Validate.isTrue(width > 0 && height > 0);
		Objects.requireNonNull(codes);
		Validate.isTrue(codes.length == width * height);
	}
}
