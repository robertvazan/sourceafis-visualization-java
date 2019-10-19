package com.machinezoo.sourceafis.visualization;

import static java.util.stream.Collectors.*;
import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class TemplateDiffImage {
	private final Template previous;
	private final Template next;
	private final byte[] background;
	public TemplateDiffImage(Template previous, Template next, byte[] background) {
		this.previous = previous;
		this.next = next;
		this.background = background;
	}
	public DomElement document() {
		DomFragment content = new DomFragment();
		Set<IntPoint> positions = Arrays.stream(next.minutiae).map(TemplateMinutia::position).collect(toSet());
		for (TemplateMinutia minutia : previous.minutiae) {
			if (!positions.contains(minutia.position())) {
				content.add(new MinutiaMarker(minutia)
					.with(m -> m.color = "red")
					.svg());
			}
		}
		content.add(new TemplateImage(next).svg());
		return new VisualizationImage()
			.size(next.size)
			.background(background)
			.content(content)
			.svg();
	}
}
