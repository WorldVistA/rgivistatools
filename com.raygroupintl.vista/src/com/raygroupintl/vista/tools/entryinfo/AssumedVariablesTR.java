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

package com.raygroupintl.vista.tools.entryinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.raygroupintl.m.parsetree.data.EntryId;
import com.raygroupintl.output.Terminal;
import com.raygroupintl.output.TerminalFormatter;
import com.raygroupintl.vista.tools.fnds.ToolResult;

public class AssumedVariablesTR implements ToolResult  {
	private EntryId entryId;
	private Set<String> assumedVariables;
	private AssumedVarsToolFlag flags;

	public AssumedVariablesTR(EntryId entryId, Set<String> assumedVariables, AssumedVarsToolFlag flags) {
		this.entryId = entryId;
		this.assumedVariables = assumedVariables;
		this.flags = flags;
	}
	
	public Set<String> getData() {
		return this.assumedVariables;
	}
	
	public boolean isValid() {
		return this.assumedVariables != null;
	}
	
	private boolean hasAssumedVariables() {
		return (this.assumedVariables != null) && (this.assumedVariables.size() > 0);
	}
	
	public void writeVariables(Terminal t, TerminalFormatter tf) {
		List<String> assumedLocalsSorted = new ArrayList<String>(this.assumedVariables);
		Collections.sort(assumedLocalsSorted);			
		t.writeFormatted("ASSUMED", assumedLocalsSorted, tf);		
	}
	
	@Override
	public void write(Terminal t, TerminalFormatter tf) {
		if (this.assumedVariables == null) {
			t.writeEOL(" " + this.entryId.toString2());		
			t.writeEOL("  ERROR: Invalid entry point");
			return;
		} else {
			if (this.flags.getIgnoreEntryWithNoAssumedVariables() && ! this.hasAssumedVariables()) {
				return;
			}
			t.writeEOL(" " + this.entryId.toString2());		
			this.writeVariables(t, tf);
		}
	}	
}
