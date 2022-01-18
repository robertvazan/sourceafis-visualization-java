// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.common.*;
import com.machinezoo.sourceafis.visualization.layers.*;

public class VectorBuffer {
	private final int width;
	private final int height;
	private int padding;
	private final Map<String, DomElement> definitions = new HashMap<>();
	private final DomFragment content = new DomFragment();
	public VectorBuffer(int width, int height) {
		this.width = width;
		this.height = height;
	}
	public VectorBuffer(IntPoint size) {
		this(size.x(), size.y());
	}
	public VectorBuffer padding(int padding) {
		this.padding = padding;
		return this;
	}
	public VectorBuffer add(FragmentVisualization fragment) {
		if (fragment != null) {
			definitions.putAll(fragment.definitions());
			content.add(fragment.content());
		}
		return this;
	}
	public VectorBuffer add(FragmentRenderer renderer) {
		return add(renderer != null ? renderer.render() : null);
	}
	public VectorBuffer embed(MatchSide side, TransparencyArchive archive) {
		return add(EmbeddedImageLayer.input(width, height, archive, side).orElse(null));
	}
	public VectorBuffer embed(TransparencyArchive archive) {
		return embed(null, archive);
	}
	public VectorVisualization render() {
		return new VectorData(
			width + 2 * padding,
			height + 2 * padding,
			Svg.svg()
				.viewBox((-padding) + " " + (-padding) + " " + (width + 2 * padding) + " " + (height + 2 * padding))
				.add(Svg.defs()
					.add(definitions.values().stream()
						.sorted(Comparator.comparing(e -> e.id()))))
				.add(content));
	}
}
