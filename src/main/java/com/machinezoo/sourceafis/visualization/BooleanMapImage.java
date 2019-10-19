package com.machinezoo.sourceafis.visualization;

import java.util.function.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.*;
import com.machinezoo.sourceafis.visualization.utils.*;

public class BooleanMapImage {
	public BooleanMatrix map;
	public int foreground = 0x000000;
	public int background = 0xffffff;
	public byte[] underlay;
	public BooleanMapImage() {
	}
	public BooleanMapImage(BooleanMatrix map) {
		this.map = map;
	}
	public BooleanMapImage with(Consumer<BooleanMapImage> consumer) {
		consumer.accept(this);
		return this;
	}
	public DomContent svg() {
		WritableImage image = new WritableImage(map.size()).fill(background);
		for (IntPoint at : map.size())
			if (map.get(at))
				image.set(at, foreground);
		return new DomFragment()
			.add(new EmbeddedImage()
				.size(map.size())
				.image(underlay)
				.svg())
			.add(image.svg());
	}
	public DomElement document() {
		return new SvgImage()
			.size(map.size())
			.content(svg())
			.document();
	}
}
