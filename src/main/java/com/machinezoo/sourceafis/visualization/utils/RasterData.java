// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.sourceafis.visualization.common.*;

public record RasterData(int width, int height, @IntColor int[] pixels) implements RasterVisualization {
	public RasterData {
		Validate.isTrue(width > 0 && height > 0);
		Objects.requireNonNull(pixels);
		Validate.isTrue(pixels.length == width * height);
	}
}
