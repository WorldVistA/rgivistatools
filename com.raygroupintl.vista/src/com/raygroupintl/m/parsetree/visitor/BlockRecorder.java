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

package com.raygroupintl.m.parsetree.visitor;

import java.util.HashSet;
import java.util.Set;

import com.raygroupintl.m.parsetree.DoBlock;
import com.raygroupintl.m.parsetree.ElseCmd;
import com.raygroupintl.m.parsetree.Entry;
import com.raygroupintl.m.parsetree.ForLoop;
import com.raygroupintl.m.parsetree.IfCmd;
import com.raygroupintl.m.parsetree.QuitCmd;
import com.raygroupintl.m.parsetree.Routine;
import com.raygroupintl.m.parsetree.data.Block;
import com.raygroupintl.m.parsetree.data.BlockWithAPIData;
import com.raygroupintl.m.parsetree.data.Blocks;
import com.raygroupintl.m.parsetree.data.EntryId;

public class BlockRecorder extends FanoutRecorder {
	private Blocks currentBlocks;
	
	private BlockWithAPIData currentBlock;
	private String currentRoutineName;
	private int inDoBlock;
	
	private int index;
	
	private int underCondition;
	private int underFor;
	
	private Set<Integer> doBlockHash = new HashSet<Integer>();

	protected BlockWithAPIData getCurrentBlock() {
		return this.currentBlock;
	}
	
	protected String getCurrentRoutineName() {
		return this.currentRoutineName;
	}
	
	protected int incrementIndex() {
		++this.index;
		return this.index;
	}
	
	protected void reset() {
		this.currentBlocks = null;
		this.currentBlock = null;
		this.currentRoutineName = null;
		this.index = 0;		
		this.inDoBlock = 0;
		this.underCondition = 0;
		this.underFor = 0;
	}
	
	protected void updateFanout(EntryId fanout) {		
	}
	
	protected void updateFanout(boolean isGoto, boolean conditional) {
		EntryId fanout = this.getLastFanout();
		boolean shouldClose = isGoto && (! conditional) && (this.underCondition < 1);
		if (fanout != null) {
			this.updateFanout(fanout);
		} 
		if (shouldClose) {
			this.currentBlock.close();
		}
	}

	protected void visitQuit(QuitCmd quitCmd) {
		quitCmd.acceptSubNodes(this);
		boolean quitConditional = (quitCmd.getPostCondition() != null);
		if ((! quitConditional) && (this.underFor < 1) && (this.underCondition < 1)) {
			this.currentBlock.close();			
		}
	}

	protected void visitForLoop(ForLoop forLoop) {
		++this.underFor;
		super.visitForLoop(forLoop);
		--this.underFor;
	}
	
	protected void visitIf(IfCmd ifCmd) {
		++this.underCondition;
		super.visitIf(ifCmd);
		--this.underCondition;
	}
	
	protected void visitElse(ElseCmd elseCmd) {
		++this.underCondition;
		super.visitElse(elseCmd);
		--this.underCondition;
	}

	private EntryId getEntryId(String tag) {
		if ((tag == null) || tag.isEmpty()) {
			if (this.inDoBlock > 0) {
				return new EntryId(this.currentRoutineName, ":" + String.valueOf(this.index));
			} else {
				return new EntryId(this.currentRoutineName, this.currentRoutineName);
			}
		} else {
			return new EntryId(this.currentRoutineName, tag);
		}
	}
	
	@Override
	protected void visitEntry(Entry entry) {
		Block lastBlock = this.currentBlock;
		++this.index;
		String tag = entry.getName();
		EntryId entryId = this.getEntryId(tag);		
		this.currentBlock = new BlockWithAPIData(this.index, entryId, this.currentBlocks);
		String[] params = entry.getParameters();
		this.currentBlock.getData().setFormals(params);
		if (lastBlock == null) {
			this.currentBlocks.setFirst(this.currentBlock);
			if ((tag != null) && (! tag.isEmpty())) {
				this.currentBlocks.put(tag, this.currentBlock);
			}
		} else {
			if ((tag == null) || tag.isEmpty()) {
				tag = ":" + String.valueOf(this.index);
			}
			this.currentBlocks.put(tag, this.currentBlock);
			EntryId defaultGoto = new EntryId(null, tag);
			lastBlock.addFanout(this.index, defaultGoto, null);
			lastBlock.close();
		}
		++this.index;
		super.visitEntry(entry);	
	}
			
	protected void visitDoBlock(DoBlock doBlock) {
		int id = doBlock.getUniqueId();
		if (! this.doBlockHash.contains(id)) {
			doBlockHash.add(id);
			++this.inDoBlock;
			doBlock.acceptPostCondition(this);
			Blocks lastBlocks = this.currentBlocks;
			BlockWithAPIData lastBlock = this.currentBlock;
			this.currentBlock = null;
			this.currentBlocks = new Blocks(lastBlocks);
			doBlock.acceptEntryList(this);
			Block firstBlock = this.currentBlocks.getFirstBlock();
			this.currentBlocks = lastBlocks;
			this.currentBlock = lastBlock;
			String tag = ":" + String.valueOf(this.index);
			EntryId defaultDo = new EntryId(null, tag);		
			lastBlock.addFanout(this.index, defaultDo, null);
			this.currentBlocks.put(tag, firstBlock);
			--this.inDoBlock;
		}
	}
	
	public Blocks getBlocks() {
		return this.currentBlocks;
	}
	
	@Override
	protected void visitRoutine(Routine routine) {
		this.reset();
		this.currentBlocks = new Blocks();
		this.currentRoutineName = routine.getName();
		super.visitRoutine(routine);
	}
}
