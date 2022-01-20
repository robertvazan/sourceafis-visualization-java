// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.images;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.rendering.*;

public record DoubleMatrixImage(DoubleMatrix matrix, double background, double foreground) implements GrayscaleModel {
	public DoubleMatrixImage {
		Objects.requireNonNull(matrix);
		Validate.isTrue(foreground != background);
	}
	@Override
	public GrayscaleImage render() {
		var values = matrix.cells();
		var shades = new byte[values.length];
		for (int i = 0; i < shades.length; ++i)
			shades[i] = (byte)(255 - Math.round((values[i] - background) / (foreground - background) * 255));
		return new GrayscaleFrame(matrix.width(), matrix.height(), shades);
	}
}
