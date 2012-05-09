package com.raygroupintl.m.token;

import com.raygroupintl.bnf.TCharacters;

public class TStringLiteral extends TCharacters {
	public TStringLiteral(String value) {
		super(value);
	}
	
	@Override
	public String getStringValue() {
		return '"' + super.getStringValue().replaceAll("\"", "\"\"") + '"';
	}

	@Override
	public int getStringSize() {
		String value = super.getStringValue();
		int quoteCount = 0;
		for (int i=0; i<value.length(); ++i) if (value.charAt(i) == '"') ++quoteCount;
		return 2 + quoteCount + super.getStringSize();
	}
}
