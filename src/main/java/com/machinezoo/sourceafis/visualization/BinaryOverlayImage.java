package com.machinezoo.sourceafis.visualization;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class BinaryOverlayImage {
	private final BooleanMatrix binary;
	private final byte[] background;
	public BinaryOverlayImage(BooleanMatrix binary, byte[] background) {
		this.binary = binary;
		this.background = background;
	}
	public BinaryOverlayImage(BooleanMatrix binary) {
		this(binary, null);
	}
	public DomElement document() {
		return new BooleanMapImage(binary)
			.with(mi -> {
				mi.foreground = WritableImage.color(0, 0xff, 0xff, 0x90);
				mi.underlay = background;
			})
			.document();
	}
}
