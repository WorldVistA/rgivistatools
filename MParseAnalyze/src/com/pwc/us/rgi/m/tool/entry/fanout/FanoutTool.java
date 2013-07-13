//---------------------------------------------------------------------------
// Copyright 2013 PwC
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

package com.pwc.us.rgi.m.tool.entry.fanout;

import java.util.List;
import java.util.Set;

import com.pwc.us.rgi.m.parsetree.data.EntryId;
import com.pwc.us.rgi.m.parsetree.data.Fanout;
import com.pwc.us.rgi.m.parsetree.data.FanoutBlocks;
import com.pwc.us.rgi.m.parsetree.visitor.BlockRecorderFactory;
import com.pwc.us.rgi.m.tool.CommonToolParams;
import com.pwc.us.rgi.m.tool.entry.Block;
import com.pwc.us.rgi.m.tool.entry.BlockData;
import com.pwc.us.rgi.m.tool.entry.MEntryTool;
import com.pwc.us.rgi.struct.Filter;

public class FanoutTool extends MEntryTool<EntryFanouts, Fanout, BlockData<Fanout>>{
	private static class EntryFanoutRecorderFactory implements BlockRecorderFactory<Fanout, BlockData<Fanout>> {
		@Override
		public VoidBlockRecorder getRecorder() {
			return new VoidBlockRecorder();
		}
	}

	public FanoutTool(CommonToolParams params) {
		super(params);
	}
	
	@Override
	protected EntryFanoutRecorderFactory getBlockRecorderFactory() {
		return new EntryFanoutRecorderFactory();
	}

	@Override
	protected EntryFanouts getResult(Block<Fanout, BlockData<Fanout>> block, Filter<Fanout> filter, Set<EntryId> missingEntryIds) {
		FanoutBlocks<Fanout, BlockData<Fanout>> fanoutBlocks = block.getFanoutBlocks(this.blocksSupply, filter, missingEntryIds);
		List<Block<Fanout, BlockData<Fanout>>> blocks = fanoutBlocks.getBlocks();
		boolean first = true;
		EntryFanouts result = new EntryFanouts();
		for (Block<Fanout, BlockData<Fanout>> b : blocks) {
			if (first) {
				first = false;
			} else if (! b.isInternal()) {					
				result.add(b.getEntryId());
			}
		}
		return result;
	}
}
