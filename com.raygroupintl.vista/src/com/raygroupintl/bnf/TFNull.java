package com.raygroupintl.bnf;


public class TFNull extends TokenFactory {
	@Override
	public Token tokenize(String line, int fromIndex) {
		return null;
	}
}
