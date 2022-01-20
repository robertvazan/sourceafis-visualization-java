// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
package com.machinezoo.sourceafis.visualization.utils;

import com.machinezoo.stagean.*;

@DraftCode("Use existing MIME library. Support BMP, WSQ, and JP2.")
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
		/*
		 * Fallback to wildcard if no specific MIME type can be detected.
		 */
		return "image/*";
	}
	public static String extension(String mime) {
		return switch (mime) {
			case "image/jpeg" -> ".jpeg";
			case "image/jp2" -> ".jp2";
			case "image/png" -> ".png";
			case "image/svg+xml" -> ".svg";
			case "image/tiff" -> ".tiff";
			case "image/bmp" -> ".bmp";
			/*
			 * WSQ doesn't have a MIME type. We will invent one.
			 */
			case "image/x-wsq" -> ".wsq";
			/*
			 * No extension for unknown formats. This is safe. We don't want null.
			 */
			default -> "";
		};
	}
}
