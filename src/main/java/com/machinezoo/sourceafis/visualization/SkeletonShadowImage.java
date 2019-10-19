package com.machinezoo.sourceafis.visualization;

import java.util.function.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class SkeletonShadowImage {
	private final BooleanMatrix map;
	public int color = WritableImage.red;
	public byte[] background;
	public SkeletonShadowImage with(Consumer<SkeletonShadowImage> consumer) {
		consumer.accept(this);
		return this;
	}
	public SkeletonShadowImage(BooleanMatrix map) {
		this.map = map;
	}
	public SkeletonShadowImage(SkeletonGraph skeleton) {
		this(skeleton.shadow());
	}
	public DomContent svg() {
		return new BooleanMapImage(map)
			.with(mi -> {
				mi.foreground = color;
				mi.underlay = background;
			})
			.svg();
	}
	public DomElement document() {
		return new SvgImage()
			.size(map.size())
			.content(svg())
			.document();
	}
}
