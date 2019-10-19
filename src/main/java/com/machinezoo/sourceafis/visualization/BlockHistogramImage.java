package com.machinezoo.sourceafis.visualization;

import java.util.*;
import java.util.stream.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class BlockHistogramImage {
	private final BlockMap blocks;
	private final HistogramCube histogram;
	private final byte[] background;
	public BlockHistogramImage(BlockMap blocks, HistogramCube histogram, byte[] background) {
		this.blocks = blocks;
		this.histogram = histogram;
		this.background = background;
	}
	public BlockHistogramImage(BlockMap blocks, HistogramCube histogram) {
		this(blocks, histogram, null);
	}
	public DomElement document() {
		BlockGrid grid = histogram.width == blocks.primary.blocks.x ? blocks.primary : blocks.secondary;
		DomFragment svg = new DomFragment();
		int slots = 32;
		for (IntPoint at : grid.blocks) {
			int[] resampled = new int[slots];
			for (int z = 0; z < histogram.depth; ++z)
				resampled[z * slots / histogram.depth] += histogram.get(at, z);
			int total = IntStream.of(resampled).sum();
			IntBlock block = grid.block(at);
			List<String> points = new ArrayList<>();
			double bottom = block.center().y + 0.8 * block.radius();
			double stretch = 0.9 * block.radius();
			for (int i = 0; i < slots; ++i) {
				double height = 1.6 * block.radius() * Math.log1p(resampled[i]) / Math.log1p(total);
				points.add(point(block.center().x + stretch * (2 * i + 1 - slots) / (slots - 1), bottom - height));
			}
			points.add(point(block.center().x + stretch, bottom));
			points.add(point(block.center().x - stretch, bottom));
			svg.add(Svg.polygon().points(String.join(" ", points)).fill("green").fillOpacity(0.4).stroke("#080").strokeWidth(0.2));
		}
		return new SvgImage()
			.size(blocks)
			.background(background)
			.content(svg)
			.document();
	}
	private static String point(double x, double y) {
		return x + "," + y;
	}
}
