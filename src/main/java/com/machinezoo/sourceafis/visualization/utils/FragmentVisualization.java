// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import java.util.*;
import com.machinezoo.pushmode.dom.*;

public interface FragmentVisualization {
	DomContent content();
	default DomElement element() {
		var content = content();
		if (content instanceof DomElement element)
			return element;
		return Svg.g().add(content);
	}
	default Map<String, DomElement> definitions() {
		return Collections.emptyMap();
	}
}
