// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.formats;

import java.lang.annotation.*;

/*
 * ARGB format. Every component in range 00 to FF. Higher values mean brighter colors. Higher alpha means higher opacity.
 */
@Target({ ElementType.TYPE_USE })
public @interface IntColor {
}
