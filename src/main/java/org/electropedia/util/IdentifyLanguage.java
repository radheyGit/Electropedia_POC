package org.electropedia.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.tika.language.LanguageIdentifier;

public class IdentifyLanguage {

	public static String readText(String text) {
		Charset charset = StandardCharsets.US_ASCII;
	    byte[] byteArrray = charset.encode(text).array();
		return new String(byteArrray);  
	}
}
