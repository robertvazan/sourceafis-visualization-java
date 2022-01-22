// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import static java.util.stream.Collectors.*;
import java.util.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.images.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.rendering.*;
import com.machinezoo.sourceafis.visualization.types.*;

public interface SkeletonFilterVisualizer extends TransparencyDiffVisualizer, VectorVisualizer {
	SkeletonType skeleton();
	SkeletonKey<SkeletonGraph> key();
	SkeletonKey<SkeletonGraph> baseline();
	@Override
	default VectorImage visualize(TransparencyArchive archive) {
		var previous = archive.deserialize(baseline()).orElseThrow();;
		var next = archive.deserialize(key()).orElseThrow();;
		var buffer = new VectorBuffer(next.width(), next.height())
			.background(archive)
			.embed(new BinaryDiffImage(SkeletonGraphs.shadow(previous), SkeletonGraphs.shadow(next)));
		var pminutiae = Arrays.stream(previous.minutiae()).collect(toSet());
		var nminutiae = Arrays.stream(next.minutiae()).collect(toSet());
		for (var minutia : previous.minutiae())
			if (!nminutiae.contains(minutia))
				buffer.add(new SkeletonMinutiaMarker(minutia, SkeletonMinutiaMarkerType.REMOVED));
		var degrees = SkeletonGraphs.degrees(next);
		for (var minutia : next.minutiae()) {
			if (!pminutiae.contains(minutia))
				buffer.add(new SkeletonMinutiaMarker(minutia, SkeletonMinutiaMarkerType.ADDED));
			else
				buffer.add(new SkeletonMinutiaMarker(minutia, SkeletonMinutiaMarkerType.classify(minutia, degrees)));
		}
		return buffer.render();
	}
}
