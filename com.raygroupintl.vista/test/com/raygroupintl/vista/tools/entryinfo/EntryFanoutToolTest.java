package com.raygroupintl.vista.tools.entryinfo;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import com.raygroupintl.m.parsetree.Routine;
import com.raygroupintl.m.parsetree.data.BlocksInMap;
import com.raygroupintl.m.parsetree.data.EntryId;
import com.raygroupintl.vista.tools.AccumulatorTestCommon;
import com.raygroupintl.vista.tools.entryfanout.EntryFanouts;

public class EntryFanoutToolTest {
	private void testFanouts(EntryFanouts r, EntryId[] expectedFanouts) {
		Set<EntryId> fanouts = r.getFanouts();
		Assert.assertEquals(expectedFanouts.length, fanouts == null ? 0 : fanouts.size());
		for (EntryId expectedFanout : expectedFanouts) {
			Assert.assertTrue(fanouts.contains(expectedFanout));			
		}				
	}
	
	@Test
	public void test() {
		String[] resourceNames = {
				"resource/APIROU00.m", "resource/APIROU01.m", "resource/APIROU02.m", "resource/APIROU03.m", 
				"resource/DMI.m", "resource/DDI.m", "resource/DIE.m", "resource/FIE.m"};
		Routine[] routines = AccumulatorTestCommon.getRoutines(EntryCodeInfoToolTest.class, resourceNames);
		
		VoidBlockRecorder recorder = new VoidBlockRecorder();
		BlocksInMap<Void> blocksMap = BlocksInMap.getInstance(recorder, routines);
		
		FanoutAccumulator a = new FanoutAccumulator(blocksMap);
				
		this.testFanouts(a.getResult(new EntryId("APIROU00", "FACT")), new EntryId[0]);
		this.testFanouts(a.getResult(new EntryId("APIROU00", "SUM")), new EntryId[0]);
		this.testFanouts(a.getResult(new EntryId("APIROU01", "STORE")), new EntryId[]{new EntryId("APIROU00", "FACT"), new EntryId("APIROU00", "STOREG")});
		this.testFanouts(a.getResult(new EntryId("APIROU01", "LOOP")), new EntryId[]{new EntryId("APIROU01", "SUMFACT"), new EntryId("APIROU01", "LATER"), new EntryId("APIROU00", "FACT"), new EntryId("APIROU00", "SUM")});
	}
}