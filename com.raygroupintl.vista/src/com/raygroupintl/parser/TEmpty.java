//---------------------------------------------------------------------------
// Copyright 2012 Ray Group International
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//---------------------------------------------------------------------------

package com.raygroupintl.parser;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TEmpty implements Token, TokenStore {
	@Override
	public void addToken(Token token) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public StringPiece toValue() {
		return new StringPiece();
	}

	@Override
	public List<Token> toList() {
		return Collections.emptyList();
	}

	@Override
	public void setLength(int length) {		
	}

	@Override
	public void resetIndex(int index) {		
	}
	
	@Override
	public boolean isAllNull() {
		return false;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean hasToken() {
		return false;
	}
	
	@Override
	public Token get(int index) {
		throw new ArrayIndexOutOfBoundsException();
	}
	
	public void set(int index, Token token) {
		throw new UnsupportedOperationException();
	}	
	
	@Override
	public Iterator<Token> iterator() {
		return null;
	}
		
	@Override
	public void beautify() {
	}
}
