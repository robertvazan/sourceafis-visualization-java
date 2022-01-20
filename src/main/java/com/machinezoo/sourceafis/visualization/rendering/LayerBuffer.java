// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.rendering;

import java.util.*;
import com.machinezoo.pushmode.dom.*;

/*
 * This class inevitably duplicates some functionality from VectorBuffer.
 * There are lots of ways to unify the code, but they all create a lot of complexity without much reduction in code size.
 * 
 * Some layers have inherent size, which could be recorded here, but that information would never get used,
 * because VectorBuffer has predefined size that cannot be changed by adding layers.
 */
public class LayerBuffer implements LayerModel {
	private final Map<String, DomElement> definitions = new HashMap<>();
	private final DomFragment content = new DomFragment();
	public LayerBuffer add(ImageLayer layer) {
		if (layer != null) {
			definitions.putAll(layer.definitions());
			content.add(layer.content());
		}
		return this;
	}
	public LayerBuffer add(LayerModel model) {
		return add(model != null ? model.render() : null);
	}
	public LayerBuffer add(DomContent content) {
		this.content.add(content);
		return this;
	}
	@Override
	public ImageLayer render() {
		return new LayerFrame(definitions, content);
	}
}
