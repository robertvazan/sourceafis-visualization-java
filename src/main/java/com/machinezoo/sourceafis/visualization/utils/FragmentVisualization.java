// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import com.machinezoo.pushmode.dom.*;

public interface FragmentVisualization {
	DomContent fragment();
	default DomElement element() {
		var fragment = fragment();
		if (fragment instanceof DomElement element)
			return element;
		return Svg.g().add(fragment);
	}
	default DomContent definitions() {
		return null;
	}
}
