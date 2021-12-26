// Part of SourceAFIS Visualization: https://sourceafis.machinezoo.com/transparency/
module com.machinezoo.sourceafis.visualization {
	exports com.machinezoo.sourceafis.visualization;
	requires java.desktop;
	requires com.machinezoo.noexception;
	/*
	 * Transitive, because we process objects deserialized by the transparency lib.
	 */
	requires transitive com.machinezoo.sourceafis.transparency;
	/*
	 * Transitive for now, but we shouldn't expose any APIs that are tied to pushmode.
	 * This dependency actually shouldn't be here at all. We should use proper SVG builder.
	 */
	requires transitive com.machinezoo.pushmode;
	requires com.google.common;
	requires org.apache.commons.io;
}
