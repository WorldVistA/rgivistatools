package com.raygroupintl.parser.annotation;

import com.raygroupintl.parser.DelimitedListAdapter;
import com.raygroupintl.parser.ListAdapter;
import com.raygroupintl.parser.SequenceAdapter;
import com.raygroupintl.parser.StringAdapter;
import com.raygroupintl.parser.TokenFactory;

public interface AdapterSupply {
	StringAdapter getStringAdapter();
	SequenceAdapter getSequenceAdapter();
	ListAdapter getListAdapter();
	DelimitedListAdapter getDelimitedListAdapter();
	
	Object getAdapter(Class<? extends TokenFactory> tokenCls);
}
