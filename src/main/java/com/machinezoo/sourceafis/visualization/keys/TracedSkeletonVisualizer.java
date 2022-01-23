// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.images.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.rendering.*;
import com.machinezoo.sourceafis.visualization.types.*;

public record TracedSkeletonVisualizer(SkeletonType skeleton) implements VectorVisualizer {
	@Override
	public TracedSkeletonKey key() {
		return new TracedSkeletonKey(skeleton);
	}
	@Override
	public Set<TransparencyKey<?>> dependencies() {
		return Set.of(key(), new InputImageKey(), new InputGrayscaleKey());
	}
	@Override
	public VectorImage visualize(TransparencyArchive archive) {
		var skeleton = archive.deserialize(key()).orElseThrow();;
		var buffer = new VectorBuffer(skeleton.width(), skeleton.height())
			.background(archive)
			.embed(new SkeletonLayerImage(SkeletonGraphs.shadow(skeleton)));
		var degrees = SkeletonGraphs.degrees(skeleton);
		for (var minutia : skeleton.minutiae())
			buffer.add(new SkeletonMinutiaMarker(minutia, SkeletonMinutiaMarkerType.classify(minutia, degrees)));
		return buffer.render();
	}
}
