package com.allrounds.pcms.service.support;

import java.util.Collection;

public interface IValuesFilter<T extends IValueFilterable> {
	
	Collection<T> filterValues( Collection<T> source );
	
}
