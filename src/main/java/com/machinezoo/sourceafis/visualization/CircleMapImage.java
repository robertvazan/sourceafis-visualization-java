package com.machinezoo.sourceafis.visualization;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class CircleMapImage {
	private final BlockMap blocks;
	private final DoubleMatrix values;
	private final byte[] background;
	public CircleMapImage(BlockMap blocks, DoubleMatrix values, byte[] background) {
		this.blocks = blocks;
		this.values = values;
		this.background = background;
	}
	public DomElement document() {
		DomFragment circles = new DomFragment();
		for (IntPoint at : blocks.primary.blocks) {
			IntBlock block = blocks.primary.block(at);
			double radius = Math.sqrt((values.get(at) - values.stats.getMin()) / (values.stats.getMax() - values.stats.getMin())) * block.radius();
			circles.add(PointMarker.circle(block.center())
				.r(radius)
				.stroke("#080")
				.strokeWidth(0.3)
				.fill("#0f0")
				.fillOpacity(0.2));
		}
		return new SvgImage()
			.size(blocks)
			.background(background)
			.content(circles)
			.document();
	}
}
