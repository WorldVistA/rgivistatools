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

package com.raygroupintl.parsergen.ruledef;

import com.raygroupintl.parser.CompositeToken;
import com.raygroupintl.parser.SequenceOfTokens;
import com.raygroupintl.parser.Token;

public class TSequence extends SequenceOfTokens implements CompositeToken {
	public TSequence(int length) {
		super(length);
	}

	public TSequence(SequenceOfTokens tokens) {
		super(tokens);
	}

	public TSequence(Token token0, Token token1) {
		super(token0, token1);
	}
	
	@Override
	public void beautify() {		
		for (Token token : this) {
			if (token != null) {
				token.beautify();
			}
		}
	}
}