package com.machinezoo.sourceafis.visualization;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class TemplateImage {
	private final Template template;
	private final byte[] background;
	public TemplateImage(Template template, byte[] background) {
		this.template = template;
		this.background = background;
	}
	public TemplateImage(Template template) {
		this(template, null);
	}
	public DomContent svg() {
		DomFragment svg = new DomFragment();
		svg.add(new EmbeddedImage()
			.size(template.size)
			.image(background)
			.svg());
		for (TemplateMinutia minutia : template.minutiae) {
			svg.add(new MinutiaMarker(minutia).svg());
		}
		return svg;
	}
	public DomElement document() {
		return new SvgImage()
			.size(template.size)
			.content(svg())
			.document();
	}
}
