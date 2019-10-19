// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class BinaryDiffImage {
	private BooleanMatrix previous;
	public BinaryDiffImage previous(BooleanMatrix previous) {
		this.previous = previous;
		return this;
	}
	private BooleanMatrix next;
	public BinaryDiffImage next(BooleanMatrix next) {
		this.next = next;
		return this;
	}
	public WritableImage image() {
		WritableImage image = new WritableImage(next.size());
		for (int y = 0; y < next.height; ++y)
			for (int x = 0; x < next.width; ++x) {
				boolean original = previous.get(x, y);
				boolean updated = next.get(x, y);
				if (updated)
					image.set(x, y, original ? WritableImage.black : WritableImage.green);
				else
					image.set(x, y, original ? WritableImage.red : WritableImage.white);
			}
		return image;
	}
	public DomElement html() {
		return image().html();
	}
}
