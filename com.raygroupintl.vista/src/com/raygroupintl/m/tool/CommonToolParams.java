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

package com.raygroupintl.m.tool;

import com.raygroupintl.m.parsetree.data.Block;
import com.raygroupintl.m.parsetree.data.BlocksSupply;
import com.raygroupintl.m.parsetree.data.EntryId;
import com.raygroupintl.m.parsetree.visitor.BlockRecorderFactory;
import com.raygroupintl.struct.FilterFactory;

public class CommonToolParams {
	private ParseTreeSupply parseTreeSupply;
	private RecursionSpecification recursionSpec = new RecursionSpecification();

	public CommonToolParams(ParseTreeSupply parseTreeSupply) {
		this.parseTreeSupply = parseTreeSupply;		
	}
	
	public void setRecursionSpecification(RecursionSpecification recursionSpec) {
		this.recursionSpec = recursionSpec;
	}
	
	public RecursionSpecification getRecursionSpecification() {
		return this.recursionSpec;
	}

	public FilterFactory<EntryId, EntryId> getFanoutFilterFactory() {
		return this.recursionSpec.getFanoutFilterFactory();
	}
	
	public <T> BlocksSupply<Block<T>> getBlocksSupply(BlockRecorderFactory<T> f) {
		return new AccumulatingBlocksSupply<T>(this.parseTreeSupply, f);
	}
}
