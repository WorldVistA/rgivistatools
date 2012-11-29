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

package com.raygroupintl.struct;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class SetValuedMap<K, V> {
	private Map<K, Set<V>> map = new HashMap<K, Set<V>>();
	
	public void put(K key, V value) {
		Set<V> set = this.map.get(key);
		if (set == null) {
			set = new TreeSet<V>();
			this.map.put(key, set);
		}
		set.add(value);
	}

	public Set<V> get(K key) {
		Set<V> set = this.map.get(key);
		if (set == null) {
			return null;
		} else {
			return Collections.unmodifiableSet(set);
		}
	}
}
