package com.allrounds.pcms.service.support;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleNonZeroFilter<T extends IValueFilterable> implements IValuesFilter<T> {

	@Override
	public Collection<T> filterValues(Collection<T> source) {
		final Collection<T> res = new ArrayList<T>();
		if ( source == null ) return res;
		for ( T vp : source ) {
			if ( vp.isImportant() || (vp.getValue() != 0) ) res.add( vp );
		}
		return res;
	}

}
