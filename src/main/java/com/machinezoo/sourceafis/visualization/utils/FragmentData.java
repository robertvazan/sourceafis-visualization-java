// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import com.machinezoo.pushmode.dom.*;

public record FragmentData(DomContent definitions, DomContent fragment) implements FragmentVisualization {
	public FragmentData(DomContent fragment) {
		this(null, fragment);
	}
}
