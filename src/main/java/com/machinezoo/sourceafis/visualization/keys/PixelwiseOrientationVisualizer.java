// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import java.awt.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.rendering.*;
import com.machinezoo.sourceafis.visualization.types.*;
import com.machinezoo.stagean.*;

public record PixelwiseOrientationVisualizer() implements RasterVisualizer {
	@Override
	public PixelwiseOrientationKey key() {
		return new PixelwiseOrientationKey();
	}
	@Override
	@DraftCode("Drop dependency on java.desktop Color class.")
	public RasterImage visualize(TransparencyArchive archive) {
		var matrix = archive.deserialize(key()).orElseThrow();
		var buffer = new RasterBuffer(matrix.size());
		double max = Math.log1p(IntPoints.stream(matrix.size()).map(matrix::get).mapToDouble(DoublePoints::length).max().orElse(1));
		for (var at : IntPoints.stream(matrix.size())) {
			var vector = matrix.get(at);
			if (vector.x() != 0 || vector.y() != 0) {
				double angle = vector.angle();
				double strength = Math.log1p(DoublePoints.length(vector)) / max;
				buffer.set(at, Color.HSBtoRGB((float)(angle / DoubleAnglesEx.PI2), (float)(0.2 + 0.8 * strength), 1.0f) | 0xFF_00_00_00);
			}
		}
		return buffer.render();
	}
}
