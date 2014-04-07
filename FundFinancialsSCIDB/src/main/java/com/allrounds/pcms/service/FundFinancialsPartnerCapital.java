package com.allrounds.pcms.service;

import java.io.Serializable;
import java.util.Collection;

import com.allrounds.pcms.service.support.IValueFilterable;
import com.allrounds.pcms.service.support.IValueFiltersFactory;

public class FundFinancialsPartnerCapital implements Serializable {

	private static final long serialVersionUID = -1568542849803118698L;

	public class ValuePair implements IValueFilterable, Comparable<ValuePair> {
		private String chart;
		private double valueGP = 0;
		private double valueLP = 0;
		private boolean isShowAlways = false;
		public ValuePair(String chartofaccounts) {
			this.chart = chartofaccounts;
		}
		public ValuePair(String chartofaccounts, boolean isShowAlways) {
			this.chart = chartofaccounts;
			this.isShowAlways = isShowAlways;
		}
		public String getChart() {
			return chart;
		}
		public void setChart(String chart) {
			this.chart = chart;
		}
		public double getValueGP() {
			return valueGP;
		}
		public void setValueGP(double valueGP) {
			this.valueGP = valueGP;
		}
		public double getValueLP() {
			return valueLP;
		}
		public void setValueLP(double valueLP) {
			this.valueLP = valueLP;
		}
		public double getValueTotal() {
			return getValueGP() + getValueLP();
		}
		public void addValueGP(double value) {
			this.valueGP += value;
		}
		public void addValueLP(double value) {
			this.valueLP += value;
		}
		@Override
		public double getValue() {
			return getValueTotal();
		}
		@Override
		public int compareTo(ValuePair o) {
			return this.chart.compareTo(o.getChart());
		}
		@Override
		public boolean isImportant() {
			return this.isShowAlways;
		}
	}
	
	private IValueFiltersFactory valuesFilterFactory;
	public void setValueFiltersFactory(IValueFiltersFactory valuesFilterFactory){
		this.valuesFilterFactory = valuesFilterFactory;
	}
	
	private Collection<ValuePair> values;
	
	public FundFinancialsPartnerCapital(){
		
	}

	public Collection<ValuePair> getValues() {
		return this.valuesFilterFactory.getPartnerCapitalFilter().filterValues( values );
	}

	public void setValues(Collection<ValuePair> values) {
		this.values = values;
	}
}
