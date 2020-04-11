// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization;

import java.util.*;
import com.machinezoo.sourceafis.transparency.*;

public class TransparencyContext {
	private final Map<TransparencyRole, byte[]> images = new EnumMap<>(TransparencyRole.class);
	public TransparencyContext image(TransparencyRole role, byte[] image) {
		images.put(role, image);
		return this;
	}
	public byte[] image(TransparencyRole role) {
		return images.get(role);
	}
	private final Map<TransparencyRole, Template> templates = new EnumMap<>(TransparencyRole.class);
	public TransparencyContext template(TransparencyRole role, Template template) {
		templates.put(role, template);
		return this;
	}
	public TransparencyContext template(TransparencyRole role, byte[] template) {
		return template(role, Template.parseIO(template));
	}
	public Template template(TransparencyRole role) {
		return templates.get(role);
	}
}
