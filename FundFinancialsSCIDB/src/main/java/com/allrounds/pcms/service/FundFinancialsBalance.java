package com.allrounds.pcms.service;

import java.util.ArrayList;
import java.util.Collection;

import com.allrounds.pcms.service.support.IValueFilterable;
import com.allrounds.pcms.service.support.IValueFiltersFactory;

public class FundFinancialsBalance {
	
	public class ValuePair implements IValueFilterable, Comparable<ValuePair> {
		private String chart;
		private double value = 0;
		private boolean isShowValue = true;
		private boolean isBold = true;
		public ValuePair(String chartofaccounts) {
			this.chart = chartofaccounts;
		}
		public ValuePair(String chartofaccounts, boolean isShowValue, boolean isBold) {
			this.chart = chartofaccounts;
			this.isShowValue = isShowValue;
			this.setBold(isBold);
		}
		public String getChart() {
			return chart;
		}
		public void setChart(String chart) {
			this.chart = chart;
		}
		public double getValue() {
			return value;
		}
		public void addValue(double value) {
			this.value += value;
		}
		public boolean isShowValue() {
			return isShowValue;
		}
		public void setShowValue(boolean isShowValue) {
			this.isShowValue = isShowValue;
		}
		@Override
		public int compareTo(ValuePair o) {
			return this.chart.compareTo(o.getChart());
		}
		@Override
		public boolean isImportant() {
			return !isShowValue || isBold;
		}
		public boolean isBold() {
			return isBold;
		}
		public void setBold(boolean isBold) {
			this.isBold = isBold;
		}
	}
	
	private IValueFiltersFactory valuesFilterFactory;
	public void setValueFiltersFactory(IValueFiltersFactory valuesFilterFactory){
		this.valuesFilterFactory = valuesFilterFactory;
	}

	private Collection<ValuePair> values = new ArrayList<ValuePair>();
	
	public FundFinancialsBalance(){}
	
	public Collection<ValuePair> getValues() {
		return this.valuesFilterFactory.getBalanceFilter().filterValues( this.values );
	}
	public void setValues(Collection<ValuePair> values) {
		this.values = values;
	}
}
