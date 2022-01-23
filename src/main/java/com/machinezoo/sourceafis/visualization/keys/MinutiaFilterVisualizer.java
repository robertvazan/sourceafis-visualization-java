// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import static java.util.stream.Collectors.*;
import java.util.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.layers.*;
import com.machinezoo.sourceafis.visualization.markers.*;
import com.machinezoo.sourceafis.visualization.rendering.*;

public interface MinutiaFilterVisualizer extends TransparencyDiffVisualizer, VectorVisualizer {
	SerializedObjectKey<Template> key();
	SerializedObjectKey<Template> baseline();
	@Override
	default Set<TransparencyKey<?>> dependencies() {
		return Set.of(key(), baseline(), new InputImageKey(), new InputGrayscaleKey());
	}
	@Override
	default VectorImage visualize(TransparencyArchive archive) {
		var previous = archive.deserialize(baseline()).orElseThrow();
		var next = archive.deserialize(key()).orElseThrow();
		var buffer = new VectorBuffer(next.size())
			.background(archive);
		var preserved = Arrays.stream(next.minutiae()).map(m -> m.position()).collect(toSet());
		for (var minutia : previous.minutiae())
			if (!preserved.contains(minutia.position()))
				buffer.add(new MinutiaMarker(minutia, true));
		buffer.add(new MinutiaLayer(next));
		return buffer.render();
	}
}
