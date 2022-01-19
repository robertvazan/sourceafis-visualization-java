// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.graphics;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.pushmode.dom.*;

public record LayerFrame(Map<String, DomElement> definitions, DomContent content) implements ImageLayer {
	public LayerFrame {
		Objects.requireNonNull(definitions);
		for (var id : definitions.keySet())
			Validate.isTrue(definitions.get(id).id().equals(id));
	}
	public LayerFrame(DomContent content) {
		this(Collections.emptyMap(), content);
	}
}
