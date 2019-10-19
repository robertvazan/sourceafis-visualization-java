package com.machinezoo.sourceafis.visualization;

import static java.util.stream.Collectors.*;
import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class SkeletonDiffImage {
	private final SkeletonGraph previous;
	private final SkeletonGraph next;
	public SkeletonDiffImage(SkeletonGraph previous, SkeletonGraph next) {
		this.previous = previous;
		this.next = next;
	}
	public DomElement document() {
		Set<IntPoint> previousMinutiae = previous.minutiae.stream().map(SkeletonMinutia::position).collect(toSet());
		Set<IntPoint> currentMinutiae = next.minutiae.stream().map(SkeletonMinutia::position).collect(toSet());
		DomFragment svg = new DomFragment();
		for (SkeletonMinutia minutia : previous.minutiae) {
			if (!currentMinutiae.contains(minutia.position())) {
				svg.add(new HollowMinutiaMarker()
					.with(m -> {
						m.minutia(minutia);
						m.color = "red";
					})
					.svg());
			}
		}
		for (SkeletonMinutia minutia : next.minutiae) {
			if (!previousMinutiae.contains(minutia.position())) {
				svg.add(new HollowMinutiaMarker()
					.with(m -> {
						m.minutia(minutia);
						m.color = "green";
					})
					.svg());
			} else {
				svg.add(new HollowMinutiaMarker()
					.with(m -> m.minutia(minutia))
					.svg());
			}
		}
		return new SvgImage()
			.size(next.size)
			.background(new BinaryDiffImage(previous.shadow(), next.shadow())
				.image()
				.png())
			.content(svg)
			.document();
	}
}
