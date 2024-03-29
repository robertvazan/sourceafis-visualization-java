// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.rendering;

import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.layers.*;
import com.machinezoo.stagean.*;

@DraftApi("Support CSS.")
public class VectorBuffer implements VectorModel {
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
	public VectorBuffer add(ImageLayer layer) {
		if (layer != null) {
			definitions.putAll(layer.definitions());
			content.add(layer.content());
		}
		return this;
	}
	public VectorBuffer add(LayerModel model) {
		return add(model != null ? model.render() : null);
	}
	public VectorBuffer background(TransparencyArchive archive) {
		return add(BackgroundImageLayer.deserialize(width, height, archive, null).orElse(null));
	}
	public VectorBuffer embed(TransparencyImage image) {
		return add(new EmbeddedImageLayer(image));
	}
	public VectorBuffer embed(ImageModel model) {
		return embed(model.render());
	}
	@Override
	public VectorImage render() {
		return new VectorFrame(
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
