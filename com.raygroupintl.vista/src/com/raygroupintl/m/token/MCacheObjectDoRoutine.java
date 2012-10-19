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

package com.raygroupintl.m.token;

import com.raygroupintl.m.parsetree.CacheObjectDoRoutine;
import com.raygroupintl.m.parsetree.Node;
import com.raygroupintl.parser.SequenceOfTokens;

public class MCacheObjectDoRoutine extends MSequence {
	public MCacheObjectDoRoutine(int length) {
		super(length);
	}
	
	public MCacheObjectDoRoutine(SequenceOfTokens tokens) {
		super(tokens);
	}
	
	@Override
	public Node getNode() {
		Node additionalNodes = super.getNode();
		CacheObjectDoRoutine result = new CacheObjectDoRoutine(additionalNodes);
		return result;
	}
}