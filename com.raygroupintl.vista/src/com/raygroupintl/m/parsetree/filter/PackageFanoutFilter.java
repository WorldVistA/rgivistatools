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

package com.raygroupintl.m.parsetree.filter;

import com.raygroupintl.m.parsetree.Fanout;
import com.raygroupintl.struct.Filter;
import com.raygroupintl.vista.repository.RepositoryInfo;

public class PackageFanoutFilter  implements Filter<Fanout> {
	private RepositoryInfo.PackageInRepository routinePackage;
	
	public PackageFanoutFilter(RepositoryInfo.PackageInRepository routinePackage) {
		this.routinePackage = routinePackage;
	}
	
	@Override
	public boolean isValid(Fanout input) {
		if (input != null) {
			String routineName = input.getRoutineName();
			return ! this.routinePackage.contains(routineName);
		}
		return false;
	}	
}