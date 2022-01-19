// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.graphics;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.visualization.common.*;

public record VectorFrame(int width, int height, DomElement tree) implements VectorImage {
	public VectorFrame {
		Validate.isTrue(width > 0 && height > 0);
		Objects.requireNonNull(tree);
	}
}
