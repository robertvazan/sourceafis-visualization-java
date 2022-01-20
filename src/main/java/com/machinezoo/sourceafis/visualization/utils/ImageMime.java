// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import com.machinezoo.stagean.*;

@DraftCode("Use existing MIME library.")
public class ImageMime {
	public static String detect(byte[] image) {
		if (image[1] == 'P' && image[2] == 'N' && image[3] == 'G')
			return "image/png";
		if (image[0] == (byte)0xff && image[1] == (byte)0xd8)
			return "image/jpeg";
		if (image[0] == (byte)0x49 && image[1] == (byte)0x49 && image[2] == (byte)0x2a)
			return "image/tiff";
		if (image[0] == (byte)0x4d && image[1] == (byte)0x4d && image[2] == (byte)0x2a)
			return "image/tiff";
		if (image[0] == '<')
			return "image/svg+xml";
		return null;
	}
}
