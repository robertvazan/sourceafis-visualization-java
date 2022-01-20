// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.formats;

import java.lang.annotation.*;

/*
 * Range 00 to FF. Higher values mean brighter colors.
 */
@Target({ ElementType.TYPE_USE })
public @interface ByteColor {
}
