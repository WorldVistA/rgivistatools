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

public class TFEmpty extends TokenFactory {
	private TokenFactory expected;
	
	public TFEmpty(String name) {		
		super(name);
	}
	
	public TFEmpty(String name, TokenFactory expected) {
		super(name);
		this.expected = expected;
	}
	
	@Override
	public Token tokenize(Text text) throws SyntaxErrorException {
		if (text.onChar()) {
			if (this.expected == null) {
				return new TEmpty();
			} else {
				Text textCopy = text.getCopy();
				Token t = this.expected.tokenize(textCopy);
				if (t != null)  {
					return new TEmpty();
				}
			}
		}
		return null;
	}
}