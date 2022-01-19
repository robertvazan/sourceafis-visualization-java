// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.graphics;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.sourceafis.visualization.common.*;

public record RasterFrame(int width, int height, @IntColor int[] pixels) implements RasterImage {
	public RasterFrame {
		Validate.isTrue(width > 0 && height > 0);
		Objects.requireNonNull(pixels);
		Validate.isTrue(pixels.length == width * height);
	}
}
