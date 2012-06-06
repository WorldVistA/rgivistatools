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

import com.raygroupintl.charlib.Predicate;
import com.raygroupintl.parser.annotation.ObjectSupply;

public class TFCharacter extends TokenFactory {
	private Predicate predicate;
	
	public TFCharacter(String name, Predicate predicate) {
		super(name);
		this.predicate = predicate;
	}
	
	@Override
	public Token tokenize(Text text, ObjectSupply objectSupply) {
		Token result = this.tokenizeRaw(text, objectSupply);
		return this.convert(result);
	}
	
	@Override
	public TString tokenizeRaw(Text text, ObjectSupply objectSupply) {
		return text.extractChar(this.predicate, objectSupply);
	}
}
