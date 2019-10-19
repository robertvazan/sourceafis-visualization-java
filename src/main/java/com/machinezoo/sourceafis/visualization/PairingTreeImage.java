package com.machinezoo.sourceafis.visualization;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class PairingTreeImage {
	private final MatchPairing pairing;
	private final MatchSide side;
	private final Template template;
	private final byte[] background;
	public PairingTreeImage(MatchPairing pairing, MatchSide side, Template template, byte[] background) {
		this.pairing = pairing;
		this.side = side;
		this.template = template;
		this.background = background;
	}
	public DomElement document() {
		DomFragment svg = new DomFragment();
		for (PairingEdge edge : pairing.support)
			svg.add(new EdgeMarker(edge, side, template).svg().stroke("yellow"));
		for (PairingEdge edge : pairing.tree)
			svg.add(new EdgeMarker(edge, side, template).svg().strokeWidth(2).stroke("green"));
		for (TemplateMinutia minutia : template.minutiae)
			svg.add(new MinutiaPositionMarker(minutia).svg());
		TemplateMinutia rootMinutia = template.minutiae[pairing.root.side(side)];
		svg.add(new MinutiaPositionMarker(rootMinutia)
			.with(m -> {
				m.radius = 3.5;
				m.color = "blue";
			})
			.svg());
		return new VisualizationImage()
			.size(template.size)
			.background(background)
			.content(svg)
			.svg();
	}
}
