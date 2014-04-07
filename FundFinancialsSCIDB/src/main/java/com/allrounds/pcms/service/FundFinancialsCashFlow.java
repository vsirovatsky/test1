package com.allrounds.pcms.service;

import java.io.Serializable;
import java.util.Collection;

import com.allrounds.pcms.service.support.IValueFilterable;
import com.allrounds.pcms.service.support.IValueFiltersFactory;

public class FundFinancialsCashFlow implements Serializable {

	private static final long serialVersionUID = 1676656459571591081L;

	public class ValuePair implements IValueFilterable, Comparable<ValuePair> {
		private String chart;
		private double value = 0;
		private boolean isShowValue = true;
		private boolean isBold = false;
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
	
	private Collection<ValuePair> values;
	
	public FundFinancialsCashFlow(){
		
	}

	public Collection<ValuePair> getValues() {
		return this.valuesFilterFactory.getCashFlow().filterValues( values );
	}

	public void setValues(Collection<ValuePair> values) {
		this.values = values;
	}
}
