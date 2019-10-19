package com.machinezoo.sourceafis.visualization;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class BlockOrientationImage {
	private final BlockMap blocks;
	private final DoublePointMatrix orientations;
	private final BooleanMatrix mask;
	private final byte[] background;
	public BlockOrientationImage(BlockMap blocks, DoublePointMatrix orientations, BooleanMatrix mask, byte[] background) {
		this.blocks = blocks;
		this.orientations = orientations;
		this.mask = mask;
		this.background = background;
	}
	public DomElement document() {
		DomFragment markers = new DomFragment();
		for (IntPoint at : blocks.primary.blocks) {
			if (mask == null || mask.get(at)) {
				IntBlock block = blocks.primary.block(at);
				DoublePoint center = block.center();
				DoublePoint direction = DoubleAngle.toVector(DoubleAngle.fromOrientation(DoubleAngle.atan(orientations.get(at))));
				DoublePoint arm = direction.multiply(0.5 * Math.min(block.width, block.height));
				markers.add(LineMarker.between(center.add(arm), center.minus(arm)).stroke("red"));
			}
		}
		return new SvgImage()
			.size(blocks)
			.background(background)
			.content(markers)
			.document();
	}
}
