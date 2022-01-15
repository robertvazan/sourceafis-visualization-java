// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.*;

public class MinutiaPairs {
	public static int side(MinutiaPair pair, MatchSide side) {
		return switch (side) {
			case PROBE -> pair.probe();
			case CANDIDATE -> pair.candidate();
		};
	}
}
