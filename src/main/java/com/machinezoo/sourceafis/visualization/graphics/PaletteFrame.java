// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.graphics;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.sourceafis.visualization.common.*;

public record PaletteFrame<T extends Enum<T> & ColorCode>(Class<T> type, int width, int height, byte[] codes) implements PaletteImage<T> {
	public PaletteFrame {
		Objects.requireNonNull(type);
		Validate.isTrue(width > 0 && height > 0);
		Objects.requireNonNull(codes);
		Validate.isTrue(codes.length == width * height);
	}
}
