package com.machinezoo.sourceafis.visualization;

import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class DoubleMapImage {
	private final DoubleMatrix map;
	public DoubleMapImage(DoubleMatrix map) {
		this.map = map;
	}
	public WritableImage image() {
		WritableImage image = new WritableImage(map.size());
		for (int y = 0; y < map.height; ++y)
			for (int x = 0; x < map.width; ++x)
				image.set(x, y, WritableImage.gray(255 - (int)((map.get(x, y) - map.stats.getMin()) / (map.stats.getMax() - map.stats.getMin()) * 255)));
		return image;
	}
	public DomElement html() {
		return image().html();
	}
}
