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

import com.raygroupintl.parser.SequenceOfTokens;
import com.raygroupintl.parser.Token;

public class TConstSymbol extends TSequence implements ConstSymbol {
	public TConstSymbol(int length) {
		super(length);
	}
	
	public TConstSymbol(SequenceOfTokens tokens) {
		super(tokens);
	}
	
	@Override
	public String getValue() {
		return this.getToken(1).toValue().toString();
	}
	
	@Override
	public boolean getIgnoreCaseFlag() {
		Token t = this.getToken(4);
		return (t != null) && (t.toValue().toString().equals("1"));		
	}
	
	@Override
	public void accept(RuleDefinitionVisitor visitor, String name, RuleSupplyFlag flag) {
		visitor.visitConstSymbol(this, name, flag);
	}
}