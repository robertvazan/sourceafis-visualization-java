// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.graphics;

import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.stagean.*;

@DraftApi("Support CSS.")
public interface ImageLayer {
	DomContent content();
	default DomElement subtree() {
		var content = content();
		if (content instanceof DomElement element)
			return element;
		return Svg.g().add(content);
	}
	default Map<String, DomElement> definitions() {
		return Collections.emptyMap();
	}
}
