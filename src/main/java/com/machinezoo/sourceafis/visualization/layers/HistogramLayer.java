// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.layers;

import java.util.stream.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.rendering.*;
import com.machinezoo.sourceafis.visualization.svg.*;
import com.machinezoo.sourceafis.visualization.types.*;
import com.machinezoo.stagean.*;

public record HistogramLayer(BlockGrid grid, HistogramCube histogram) implements LayerModel {
	private static final int SLOTS = 32;
	@Override
	@DraftCode("Centralize styling using CSS.")
	public ImageLayer render() {
		var buffer = new LayerBuffer();
		for (var at : IntPoints.stream(grid.blocks())) {
			int[] resampled = new int[SLOTS];
			for (int z = 0; z < histogram.bins(); ++z)
				resampled[z * SLOTS / histogram.bins()] += histogram.get(at, z);
			int total = IntStream.of(resampled).sum();
			var block = grid.block(at);
			var center = IntRects.center(block);
			double radius = IntRects.radius(block);
			double bottom = center.y() + 0.8 * radius;
			double stretch = 0.9 * radius;
			var poly = new SvgPointList();
			for (int i = 0; i < SLOTS; ++i) {
				double height = 1.6 * radius * Math.log1p(resampled[i]) / Math.log1p(total);
				poly.add(center.x() + stretch * (2 * i + 1 - SLOTS) / (SLOTS - 1), bottom - height);
			}
			poly.add(center.x() + stretch, bottom);
			poly.add(center.x() - stretch, bottom);
			buffer.add(poly.polygon().fill("green").fillOpacity(0.4).stroke("#080").strokeWidth(0.2));
		}
		return buffer.render();
	}
}
