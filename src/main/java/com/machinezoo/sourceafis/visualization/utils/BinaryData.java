// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.sourceafis.visualization.common.*;

public record BinaryData(int width, int height, BitSet bits) implements BinaryVisualization {
	public BinaryData {
		Validate.isTrue(width > 0 && height > 0);
		Objects.requireNonNull(bits);
	}
}
