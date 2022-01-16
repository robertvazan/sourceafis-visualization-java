// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.sourceafis.visualization.common.*;

public record GrayscaleData(int width, int height, @ByteColor byte[] shades) implements GrayscaleVisualization {
	public GrayscaleData {
		Validate.isTrue(width > 0 && height > 0);
		Objects.requireNonNull(shades);
		Validate.isTrue(shades.length == width * height);
	}
}
