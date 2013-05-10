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

package com.raygroupintl.m.tool.entry.fanout;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.raygroupintl.m.parsetree.data.EntryId;

public class EntryFanouts implements Serializable {
	private static final long serialVersionUID = 1L;

	private SortedSet<EntryId> fanoutEntries;

	public void add(EntryId fanout) {
		if (this.fanoutEntries == null) {
			this.fanoutEntries = new TreeSet<EntryId>();
		} 
		this.fanoutEntries.add(fanout);
	}
	
	public Set<EntryId> getFanouts() {
		return this.fanoutEntries;
	}
}
