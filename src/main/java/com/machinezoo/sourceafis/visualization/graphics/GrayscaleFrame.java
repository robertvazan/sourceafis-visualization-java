// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.graphics;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.sourceafis.visualization.common.*;

public record GrayscaleFrame(int width, int height, @ByteColor byte[] shades) implements GrayscaleImage {
	public GrayscaleFrame {
		Validate.isTrue(width > 0 && height > 0);
		Objects.requireNonNull(shades);
		Validate.isTrue(shades.length == width * height);
	}
}
