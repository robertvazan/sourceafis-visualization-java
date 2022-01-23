// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.rendering;

import java.util.*;
import com.machinezoo.pushmode.dom.*;
import com.machinezoo.sourceafis.transparency.types.*;
import com.machinezoo.sourceafis.visualization.*;
import com.machinezoo.sourceafis.visualization.formats.*;
import com.machinezoo.sourceafis.visualization.layers.*;
import com.machinezoo.sourceafis.visualization.svg.*;
import com.machinezoo.stagean.*;

@DraftApi("Support CSS.")
public class SplitBuffer implements VectorModel {
	private final int width;
	private final int height;
	private final int split;
	private final Map<String, DomElement> definitions = new HashMap<>();
	private final DomFragment content = new DomFragment();
	private DomContainer sink = content;
	public SplitBuffer(int width1, int height1, int width2, int height2) {
		width = width1 + width2;
		height = Math.max(height1, height2);
		split = width1;
	}
	public SplitBuffer(IntPoint size1, IntPoint size2) {
		this(size1.x(), size1.y(), size2.x(), size2.y());
	}
	public double leftX(double x) {
		return x;
	}
	public double leftY(double y) {
		return y;
	}
	public DoublePoint left(DoublePoint at) {
		return at;
	}
	public double rightX(double x) {
		return split + x;
	}
	public double rightY(double y) {
		return y;
	}
	public DoublePoint right(DoublePoint at) {
		return new DoublePoint(rightX(at.x()), rightY(at.y()));
	}
	public SplitBuffer select(MatchSide side) {
		if (side == MatchSide.CANDIDATE) {
			if (sink == content) {
				sink = Svg.g()
					.transform(new SvgTransform()
						.translate(split, 0)
						.toString());
				content.add(sink);
			}
		} else
			sink = content;
		return this;
	}
	public SplitBuffer add(ImageLayer layer) {
		if (layer != null) {
			definitions.putAll(layer.definitions());
			sink.add(layer.content());
		}
		return this;
	}
	public SplitBuffer add(LayerModel model) {
		return add(model != null ? model.render() : null);
	}
	public SplitBuffer add(DomContent content) {
		sink.add(content);
		return this;
	}
	public SplitBuffer embed(TransparencyImage image) {
		return add(new EmbeddedImageLayer(image));
	}
	public SplitBuffer embed(ImageModel model) {
		return embed(model.render());
	}
	@Override
	public VectorImage render() {
		return new VectorFrame(
			width,
			height,
			Svg.svg()
				.viewBox("0 0 " + width + " " + height)
				.add(Svg.defs()
					.add(definitions.values().stream()
						.sorted(Comparator.comparing(e -> e.id()))))
				.add(content));
	}
}
