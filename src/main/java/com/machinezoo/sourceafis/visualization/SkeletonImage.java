package com.machinezoo.sourceafis.visualization;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class SkeletonImage {
	private final SkeletonGraph skeleton;
	private final byte[] background;
	public SkeletonImage(SkeletonGraph skeleton, byte[] background) {
		this.skeleton = skeleton;
		this.background = background;
	}
	public DomContent svg() {
		DomFragment svg = new DomFragment();
		svg.add(new SkeletonShadowImage(skeleton)
			.with(si -> si.background = background)
			.svg());
		for (SkeletonMinutia minutia : skeleton.minutiae) {
			svg.add(new HollowMinutiaMarker()
				.with(m -> m.minutia(minutia))
				.svg());
		}
		return svg;
	}
	public DomElement document() {
		return new VisualizationImage()
			.size(skeleton.size)
			.content(svg())
			.svg();
	}
}
