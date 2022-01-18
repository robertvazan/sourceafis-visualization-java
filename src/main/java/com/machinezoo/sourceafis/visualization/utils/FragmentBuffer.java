// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import java.util.*;
import com.machinezoo.pushmode.dom.*;

public class FragmentBuffer {
	private final Map<String, DomElement> definitions = new HashMap<>();
	private final DomFragment content = new DomFragment();
	public FragmentBuffer add(FragmentVisualization fragment) {
		definitions.putAll(fragment.definitions());
		content.add(fragment.content());
		return this;
	}
	public FragmentBuffer add(FragmentRenderer renderer) {
		return add(renderer.render());
	}
	public FragmentBuffer add(DomContent content) {
		this.content.add(content);
		return this;
	}
	public FragmentVisualization render() {
		return new FragmentData(definitions, content);
	}
}
