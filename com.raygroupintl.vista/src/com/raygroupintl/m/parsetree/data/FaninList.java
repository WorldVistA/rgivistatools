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

package com.raygroupintl.m.parsetree.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.raygroupintl.struct.Indexed;

public class FaninList<T> {
	private T node;
	private List<Indexed<T>> faninNodes = new ArrayList<Indexed<T>>();
	private Set<Integer> existing = new HashSet<Integer>();
	
	public FaninList(T node) {
		this.node = node;
	}
			
	public void addFanin(T faninNode, int index) {
		int faninId = System.identityHashCode(faninNode);
		if (faninId != System.identityHashCode(this.node)) {
			if (! this.existing.contains(faninId)) {
				Indexed<T> e = new Indexed<T>(faninNode, index);
				this.faninNodes.add(e);
				this.existing.add(faninId);
			}
		}
	}
	
	public List<Indexed<T>> getFanins() {
		return this.faninNodes;
	}
}
