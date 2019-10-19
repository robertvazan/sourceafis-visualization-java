package com.machinezoo.sourceafis.visualization;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class MaskImage {
	private final BooleanMatrix mask;
	private final BlockMap blocks;
	private final byte[] background;
	public MaskImage(BooleanMatrix mask, BlockMap blocks, byte[] background) {
		this.mask = mask;
		this.blocks = blocks;
		this.background = background;
	}
	public MaskImage(BooleanMatrix mask, byte[] background) {
		this(mask, null, background);
	}
	public DomElement document() {
		return new BooleanMapImage(blocks == null ? mask : mask.expand(blocks))
			.with(mi -> {
				mi.foreground = WritableImage.color(0xff, 0xff, 0, 0x20);
				mi.background = WritableImage.color(0, 0xff, 0xff, 0x20);
				mi.underlay = background;
			})
			.document();
	}
}
