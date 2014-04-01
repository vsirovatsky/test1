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
		public ValuePair(String chartofaccounts) {
			this.chart = chartofaccounts;
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
			return !isShowValue;
		}
	}
	
	public int totalCount;
	public int assetsCount;
	public int liabilitiesCount;
	public int equitiesCount;
	
	
	public int getTotalCount(){
		return this.totalCount;
	}
	
	public int getAssetsCount() {
		return assetsCount;
	}

	public int getLiabilitiesCount() {
		return liabilitiesCount;
	}

	public int getEquitiesCount() {
		return equitiesCount;
	}
	
	private IValueFiltersFactory valuesFilterFactory;
	public void setValueFiltersFactory(IValueFiltersFactory valuesFilterFactory){
		this.valuesFilterFactory = valuesFilterFactory;
	}

	private Collection<ValuePair> valuesAssets = new ArrayList<ValuePair>();
	private Collection<ValuePair> valuesLiabilities = new ArrayList<ValuePair>();
	private Collection<ValuePair> valuesEquities = new ArrayList<ValuePair>();
	
	public FundFinancialsBalance(){}
	
	public Collection<ValuePair> getValuesAssets() {
		return this.valuesFilterFactory.getBalanceFilter().filterValues( this.valuesAssets );
	}
	public void setValuesAssets(Collection<ValuePair> valuesAssets) {
		this.valuesAssets.addAll(valuesAssets);
	}
	public Collection<ValuePair> getValuesEquities() {
		return this.valuesFilterFactory.getBalanceFilter().filterValues( valuesEquities );
	}
	public void setValuesEquities(Collection<ValuePair> valuesEquities) {
		this.valuesEquities.addAll(valuesEquities);
	}
	public Collection<ValuePair> getValuesLiabilities() {
		return this.valuesFilterFactory.getBalanceFilter().filterValues( valuesLiabilities );
	}
	public void setValuesLiabilities(Collection<ValuePair> valuesLiabilities) {
		this.valuesLiabilities.addAll(valuesLiabilities);
	}
	public int getAssetsSize(){
		return this.valuesAssets.size();
	}
	public int getLiabilitiesSize(){
		return this.valuesLiabilities.size();
	}
	public int getEquitiesSize(){
		return this.valuesEquities.size();
	}
}
