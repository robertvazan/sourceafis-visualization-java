// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.graphics;

import com.machinezoo.sourceafis.visualization.common.*;

/*
 * Image models must be separate from the generated image, because image may consist of several fields
 * while render() method should run only once. Image models could be also implemented as static methods,
 * but it is advantageous to make them all implement an interface, which cannot be done with static methods.
 * Having models as objects also allows them to define private methods that have access to all parameters.
 */
public interface ImageModel {
	TransparencyImage render();
}
