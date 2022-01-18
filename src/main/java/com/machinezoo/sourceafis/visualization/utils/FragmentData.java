// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.pushmode.dom.*;

public record FragmentData(Map<String, DomElement> definitions, DomContent content) implements FragmentVisualization {
	public FragmentData {
		Objects.requireNonNull(definitions);
		for (var id : definitions.keySet())
			Validate.isTrue(definitions.get(id).id().equals(id));
	}
	public FragmentData(DomContent content) {
		this(Collections.emptyMap(), content);
	}
}
