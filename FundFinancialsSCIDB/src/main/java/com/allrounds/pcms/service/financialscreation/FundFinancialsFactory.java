package com.allrounds.pcms.service.financialscreation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.allrounds.pcms.dao.DAODetailsParams;
import com.allrounds.pcms.dao.DAOParams;
import com.allrounds.pcms.dao.IDataProvider;
import com.allrounds.pcms.domain.JournalEntryItem;
import com.allrounds.pcms.domain.JournalEntryItem.CATEGORY;
import com.allrounds.pcms.service.FundFinancials;
import com.allrounds.pcms.service.FundFinancialsBalance;
import com.allrounds.pcms.service.FundFinancialsCashFlow;
import com.allrounds.pcms.service.FundFinancialsPartnerCapital;
import com.allrounds.pcms.service.FundFinancialsStatements;
import com.allrounds.pcms.service.ItemsDetails;
import com.allrounds.pcms.service.PcmsServiceException;
import com.allrounds.pcms.service.PcmsServiceException.CategoryNotFoundPcmsServiceException;
import com.allrounds.pcms.service.support.IValueFiltersFactory;
import com.allrounds.pcms.service.support.JeiUtils;

public class FundFinancialsFactory implements IFundFinancialsFactory {

	@Autowired
	private IDataProvider dataProvider;
	
	@Autowired
	private IValueFiltersFactory valuesFilterFactory;
	
	public IDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(IDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}
	
	@Override
	public FundFinancials createFundFinancials( FundFinancialsParameters params ) throws PcmsServiceException {
		final FundFinancials ff = new FundFinancials();
		
		DAOParams daoParams = new DAOParams();
		daoParams.setFundId("default");
		daoParams.setStartDate( params.getStartDate() );
		daoParams.setEndDate( params.getEndDate() );
		final List<JournalEntryItem> items = getDataProvider().getAllItems( daoParams  );
		
		switch ( params.getMode() ) {
		case BALANCE:
			ff.setBalance( createFundFinancialsBalance(items) );
			break;
		case OPERATIONS:
			ff.setStatements( createFundFinancialsStatements(items) );
			break;
		case PARTNER_CAPITAL:
			ff.setPartnerCapital( creatFundFinancialsPartnerCapital(items) );
			break;
		case CASH_FLOW:
			ff.setCashFlow( createFundFinancialsCashFlow(items) );
			break;
		case ALL:
			ff.setBalance( createFundFinancialsBalance(items) );
			ff.setStatements( createFundFinancialsStatements(items) );
			ff.setPartnerCapital( creatFundFinancialsPartnerCapital(items) );
			ff.setCashFlow( createFundFinancialsCashFlow(items) );
			break;
		}
		
		return ff;
	}
	
	private FundFinancialsBalance createFundFinancialsBalance( final List<JournalEntryItem> items ) throws CategoryNotFoundPcmsServiceException{
		final FundFinancialsBalance ffb = new FundFinancialsBalance();
		ffb.setValueFiltersFactory(this.valuesFilterFactory);
		
		final TreeMap<String, HashMap<String, FundFinancialsBalance.ValuePair>> valuesAssets = new TreeMap<String, HashMap<String, FundFinancialsBalance.ValuePair>>();
		final TreeMap<String, HashMap<String, FundFinancialsBalance.ValuePair>> valuesLiabilities = new TreeMap<String, HashMap<String, FundFinancialsBalance.ValuePair>>();
		final TreeMap<String, HashMap<String, FundFinancialsBalance.ValuePair>> valuesEquities = new TreeMap<String, HashMap<String, FundFinancialsBalance.ValuePair>>();
		for ( JournalEntryItem jei : items ) {
			HashMap<String, FundFinancialsBalance.ValuePair> subMap;
			FundFinancialsBalance.ValuePair p;
			switch (jei.getCategory()) {
				case ASSET:
					subMap = valuesAssets.get(jei.getChartcategory());
					if ( subMap == null ) {
						subMap = new HashMap<String, FundFinancialsBalance.ValuePair>();
						valuesAssets.put(jei.getChartcategory(), subMap);
					}
					p = subMap.get(jei.getChartofaccounts());
					if ( p == null ) {
						p = ffb.new ValuePair( jei.getChartofaccounts() );
						subMap.put( jei.getChartofaccounts(), p );
					}
					p.addValue( jei.getDebit() - jei.getCredit() );
					break;
				case LIABILITY:
					subMap = valuesLiabilities.get(jei.getChartcategory());
					if ( subMap == null ) {
						subMap = new HashMap<String, FundFinancialsBalance.ValuePair>();
						valuesLiabilities.put(jei.getChartcategory(), subMap);
					}
					p = subMap.get(jei.getChartofaccounts());
					if ( p == null ) {
						p = ffb.new ValuePair( jei.getChartofaccounts() );
						subMap.put( jei.getChartofaccounts(), p );
					}
					p.addValue( jei.getCredit() - jei.getDebit() );
					break;
				case EQUITY:
					subMap = valuesEquities.get(jei.getChartcategory());
					if ( subMap == null ) {
						subMap = new HashMap<String, FundFinancialsBalance.ValuePair>();
						valuesEquities.put(jei.getChartcategory(), subMap);
					}
					p = subMap.get(jei.getChartofaccounts());
					if ( p == null ) {
						p = ffb.new ValuePair( jei.getChartofaccounts() );
						subMap.put( jei.getChartofaccounts(), p );
					}
					p.addValue( jei.getCredit() - jei.getDebit() );
					break;
				default:
					throw new PcmsServiceException.CategoryNotFoundPcmsServiceException();
			}
		}
		
		final List<FundFinancialsBalance.ValuePair> values = new ArrayList<FundFinancialsBalance.ValuePair>();
		
		values.add( ffb.new ValuePair("Assets", false, true) );
		final FundFinancialsBalance.ValuePair totalAssets = ffb.new ValuePair("Total Assets", true, true);
		if ( !valuesAssets.isEmpty() ) {
			for ( String key : valuesAssets.keySet() ) {
				values.add( ffb.new ValuePair(key+":", false, false) );
				List<FundFinancialsBalance.ValuePair> localvalues = new ArrayList<FundFinancialsBalance.ValuePair>( valuesAssets.get( key ).values() );
				Collections.sort( localvalues );
				values.addAll( localvalues );
				for ( FundFinancialsBalance.ValuePair p : localvalues ) {
					totalAssets.addValue( p.getValue() );
				}
			}
		}
		values.add( totalAssets );
		values.add( ffb.new ValuePair("", false, false) );

		values.add( ffb.new ValuePair("Liabilities", false, true) );
		final FundFinancialsBalance.ValuePair totalLiabilities = ffb.new ValuePair("Total Liabilities", true, true);
		if ( !valuesLiabilities.isEmpty() ) {
			for ( String key : valuesLiabilities.keySet() ) {
				values.add( ffb.new ValuePair(key+":", false, false) );
				List<FundFinancialsBalance.ValuePair> localvalues = new ArrayList<FundFinancialsBalance.ValuePair>( valuesLiabilities.get( key ).values() );
				Collections.sort( localvalues );
				values.addAll( localvalues );
				for ( FundFinancialsBalance.ValuePair p : localvalues ) {
					totalLiabilities.addValue( p.getValue() );
				}
			}
		}
		values.add( totalLiabilities );
		values.add( ffb.new ValuePair("", false, false) );
		
		values.add( ffb.new ValuePair("Equity", false, true) );
		final FundFinancialsBalance.ValuePair totalEquities = ffb.new ValuePair("Total Equity", true, true);
		if ( !valuesEquities.isEmpty() ) {
			for ( String key : valuesEquities.keySet() ) {
				values.add( ffb.new ValuePair(key+":", false, false) );
				List<FundFinancialsBalance.ValuePair> localvalues = new ArrayList<FundFinancialsBalance.ValuePair>( valuesEquities.get( key ).values() );
				Collections.sort( localvalues );
				values.addAll( localvalues );
				for ( FundFinancialsBalance.ValuePair p : localvalues ) {
					totalEquities.addValue( p.getValue() );
				}
			}
		}
		values.add( totalEquities );
		values.add( ffb.new ValuePair("", false, false) );
		
		final FundFinancialsBalance.ValuePair totalLiabilitiesEquities = ffb.new ValuePair("Total Liabilities and Equities", true, true);
		totalLiabilitiesEquities.addValue( totalLiabilities.getValue() );
		totalLiabilitiesEquities.addValue( totalEquities.getValue() );
		values.add( totalLiabilitiesEquities );
		
		ffb.setValues( values );
		
		return ffb;
	}
	
	private FundFinancialsStatements createFundFinancialsStatements( final List<JournalEntryItem> items ) throws CategoryNotFoundPcmsServiceException{
		final FundFinancialsStatements ffs = new FundFinancialsStatements();
		ffs.setValueFiltersFactory(this.valuesFilterFactory);
		
		final Map<String,FundFinancialsStatements.ValuePair> income = new HashMap<String,FundFinancialsStatements.ValuePair>();
		final Map<String,FundFinancialsStatements.ValuePair> expense = new HashMap<String,FundFinancialsStatements.ValuePair>();
		final Map<String,FundFinancialsStatements.ValuePair> unrealizedGain = new HashMap<String,FundFinancialsStatements.ValuePair>();
		final Map<String,FundFinancialsStatements.ValuePair> realizedGain = new HashMap<String,FundFinancialsStatements.ValuePair>();
    	
		for ( JournalEntryItem jei : items ) {
			FundFinancialsStatements.ValuePair p = null;
			if ( JeiUtils.isIncome(jei.getChartcategory()) ) {
				p = income.get( jei.getChartofaccounts() );
				if ( p == null ) {
					p = ffs.new ValuePair( jei.getChartofaccounts() );
					income.put(jei.getChartofaccounts(), p);
				}
				p.addValue( jei.getCredit() - jei.getDebit() );
			} else if ( JeiUtils.isExpense(jei.getChartcategory()) ) {
				p = expense.get( jei.getChartofaccounts() );
				if ( p == null ) {
					p = ffs.new ValuePair( jei.getChartofaccounts() );
					expense.put(jei.getChartofaccounts(), p);
				}
				p.addValue( jei.getDebit() - jei.getCredit() );
			} else if ( JeiUtils.isUnrealizedGains(jei.getChartofaccounts()) ) {
				p = unrealizedGain.get( jei.getChartofaccounts() );
				if ( p == null ) {
					p = ffs.new ValuePair( jei.getChartofaccounts() );
					unrealizedGain.put(jei.getChartofaccounts(), p);
				}
				p.addValue( jei.getCredit() - jei.getDebit() );
			} else if ( JeiUtils.isRealizedGains(jei.getChartofaccounts()) ) {
				p = realizedGain.get( jei.getChartofaccounts() );
				if ( p == null ) {
					p = ffs.new ValuePair( jei.getChartofaccounts() );
					realizedGain.put(jei.getChartofaccounts(), p);
				}
				p.addValue( jei.getCredit() - jei.getDebit() );
			}
		}
		
		final Collection<FundFinancialsStatements.ValuePair> values = new ArrayList<FundFinancialsStatements.ValuePair>();
		ffs.setValues(values);
		
		final List<FundFinancialsStatements.ValuePair> incomeList = new ArrayList<FundFinancialsStatements.ValuePair>( income.size() );
		final List<FundFinancialsStatements.ValuePair> expenseList = new ArrayList<FundFinancialsStatements.ValuePair>( expense.size() );
		final List<FundFinancialsStatements.ValuePair> unrealizedGainList = new ArrayList<FundFinancialsStatements.ValuePair>( unrealizedGain.size() );
		final List<FundFinancialsStatements.ValuePair> realizedGainList = new ArrayList<FundFinancialsStatements.ValuePair>( realizedGain.size() );
		incomeList.addAll( income.values() );
		expenseList.addAll( expense.values() );
		unrealizedGainList.addAll( unrealizedGain.values() );
		realizedGainList.addAll( realizedGain.values() );
		Collections.sort(incomeList);
		Collections.sort(expenseList);
		Collections.sort(unrealizedGainList);
		Collections.sort(realizedGainList);
		
		values.add( ffs.new ValuePair( "Investment Income", false, true ) );
		values.addAll( incomeList );
		final FundFinancialsStatements.ValuePair totalIncome = ffs.new ValuePair( "Investment Income", true, true );
		for ( FundFinancialsStatements.ValuePair p : incomeList ) {
			totalIncome.addValue( p.getValue() );
		}
		values.add( totalIncome );
		values.add( ffs.new ValuePair( "", false, true ) );
		
		values.add( ffs.new ValuePair( "Expenses", false, true ) );
		values.addAll( expenseList );
		final FundFinancialsStatements.ValuePair totalExpenses = ffs.new ValuePair( "Total Expenses", true, true );
		for ( FundFinancialsStatements.ValuePair p : expenseList ) {
			totalExpenses.addValue( p.getValue() );
		}
		values.add( totalExpenses );
		values.add( ffs.new ValuePair( "", false, true ) );
		
		final FundFinancialsStatements.ValuePair netInvestmentIncomeOrLoss = ffs.new ValuePair( "Net investment income/(loss)", true, true );
		netInvestmentIncomeOrLoss.addValue(totalIncome.getValue());
		netInvestmentIncomeOrLoss.addValue(-totalExpenses.getValue());
		values.add( netInvestmentIncomeOrLoss );
		values.add( ffs.new ValuePair( "", false, true ) );
    	
		final FundFinancialsStatements.ValuePair netEarningsFromInvestments = ffs.new ValuePair( "Net earnings/(loss) from investments", true, true );
		for ( FundFinancialsStatements.ValuePair p : realizedGainList ) {
			netEarningsFromInvestments.addValue( p.getValue() );
		}
		values.add( netEarningsFromInvestments );
		values.addAll( realizedGainList );
		values.add( ffs.new ValuePair( "", false, true ) );
		
		final FundFinancialsStatements.ValuePair netChangeInUnrealized = ffs.new ValuePair( "Net change in unrealized appreciated/(depreciation) from investments", true, true );
		for ( FundFinancialsStatements.ValuePair p : unrealizedGainList ) {
			netChangeInUnrealized.addValue( p.getValue() );
		}
		values.add( netChangeInUnrealized );
		values.addAll( unrealizedGainList );
		values.add( ffs.new ValuePair( "", false, true ) );

		final FundFinancialsStatements.ValuePair netIncomeOrLoss = ffs.new ValuePair( "Net Income/(Loss)", true, true );
		netIncomeOrLoss.addValue(totalIncome.getValue());
		netIncomeOrLoss.addValue(-totalExpenses.getValue());
		netIncomeOrLoss.addValue(netEarningsFromInvestments.getValue());
		netIncomeOrLoss.addValue(netChangeInUnrealized.getValue());
		values.add( netIncomeOrLoss );
		
		return ffs;
	}
	
	private FundFinancialsPartnerCapital creatFundFinancialsPartnerCapital(	List<JournalEntryItem> items ) {
		final FundFinancialsPartnerCapital ffpc = new FundFinancialsPartnerCapital();
		ffpc.setValueFiltersFactory(this.valuesFilterFactory);
		
		FundFinancialsPartnerCapital.ValuePair beginCapitalPC = ffpc.new ValuePair( "Partners capital at Beginning" );
		FundFinancialsPartnerCapital.ValuePair netIncomePC = ffpc.new ValuePair( "Net investment income" );
		FundFinancialsPartnerCapital.ValuePair realizedGainPC = ffpc.new ValuePair( "Net change in realized gains" );
		FundFinancialsPartnerCapital.ValuePair unrealizedGainPC = ffpc.new ValuePair( "Net change in unrealized gains" );
		FundFinancialsPartnerCapital.ValuePair expensePC = ffpc.new ValuePair( "Net changes in operating expenses" );
		FundFinancialsPartnerCapital.ValuePair redemptionsPC = ffpc.new ValuePair( "Redemptions" );
		FundFinancialsPartnerCapital.ValuePair distributionsPC = ffpc.new ValuePair( "Distributions" );
		FundFinancialsPartnerCapital.ValuePair distributionsChildsPC = ffpc.new ValuePair( "Distribution from Funds" );
		FundFinancialsPartnerCapital.ValuePair contributionsPC = ffpc.new ValuePair( "Contributions" );
		FundFinancialsPartnerCapital.ValuePair othersPC = ffpc.new ValuePair( "Other" );
		FundFinancialsPartnerCapital.ValuePair endCapitalPC = ffpc.new ValuePair( "Partners capital at End" );
		
		for ( JournalEntryItem jei : items ) {
			if ( jei.getCategory() == CATEGORY.ASSET ) continue;
			 //TODO: Date < date implement
			
			if ( JeiUtils.isIncome(jei.getChartcategory()) ) {
				netIncomePC.addValueLP( jei.getCredit() - jei.getDebit() );
			} else if ( JeiUtils.isExpense(jei.getChartcategory()) ) {
				expensePC.addValueLP( jei.getCredit() - jei.getDebit() );
			} else if ( JeiUtils.isUnrealizedGains(jei.getChartofaccounts()) ) {
				unrealizedGainPC.addValueLP( jei.getCredit() - jei.getDebit() );
			} else if ( JeiUtils.isRealizedGains(jei.getChartofaccounts()) ) {
				realizedGainPC.addValueLP( jei.getCredit() - jei.getDebit() );
			} else {
				contributionsPC.addValueLP( jei.getCredit() - jei.getDebit() );
			}
		}
		
		final Collection<FundFinancialsPartnerCapital.ValuePair> values = new ArrayList<FundFinancialsPartnerCapital.ValuePair>();
		values.add( beginCapitalPC );
		values.add( ffpc.new ValuePair( "", true ) );
		values.add( contributionsPC );
		values.add( ffpc.new ValuePair( "", true ) );
		values.add( netIncomePC );
		values.add( ffpc.new ValuePair( "", true ) );
		values.add( realizedGainPC );
		values.add( ffpc.new ValuePair( "", true ) );
		values.add( unrealizedGainPC );
		values.add( ffpc.new ValuePair( "", true ) );
		values.add( expensePC );
		values.add( ffpc.new ValuePair( "", true ) );
		values.add( redemptionsPC );
		values.add( ffpc.new ValuePair( "", true ) );
		values.add( distributionsPC );
		values.add( ffpc.new ValuePair( "", true ) );
		values.add( distributionsChildsPC );
		values.add( ffpc.new ValuePair( "", true ) );
		values.add( othersPC );
		values.add( ffpc.new ValuePair( "", true ) );
		
		for ( FundFinancialsPartnerCapital.ValuePair p : values ) {
			endCapitalPC.addValueGP( p.getValueGP() );
			endCapitalPC.addValueLP( p.getValueLP() );
		}
		values.add( endCapitalPC );
		
		ffpc.setValues(values);
		
		return ffpc;
	}
	
	private FundFinancialsCashFlow createFundFinancialsCashFlow( List<JournalEntryItem> items ) {
		final FundFinancialsCashFlow ffcf = new FundFinancialsCashFlow();
		ffcf.setValueFiltersFactory(this.valuesFilterFactory);
		
		final FundFinancialsCashFlow.ValuePair netIncomeCF = ffcf.new ValuePair( "Net Income/Loss" );//OA
		final FundFinancialsCashFlow.ValuePair carriedInterestCF = ffcf.new ValuePair( "Change in Carried Interest" );//OA
		final FundFinancialsCashFlow.ValuePair manFeeCF = ffcf.new ValuePair( "Management fee payable" );//OA
		final FundFinancialsCashFlow.ValuePair otherAssetsCF = ffcf.new ValuePair( "Other Assets" );//OA
		final FundFinancialsCashFlow.ValuePair accPayableCF = ffcf.new ValuePair( "Accounts Payable" );//OA
		final FundFinancialsCashFlow.ValuePair netChangeUnrealized = ffcf.new ValuePair( "Net change in unrealized gain from investments" );//OA
		
		final FundFinancialsCashFlow.ValuePair netProceedsFromInvCF = ffcf.new ValuePair( "Net proceeds from Investments" );//IA
		final FundFinancialsCashFlow.ValuePair investmentsInFundsCF = ffcf.new ValuePair( "Investments in Funds" );//IA
		final FundFinancialsCashFlow.ValuePair purchasedCF = ffcf.new ValuePair( "Purchase/Sale of Investments" );//IA
		
		final FundFinancialsCashFlow.ValuePair contribFromInvestCF = ffcf.new ValuePair( "Contributions" );//FA
		final FundFinancialsCashFlow.ValuePair commitmFromInvestCF = ffcf.new ValuePair( "Commitments" );//FA
		final FundFinancialsCashFlow.ValuePair distribFromFinActCF = ffcf.new ValuePair( "Distributions" );//FA
		final FundFinancialsCashFlow.ValuePair redemptionCF = ffcf.new ValuePair( "Redemption" );//FA
		final FundFinancialsCashFlow.ValuePair proceedsFromLoansAndNotesCF = ffcf.new ValuePair( "Proceeds from loans and notes" );//FA
		final FundFinancialsCashFlow.ValuePair prefDividendCF = ffcf.new ValuePair( "Preferred Dividend" );//FA
		final FundFinancialsCashFlow.ValuePair dividendPayableCF = ffcf.new ValuePair( "Dividend Payable" );//FA
		
		final FundFinancialsCashFlow.ValuePair beginCF = ffcf.new ValuePair( "Cash and cash equivalents, beginning of the year", true, true );
		final FundFinancialsCashFlow.ValuePair netEarningsLossFromInvestments = ffcf.new ValuePair( "Net earnings/loss from investments" );
		final FundFinancialsCashFlow.ValuePair netChangeCashEquivalents = ffcf.new ValuePair( "Net change in cash and cash equivalents", true, true );
		final FundFinancialsCashFlow.ValuePair endCF = ffcf.new ValuePair( "Cash and Cash equivalents, end of period", true, true );
		
		final Map<String,FundFinancialsCashFlow.ValuePair> additionalFlows = new HashMap<String, FundFinancialsCashFlow.ValuePair>();
		
		for ( JournalEntryItem jei : items ) {
			
			if ( jei.getChartcategory() == "Cash" ) continue;
			double am = (jei.getCategory() == CATEGORY.ASSET) ? ( jei.getDebit() - jei.getCredit() ) : ( jei.getCredit() - jei.getDebit() );
			 //TODO: Date < date implement
			
			if ( jei.getChartofaccounts() == "Partner Capital" ) {
				contribFromInvestCF.addValue(am);
			} else if ( JeiUtils.isNetIncome( jei.getChartcategory() ) ) {
				netIncomeCF.addValue(am);
			} else if ( JeiUtils.isRealizedGains( jei.getChartofaccounts() ) ) {
				netIncomeCF.addValue(am);
			} else if ( JeiUtils.isUnrealizedGains( jei.getChartofaccounts() ) ) {
				netIncomeCF.addValue(am);
				netChangeUnrealized.addValue( jei.getDebit() - jei.getCredit() );
			} else if ( ( jei.getChartofaccounts() == "Carried Interest Allocation Unpaid" ) || ( jei.getChartofaccounts() == "Carried Interest Allocation Earned" ) ) {
				carriedInterestCF.addValue(am);
			} else if ( ( jei.getChartofaccounts() == "Capital Contributions" ) && (jei.getDebit() > 0) ) {
				netProceedsFromInvCF.addValue(am);
			} else if ( ( jei.getChartofaccounts() == "Preferred Dividend Payable" ) && (jei.getCredit() > 0) ) {
				prefDividendCF.addValue(am);
			} else if ( jei.getChartofaccounts() == "Dividends Payable" ) {
				dividendPayableCF.addValue(am);
			} else if  ( (jei.getChartcategory() == "Marketable Securities") || (jei.getChartcategory() == "Non Marketable Securities") ) {
				purchasedCF.addValue(am);
			} else if ( jei.getChartofaccounts() == "Management Fee Payable" ) {
				manFeeCF.addValue(am);
			} else if ( jei.getChartofaccounts() == "Investments in Funds" ) {
				investmentsInFundsCF.addValue( am );
			} else if ( jei.getChartofaccounts() == "Accounts Payabl" ) {
				accPayableCF.addValue( am );
			} else if ( jei.getCategory() == CATEGORY.ASSET ) {
				otherAssetsCF.addValue( am );
			} else if ( jei.getChartofaccounts() == "Debt" ) {
				proceedsFromLoansAndNotesCF.addValue( am );
			} else {
				FundFinancialsCashFlow.ValuePair p = additionalFlows.get( jei.getChartofaccounts() );
				if ( p == null ) {
					p = ffcf.new ValuePair( jei.getChartofaccounts() );
					additionalFlows.put( jei.getChartofaccounts(), p );
				}
				p.addValue(am);
			}
		}
		
		final Collection<FundFinancialsCashFlow.ValuePair> values = new ArrayList<FundFinancialsCashFlow.ValuePair>();
		ffcf.setValues(values);
		
		final List<FundFinancialsCashFlow.ValuePair> operating = new ArrayList<FundFinancialsCashFlow.ValuePair>();
		final List<FundFinancialsCashFlow.ValuePair> investing = new ArrayList<FundFinancialsCashFlow.ValuePair>();
		final List<FundFinancialsCashFlow.ValuePair> financing = new ArrayList<FundFinancialsCashFlow.ValuePair>();
		
		// OPERATING
		values.add( ffcf.new ValuePair("Operating Activities", false, true) );
		values.add( ffcf.new ValuePair("", false, true) );
		values.add( netIncomeCF );
		operating.add(netIncomeCF);
		if ( netChangeUnrealized.getValue() != 0 ) {
			values.add( ffcf.new ValuePair("", false, true) );
			values.add( netChangeUnrealized );
			operating.add(netChangeUnrealized);
		}
		if ( carriedInterestCF.getValue() != 0 ) {
			values.add( ffcf.new ValuePair("", false, true) );
			values.add( carriedInterestCF );
			operating.add(carriedInterestCF);
		}
		if ( manFeeCF.getValue() != 0 ) {
			values.add( ffcf.new ValuePair("", false, true) );
			values.add( manFeeCF );
			operating.add(manFeeCF);
		}
		if ( accPayableCF.getValue() != 0 ) {
			values.add( ffcf.new ValuePair("", false, true) );
			values.add( accPayableCF );
			operating.add(accPayableCF);
		}
		if ( otherAssetsCF.getValue() != 0 ) {
			values.add( ffcf.new ValuePair("", false, true) );
			values.add( otherAssetsCF );
			operating.add(otherAssetsCF);
		}
		if ( netEarningsLossFromInvestments.getValue() != 0 ) {
			values.add( ffcf.new ValuePair("", false, true) );
			values.add( netEarningsLossFromInvestments );
			operating.add(netEarningsLossFromInvestments);
		}
		final FundFinancialsCashFlow.ValuePair netOperatingActivities = ffcf.new ValuePair("Net Cash Provided by (Used in) Operations", true, true);
		for ( FundFinancialsCashFlow.ValuePair p : operating ) {
			netOperatingActivities.addValue( p.getValue() );
		}
		values.add( ffcf.new ValuePair("", false, true) );
		values.add( netOperatingActivities );
		values.add( ffcf.new ValuePair("", false, true) );
		
		// INVESTING
		values.add( ffcf.new ValuePair("Investing Activities", false, true) );
		if ( netProceedsFromInvCF.getValue() != 0 ) {
			values.add( ffcf.new ValuePair("", false, true) );
			values.add( netProceedsFromInvCF );
			investing.add(netProceedsFromInvCF);
		}
		if ( investmentsInFundsCF.getValue() != 0 ) {
			values.add( ffcf.new ValuePair("", false, true) );
			values.add( investmentsInFundsCF );
			investing.add(investmentsInFundsCF);
		}
		if ( purchasedCF.getValue() != 0 ) {
			values.add( ffcf.new ValuePair("", false, true) );
			values.add( purchasedCF );
			investing.add(purchasedCF);
		}
		final FundFinancialsCashFlow.ValuePair netInvestingActivities = ffcf.new ValuePair("Net Cash Provided by Investing Activities", true, true);
		for ( FundFinancialsCashFlow.ValuePair p : investing ) {
			netInvestingActivities.addValue( p.getValue() );
		}
		values.add( ffcf.new ValuePair("", false, true) );
		values.add( netInvestingActivities );
		values.add( ffcf.new ValuePair("", false, true) );

		// FINANCING
		values.add( ffcf.new ValuePair("Financing Activities", false, true) );
		if ( contribFromInvestCF.getValue() != 0 ) {
			values.add( ffcf.new ValuePair("", false, true) );
			values.add( contribFromInvestCF );
			financing.add(contribFromInvestCF);
		}
		if ( commitmFromInvestCF.getValue() != 0 ) {
			values.add( ffcf.new ValuePair("", false, true) );
			values.add( commitmFromInvestCF );
			financing.add(commitmFromInvestCF);
		}
		if ( distribFromFinActCF.getValue() != 0 ) {
			values.add( ffcf.new ValuePair("", false, true) );
			values.add( distribFromFinActCF );
			financing.add(distribFromFinActCF);
		}
		if ( redemptionCF.getValue() != 0 ) {
			values.add( ffcf.new ValuePair("", false, true) );
			values.add( redemptionCF );
			financing.add(redemptionCF);
		}
		if ( proceedsFromLoansAndNotesCF.getValue() != 0 ) {
			values.add( ffcf.new ValuePair("", false, true) );
			values.add( proceedsFromLoansAndNotesCF );
			financing.add(proceedsFromLoansAndNotesCF);
		}
		if ( prefDividendCF.getValue() != 0 ) {
			values.add( ffcf.new ValuePair("", false, true) );
			values.add( prefDividendCF );
			financing.add(prefDividendCF);
		}
		if ( dividendPayableCF.getValue() != 0 ) {
			values.add( ffcf.new ValuePair("", false, true) );
			values.add( dividendPayableCF );
			financing.add(dividendPayableCF);
		}
		final FundFinancialsCashFlow.ValuePair netFianacingActivities = ffcf.new ValuePair("Net Cash Provided by Financing Activities", true, true);
		for ( FundFinancialsCashFlow.ValuePair p : financing ) {
			netFianacingActivities.addValue( p.getValue() );
		}
		values.add( ffcf.new ValuePair("", false, true) );
		values.add( netFianacingActivities );
		values.add( ffcf.new ValuePair("", false, true) );

		// OTHER
		boolean isOtherPresent = false;
		if ( !additionalFlows.isEmpty() ) {
			for ( FundFinancialsCashFlow.ValuePair p : additionalFlows.values() ) {
				if ( p.getValue() != 0 ) {
					isOtherPresent = true;
					break;
				} 
			}
		}
		if ( isOtherPresent ) {
			values.add( ffcf.new ValuePair("Other Activities", false, true) );
			final FundFinancialsCashFlow.ValuePair netOtherActivities = ffcf.new ValuePair("Net Cash Provided by Other Activities", true, true);
			for ( FundFinancialsCashFlow.ValuePair p : additionalFlows.values() ) {
				if ( p.getValue() != 0 ) {
					values.add( ffcf.new ValuePair("", false, true) );
					values.add( p );
				}
			}
			values.add( ffcf.new ValuePair("", false, true) );
			values.add( netOtherActivities );
		}

		// SUMMING
        for (FundFinancialsCashFlow.ValuePair p : operating){
        	netChangeCashEquivalents.addValue(p.getValue());
        }
        for (FundFinancialsCashFlow.ValuePair p : investing){
        	netChangeCashEquivalents.addValue(p.getValue());
        }
        for (FundFinancialsCashFlow.ValuePair p : financing){
        	netChangeCashEquivalents.addValue(p.getValue());
        }
        for (FundFinancialsCashFlow.ValuePair p : additionalFlows.values()){
        	netChangeCashEquivalents.addValue(p.getValue());
        }
        values.add( ffcf.new ValuePair("", false, true) );
		values.add( netChangeCashEquivalents );
		values.add( ffcf.new ValuePair("", false, true) );
		values.add( beginCF );
		endCF.addValue(beginCF.getValue());
		endCF.addValue(netChangeCashEquivalents.getValue());
		values.add( ffcf.new ValuePair("", false, true) );
		values.add( endCF );
		
		return ffcf;
	}

	@Override
	public ItemsDetails createDetails(DAODetailsParams params)
			throws PcmsServiceException {
		final List<JournalEntryItem> items = getDataProvider().getDetails( params );
		final ItemsDetails result = new ItemsDetails();
		
		//TODO: implement
		int pageSize = 10;
		int totalPages = ( (items.size()-1) / pageSize ) + 1;
		if ( totalPages == 0 ) totalPages = 1;
		int page = params.getPage();
		if ( page > totalPages ) page = totalPages;
		if ( page < 1 ) page = 1;
		int start = ( page - 1 ) * pageSize;
		int end = Math.min( page * pageSize, items.size() );
		for ( int i = start; i < end; i++ ) {
			result.addItem( items.get(i) );
		}
  	
		result.setStartDate(params.getStartDate());
		result.setEndDate(params.getEndDate());
		result.setTotalPages(totalPages);
		result.setPage(page);
		
		return result;
	}
}
