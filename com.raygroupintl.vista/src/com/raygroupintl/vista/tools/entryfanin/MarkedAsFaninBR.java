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

package com.raygroupintl.vista.tools.entryfanin;

import com.raygroupintl.m.parsetree.data.Block;
import com.raygroupintl.m.parsetree.data.Blocks;
import com.raygroupintl.m.parsetree.data.CallArgument;
import com.raygroupintl.m.parsetree.data.EntryId;
import com.raygroupintl.m.parsetree.visitor.BlockRecorder;

public class MarkedAsFaninBR extends BlockRecorder<FaninMark> {
	private EntryId entryId;
	
	public MarkedAsFaninBR(EntryId entryId) {
		this.entryId = entryId;
	}

	@Override
	protected void postUpdateFanout(EntryId fanout, CallArgument[] callArguments) {
		if (this.entryId.equals(fanout, this.getCurrentRoutineName())) {
			FaninMark b = this.getCurrentBlockAttachedObject();
			b.set(this.entryId);
		}
	}
	
	@Override
	protected Block<FaninMark> getNewBlock(int index, EntryId entryId, Blocks<FaninMark> blocks, String[] params) {
		return new Block<FaninMark>(index, entryId, blocks, new FaninMark(entryId));
	}
}
	