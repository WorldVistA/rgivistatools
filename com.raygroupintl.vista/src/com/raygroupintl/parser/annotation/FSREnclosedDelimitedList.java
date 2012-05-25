package com.raygroupintl.parser.annotation;

import java.util.Map;

import com.raygroupintl.parser.TFDelimitedList;
import com.raygroupintl.parser.TFSequence;
import com.raygroupintl.parser.TokenFactory;

public class FSREnclosedDelimitedList extends FSRBase implements TopTFRule {
	private FactorySupplyRule element;
	private FactorySupplyRule delimiter;
	private FactorySupplyRule left;
	private FactorySupplyRule right;
	
	public FSREnclosedDelimitedList(FactorySupplyRule element, FactorySupplyRule delimiter, FactorySupplyRule left, FactorySupplyRule right, boolean required) {
		super(required);
		this.element = element;
		this.delimiter = delimiter;
		this.left = left;
		this.right = right;
	}
	
	@Override
	public TFSequence getFactory(String name, Map<String, TokenFactory> symbols) {
		TFSequence result = new TFSequence(name);
		TokenFactory e = this.element.getFactory(name + ".element", symbols);
		TokenFactory d = this.delimiter.getFactory(name + ".delimiter", symbols);
		TFDelimitedList dl = new TFDelimitedList(name);
		dl.set(e, d, false);
		TokenFactory l = this.left.getFactory(name + ".left", symbols);
		TokenFactory r = this.right.getFactory(name + ".right", symbols);
		TokenFactory[] factories = {l, dl, r};
		boolean[] required = {true, true, true};
		result.setFactories(factories, required);
		return result;
	}

	@Override
	public TFSequence getTopFactory(String name, Map<String, TokenFactory> symbols, boolean asShell) {
		if (! asShell) {
			return this.getFactory(name, symbols);
		} else {			
			return new TFSequence(name);
		}
	}
}