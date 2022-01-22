// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.keys;

import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.transparency.keys.*;
import com.machinezoo.sourceafis.visualization.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.images.*;

public record FilteredBinaryImageVisualizer() implements TransparencyDiffVisualizer, PaletteVisualizer<DiffPalette> {
	@Override
	public FilteredBinaryImageKey key() {
		return new FilteredBinaryImageKey();
	}
	@Override
	public BinarizedImageKey baseline() {
		return new BinarizedImageKey();
	}
	@Override
	public Class<DiffPalette> palette() {
		return DiffPalette.class;
	}
	@Override
	public PaletteImage<DiffPalette> visualize(TransparencyArchive archive) {
		return new BinaryDiffImage(archive.deserialize(baseline()).orElseThrow(), archive.deserialize(key()).orElseThrow()).render();
	}
}
