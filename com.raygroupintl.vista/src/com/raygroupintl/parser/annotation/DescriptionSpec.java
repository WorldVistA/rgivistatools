package com.raygroupintl.parser.annotation;

import com.raygroupintl.parser.TokenFactory;

public class DescriptionSpec {
	@CharSpecified(chars={','}, single=true)
	public TokenFactory comma;
	
	@CharSpecified(chars={':'}, single=true)
	public TokenFactory colon;

	@CharSpecified(chars={'('}, single=true)
	public TokenFactory lpar;
	@CharSpecified(chars={')'}, single=true)
	public TokenFactory rpar;
	
	@CharSpecified(chars={'['}, single=true)
	public TokenFactory lsqr;
	@CharSpecified(chars={']'}, single=true)
	public TokenFactory rsqr;
	
	@CharSpecified(chars={'{'}, single=true)
	public TokenFactory lcur;
	@CharSpecified(chars={'}'}, single=true)
	public TokenFactory rcur;
	
	@CharSpecified(chars={'\''}, single=true)
	public TokenFactory squote;
	@CharSpecified(excludechars={'\''}, single=true)
	public TokenFactory squoted;

	@TokenType(TSymbol.class)
	@CharSpecified(ranges={'a', 'z'})
	public TokenFactory specifiedsymbol; 

	@TokenType(TCharSymbol.class)
	@Sequence(value={"squote", "squoted", "squote"}, required="all")
	public TokenFactory charsymbol; 
	
	@Choice({"specifiedsymbol", "charsymbol"})
	public TokenFactory symbol; 
	
	@CharSpecified(chars={' '})
	public TokenFactory sp;

	@Sequence(value={"sp", "lpar", "sp"}, required="oro")
	public TokenFactory openrequired;
	@Sequence(value={"sp", "rpar", "sp"}, required="oro")
	public TokenFactory closerequired;
	
	@Sequence(value={"sp", "lsqr", "sp"}, required="oro")
	public TokenFactory openoptional;
	@Sequence(value={"sp", "rsqr", "sp"}, required="oro")
	public TokenFactory closeoptional;
	
	@Sequence(value={"sp", "lcur", "sp"}, required="oro")
	public TokenFactory openlist;
	@Sequence(value={"sp", "rcur", "sp"}, required="oro")
	public TokenFactory closelist;
	
	@Sequence(value={"sp", "comma", "sp"}, required="oro")
	public TokenFactory delimiter;
	
	@TokenType(TOptionalSymbols.class)
	@List(value="anysymbols", delim="delimiter", left="openoptional", right="closeoptional")
	public TokenFactory optionalsymbols; 
	@TokenType(TRequiredSymbols.class)
	@List(value="anysymbols", delim="delimiter", left="openrequired", right="closerequired")
	public TokenFactory requiredsymbols;
	
	@Sequence(value={"colon", "anysymbols", "colon", "anysymbols"}, required="all")
	public TokenFactory leftrightspec;
	@Sequence(value={"colon", "anysymbols", "leftrightspec"}, required="roo")
	public TokenFactory delimleftrightspec;
	@TokenType(TSymbolList.class)
	@Sequence(value={"openlist", "anysymbols", "delimleftrightspec", "closelist"}, required="rror")
	public TokenFactory list;
	
	@Choice({"symbol", "optionalsymbols", "requiredsymbols", "list"})
	public TokenFactory anysymbols;
	
	@TokenType(TDescription.class)
	@List(value="anysymbols", delim="delimiter")
	public TokenFactory description;
}