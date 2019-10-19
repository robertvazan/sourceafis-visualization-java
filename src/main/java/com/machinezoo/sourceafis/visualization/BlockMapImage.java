package com.machinezoo.sourceafis.visualization;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class BlockMapImage {
	private final BlockMap blocks;
	private final boolean secondary;
	private final byte[] background;
	public BlockMapImage(BlockMap blocks, boolean secondary, byte[] background) {
		this.blocks = blocks;
		this.secondary = secondary;
		this.background = background;
	}
	public BlockMapImage(BlockMap blocks, byte[] background) {
		this(blocks, false, background);
	}
	public DomElement document() {
		if (secondary)
			return document(blocks.secondary, blocks.primary, "#080", background);
		else
			return document(blocks.primary, blocks.secondary, "#00c", background);
	}
	private DomElement document(BlockGrid foreground, BlockGrid background, String color, byte[] image) {
		return new SvgImage()
			.size(blocks)
			.padding(1)
			.background(image)
			.content(new DomFragment()
				.add(grid(background, "#888", 0.1))
				.add(grid(foreground, color, 0.25)))
			.document();
	}
	private DomContent grid(BlockGrid grid, String color, double width) {
		DomFragment svg = new DomFragment();
		for (int x : grid.x) {
			svg.add(Svg.line()
				.x1(x)
				.y1(0)
				.x2(x)
				.y2(blocks.pixels.y)
				.stroke(color)
				.strokeWidth(width));
		}
		for (int y : grid.y) {
			svg.add(Svg.line()
				.x1(0)
				.y1(y)
				.x2(blocks.pixels.x)
				.y2(y)
				.stroke(color)
				.strokeWidth(width));
		}
		return svg;
	}
}
