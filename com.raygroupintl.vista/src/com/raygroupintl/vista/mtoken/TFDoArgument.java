package com.raygroupintl.vista.mtoken;

import com.raygroupintl.vista.fnds.IToken;
import com.raygroupintl.vista.fnds.ITokenFactory;
import com.raygroupintl.vista.fnds.ITokenFactorySupply;
import com.raygroupintl.vista.token.TFAllRequired;
import com.raygroupintl.vista.token.TFConstChar;
import com.raygroupintl.vista.token.TFNull;
import com.raygroupintl.vista.token.TFParallel;
import com.raygroupintl.vista.token.TFSerialBase;
import com.raygroupintl.vista.token.TFSerialOR;

public class TFDoArgument extends TFSerialBase {
	private static ITokenFactory getFactory0(IToken[] previousTokens) {
		TFLabel tfl = TFLabel.getInstance();
		TFIndirection tfi = TFIndirection.getInstance();
		TFParallel tf = TFParallel.getInstance(tfl, tfi);
		return tf; 
	}
	
	private static ITokenFactory getFactory1(IToken[] previousTokens) {
		if (previousTokens[0] == null) {
			return new TFNull();
		} else {
			return new TFAllRequired() {								
				@Override
				protected ITokenFactory[] getFactories() {
					TFConstChar tfc = TFConstChar.getInstance('+');
					TFExpr tfe = TFExpr.getInstance();
					return new ITokenFactory[]{tfc, tfe};
				}
			};
		}
	}

	private static ITokenFactory getFactory2(IToken[] previousTokens) {
		return TFConstChar.getInstance('^');
	}

	private static ITokenFactory getFactory3(IToken[] previousTokens) {
		if (previousTokens[2] == null) {
			return new TFNull();
		} else {
			ITokenFactory tfEnv = TFEnvironment.getInstance();
			ITokenFactory tfName = TFName.getInstance();
			ITokenFactory tfEnvName = TFSerialOR.getInstance(tfEnv, tfName);
			ITokenFactory tfInd = TFIndirection.getInstance();
			return TFParallel.getInstance(tfEnvName, tfInd);
		}
	}

	private static ITokenFactory getFactory4(IToken[] previousTokens) {
		if ((previousTokens[1] != null) || (previousTokens[3] instanceof TIndirection)) {
			return new TFNull();
		} else {
			return new TFActualList();
		}
	}

	protected ITokenFactorySupply getFactorySupply(final int count) {
		return new ITokenFactorySupply() {				
			@Override
			public int getCount() {
				return count;
			}
			
			@Override
			public ITokenFactory get(IToken[] previousTokens) {
				int n = previousTokens.length;
				switch (n) {
					case 0: return TFDoArgument.getFactory0(previousTokens); 
					case 1: return TFDoArgument.getFactory1(previousTokens); 
					case 2: return TFDoArgument.getFactory2(previousTokens); 
					case 3: return TFDoArgument.getFactory3(previousTokens); 
					case 4: return TFDoArgument.getFactory4(previousTokens); 
					case 5: return TCommandName.getTFPostCondition(previousTokens);
					default:
						assert(false);
						return null;
				}
			}
		};
	}

	@Override
	protected ITokenFactorySupply getFactorySupply() {
		return this.getFactorySupply(6);
	}
	
	
	protected int getCodeNextIsNull(IToken[] foundTokens) {
		int n = foundTokens.length;
		if (n == 2) {
			if (foundTokens[0] == null) {
				return RETURN_NULL;
			} 
		}
		if (n == 3) {
			if (foundTokens[2] != null) {
				return this.getErrorCode();
			}
		}
		return CONTINUE;
	}
	
	protected int getCodeStringEnds(IToken[] foundTokens) {
		if (foundTokens.length == 3) {
			return this.getErrorCode();
		}
		return 0;
	}
	
	private static class TFDoArgumentNoPostCondition extends TFDoArgument {
		@Override
		protected ITokenFactorySupply getFactorySupply() {
			return getFactorySupply(5);
		}
	}
		
	public static TFDoArgument getInstance() {
		return new TFDoArgument();
	}
	
	public static TFDoArgument getInstance(boolean noPostCondition) {
		if (noPostCondition) {
			return new TFDoArgumentNoPostCondition();
		} else {
			return new TFDoArgument();
		}
	}	
}	

