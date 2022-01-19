// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.graphics;

import java.util.*;
import org.apache.commons.lang3.*;
import com.machinezoo.sourceafis.visualization.common.*;

public record BinaryFrame(int width, int height, BitSet bits) implements BinaryImage {
	public BinaryFrame {
		Validate.isTrue(width > 0 && height > 0);
		Objects.requireNonNull(bits);
	}
}
