package com.machinezoo.sourceafis.visualization;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class RootPairsImage {
	private final MinutiaPair[] roots;
	private final Template probe;
	private final Template candidate;
	private final byte[] probeImage;
	private final byte[] candidateImage;
	public RootPairsImage(MinutiaPair[] roots, Template probe, Template candidate, byte[] probeImage, byte[] candidateImage) {
		this.roots = roots;
		this.probe = probe;
		this.candidate = candidate;
		this.probeImage = probeImage;
		this.candidateImage = candidateImage;
	}
	public DomElement document() {
		DoublePoint shift = new DoublePoint(probe.size.x, 0);
		DomFragment svg = new DomFragment();
		for (MinutiaPair pair : roots) {
			svg.add(LineMarker.between(probe.minutiae[pair.probe].center(), candidate.minutiae[pair.candidate].center().add(shift))
				.stroke("green")
				.strokeWidth(0.4));
		}
		for (TemplateMinutia minutia : probe.minutiae)
			svg.add(new MinutiaPositionMarker(minutia).svg());
		for (TemplateMinutia minutia : candidate.minutiae) {
			svg.add(PointMarker.circle(minutia.center().add(shift))
				.r(2.5)
				.fill("red"));
		}
		IntPoint totalSize = new IntPoint(probe.size.x + candidate.size.x, Math.max(probe.size.y, candidate.size.y));
		return new VisualizationImage()
			.size(totalSize)
			.content(new DomFragment()
				.add(new EmbeddedImage()
					.width(probe.size.x)
					.height(probe.size.y)
					.image(probeImage)
					.svg())
				.add(new EmbeddedImage()
					.width(candidate.size.x)
					.height(candidate.size.y)
					.image(candidateImage)
					.svg()
					.transform("translate(" + probe.size.x + ",0)"))
				.add(svg))
			.svg();
	}
}
