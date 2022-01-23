// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;

public class LegacyTransparencyGallery {
	private final TransparencyArchive archive;
	public LegacyTransparencyGallery(TransparencyArchive archive) {
		Objects.requireNonNull(archive);
		this.archive = archive;
	}
}
