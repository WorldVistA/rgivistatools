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

package com.raygroupintl.m.tool.entry.quittype;

import com.raygroupintl.m.parsetree.data.EntryId;
import com.raygroupintl.m.parsetree.data.FanoutWithLocation;
import com.raygroupintl.m.tool.entry.BlockData;

class QTBlockData extends BlockData<FanoutWithLocation> {
	private QuitType quitType = new QuitType();
	
	public QTBlockData(EntryId entryId) {
		super(entryId);
	}

	public QuitType getQuitType() {
		return this.quitType;
	}
}
