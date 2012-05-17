package com.raygroupintl.parser;

import java.util.List;

public abstract class TFSequence extends TokenFactory {
	public enum ValidateResult {
		CONTINUE, BREAK, NULL_RESULT
	}

	private static final SequenceAdapter DEFAULT_ADAPTER = new SequenceAdapter() {		
		@Override
		public Token convert(List<Token> tokens) {
			return new TSequence(tokens);
		}
	}; 
		
	private SequenceAdapter adapter;

	public TFSequence(String name) {
		this(name, DEFAULT_ADAPTER);
	}

	public TFSequence(String name, SequenceAdapter adapter) {
		super(name);
		this.adapter = adapter == null ? DEFAULT_ADAPTER : adapter;
	}

	protected abstract TokenFactory getTokenFactory(int i, TokenStore foundTokens) throws SyntaxErrorException;

	protected abstract int getExpectedTokenCount();

	protected abstract ValidateResult validateNull(int seqIndex, TokenStore foundTokens) throws SyntaxErrorException;

	protected ValidateResult validateNext(int seqIndex, TokenStore foundTokens, Token nextToken) throws SyntaxErrorException {
		return ValidateResult.CONTINUE;
	}

	protected void validateEnd(int seqIndex, TokenStore foundTokens) throws SyntaxErrorException {		
	}

	protected Token getToken(TokenStore foundTokens) {
		if (this.adapter == null) {
			return new TSequence(foundTokens.toList());
		} else {
			return this.adapter.convert(foundTokens.toList());
		}
	}

	private ValidateResult validate(int seqIndex,TokenStore foundTokens, Token nextToken) throws SyntaxErrorException {
		if (nextToken == null) {
			return this.validateNull(seqIndex, foundTokens);
		} else {
			return this.validateNext(seqIndex, foundTokens, nextToken);			
		}
	}

	@Override
	public Token tokenize(Text text) throws SyntaxErrorException {
		if (text.onChar()) {
			int factoryCount = this.getExpectedTokenCount();
			TokenStore foundTokens = new ArrayAsTokenStore(factoryCount);
			for (int i=0; i<factoryCount; ++i) {
				TokenFactory factory = this.getTokenFactory(i, foundTokens);
				Token token = null;
				try {
					token = factory.tokenize(text);				
				} catch (SyntaxErrorException e) {
					e.addStore(foundTokens);
					throw e;
				}
					
				ValidateResult vr = this.validate(i, foundTokens, token);
				if (vr == ValidateResult.BREAK) break;
				if (vr == ValidateResult.NULL_RESULT) return null;
	
				foundTokens.addToken(token);
				if (token != null) {				
					if (text.onEndOfText() && (i < factoryCount-1)) {
						this.validateEnd(i, foundTokens);
						break;
					}
				}
			}
			return this.getToken(foundTokens);
		}		
		return null;
	}
}