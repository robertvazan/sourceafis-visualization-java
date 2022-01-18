// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.images;

import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.common.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public record DoubleMatrixImage(DoubleMatrix matrix, double background, double foreground) implements GrayscaleRenderer {
	@Override
	public GrayscaleVisualization render() {
		var values = matrix.cells();
		var shades = new byte[values.length];
		for (int i = 0; i < shades.length; ++i)
			shades[i] = (byte)(255 - Math.round((values[i] - background) / (foreground - background) * 255));
		return new GrayscaleData(matrix.width(), matrix.height(), shades);
	}
}
