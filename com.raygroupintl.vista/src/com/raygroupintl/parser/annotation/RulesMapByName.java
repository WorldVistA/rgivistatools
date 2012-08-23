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

package com.raygroupintl.parser.annotation;

import java.util.Map;

public class RulesMapByName implements RulesByName {
	private Map<String, FactorySupplyRule> topRules;
	
	public RulesMapByName(Map<String, FactorySupplyRule> topRules) {
		this.topRules = topRules;
	}
	
	@Override
	public FactorySupplyRule get(String name) {
		return this.topRules.get(name);
	}
	
	@Override
	public void put(String name, FactorySupplyRule rule) {
		this.topRules.put(name, rule);
	}

	@Override
	public boolean hasRule(String name) {
		return this.topRules.get(name) != null;
	}
}
