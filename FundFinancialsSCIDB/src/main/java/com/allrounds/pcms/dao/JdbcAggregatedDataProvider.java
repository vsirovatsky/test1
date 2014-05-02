package com.allrounds.pcms.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.allrounds.pcms.domain.ChartOfAccount;
import com.allrounds.pcms.domain.JournalEntry;
import com.allrounds.pcms.domain.JournalEntryItem;
import com.allrounds.pcms.domain.RegisteredInvestor;
import com.allrounds.pcms.utils.DateUtils;

public class JdbcAggregatedDataProvider implements IDataProvider {
	
	public JdbcAggregatedDataProvider() throws ClassNotFoundException{
		Class.forName("org.scidb.jdbc.Driver");
	}
	
	@Override
	public List<RegisteredInvestor> getAllInvestors() {
		return null;
	}

	@Override
	public List<ChartOfAccount> getAllCharts() {
		return null;
	}

	@Override
	public List<JournalEntry> getAllEntries() {
		return null;
	}

	@Override
	public List<JournalEntryItem> getAllItems() {
		return fillUpTheData( null );
	}

	@Override
	public List<JournalEntryItem> getAllItems(DAOParams params) {
		return fillUpTheData( params );
	}

	private List<JournalEntryItem> fillUpTheData( DAOParams params ){
		List<JournalEntryItem> newJeis = new ArrayList<JournalEntryItem>(100);
		
		Connection conn = null;
		try
	    {
		  conn = DriverManager.getConnection("jdbc:scidb://localhost/");
	      Statement st = conn.createStatement();
	      
	      ResultSet resChartsCats = st.executeQuery( "select chart_id,chart,chartcategory from test_chart_full_index");
	      HashMap<Long, String> chartNames = new HashMap<Long, String>();
	      HashMap<Long, String> chartCats = new HashMap<Long, String>();
	      while ( !resChartsCats.isAfterLast() ) {
	    	  Long index = resChartsCats.getLong("chart_id");
	    	  chartNames.put(index, resChartsCats.getString("chart"));
	    	  chartCats.put(index, resChartsCats.getString("chartcategory"));
	    	  resChartsCats.next();
	      }
	      
	      
	      ResultSet res = null;
	      if ( params.isDatesSet() ) {
	    	  int startDate = DateUtils.convertToInt(params.getStartDate(), Date.valueOf("2000-01-01"));
	  		  int endDate = DateUtils.convertToInt(params.getEndDate(), Date.valueOf("2000-01-01"));
	  		  res = st.executeQuery( "select chart_id,sum(debit),sum(credit) from test2 WHERE (jeidate >= " + startDate + ") AND (jeidate <= " + endDate + ") group by chart_id" );
	      } else {
	    	  res = st.executeQuery( "select chart_id,sum(debit),sum(credit) from test2 group by chart_id" );
	      }
	      //ResultSetMetaData meta = res.getMetaData();

	      //IResultSetWrapper resWrapper = res.unwrap(IResultSetWrapper.class);
	      while(!res.isAfterLast())
	      {
	    	  Long index = res.getLong("chart_id");
	    	  JournalEntryItem item = new JournalEntryItem();
	    	  item.setDebit( res.getDouble("sum") );
	    	  item.setCredit( res.getDouble("sum_1") );
	    	  item.setChartofaccounts( chartNames.get(index) ); 
	    	  item.setChartcategory( chartCats.get(index) );
	    	  newJeis.add( item );
	          res.next();
	      }
	      st.close();
	    }
	    catch (SQLException e)
	    {
	    	e.printStackTrace();
	    } finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
		
		return newJeis;
	}

	@Override
	public List<JournalEntryItem> getDetails(DAODetailsParams params) {
		List<JournalEntryItem> newJeis = new ArrayList<JournalEntryItem>(100);
		
		Connection conn = null;
		try
	    {
		  conn = DriverManager.getConnection("jdbc:scidb://localhost/");
	      Statement st = conn.createStatement();
	      
	      ResultSet resChartsCats = st.executeQuery( "select chart_id,chart,chartcategory from test_chart_full_index");
	      HashMap<String, Long> chartIndexes = new HashMap<String, Long>();
	      HashMap<Long, String> chartCats = new HashMap<Long, String>();
	      while ( !resChartsCats.isAfterLast() ) {
	    	  Long index = resChartsCats.getLong("chart_id");
	    	  chartIndexes.put(resChartsCats.getString("chart"), index);
	    	  chartCats.put(index, resChartsCats.getString("chartcategory"));
	    	  resChartsCats.next();
	      }
	      
	      ResultSet res = null;
	      Long id = chartIndexes.get( params.getChart() );
	      if ( id != null ) {
	    	  if ( params.isDatesSet() ) {
		    	  int startDate = DateUtils.convertToInt(params.getStartDate(), Date.valueOf("2000-01-01"));
		  		  int endDate = DateUtils.convertToInt(params.getEndDate(), Date.valueOf("2000-01-01"));
		  		  StringBuffer sb = new StringBuffer();
		  		  sb.append( "select credit, debit, jeid, jeidate from unpack(between(test2," );
		  		  sb.append( startDate );
		  		  sb.append( ',' );
		  		  sb.append( id );
			      sb.append( ",null," );
			      sb.append( endDate );
			      sb.append( ',' );
			      sb.append( id );
			      sb.append( ",null), jeidate)" );
			      String sql = sb.toString();
			      res = st.executeQuery(sql);
		      } else {
		    	  StringBuffer sb = new StringBuffer();
		  		  sb.append( "select credit, debit, jeid, jeidate from unpack(between(test2,null," );
		  		  sb.append( id );
			      sb.append( ",null,null," );
			      sb.append( id );
			      sb.append( ",null), jeidate)" );
			      String sql = sb.toString();
			      res = st.executeQuery(sql);
		      }
	      }
	      
	      while( (res!=null) && !res.isAfterLast() ) {
	    	  JournalEntryItem item = new JournalEntryItem();
	    	  item.setCredit( res.getDouble("credit") );
	    	  item.setDebit( res.getDouble("debit") );
	    	  //item.setInvestor( res.getString("investor") );
	    	  item.setJeId( res.getString("jeid") );
	    	  item.setChartofaccounts( params.getChart() ); 
	    	  item.setChartcategory( chartCats.get(id) );
	    	  item.setDate((int)res.getLong("jeidate"));
	    	  newJeis.add( item );
	    	 //result.add("investor: " + res.getString("investor") + ", debit: " + res.getDouble("debit") + ", credit: " + res.getDouble("credit") + ", on " + res.getInt("jeidate"));
	          res.next();
	      }
	      st.close();
	    }
	    catch (Exception e)
	    {
	    	JournalEntryItem item = new JournalEntryItem();
	    	  item.setCredit( 1 );
	    	  item.setDebit( 1 );
	    	  //item.setInvestor( res.getString("investor") );
	    	  item.setJeId( "1" );
	    	  item.setChartofaccounts( e.getMessage() ); 
	    	  item.setChartcategory( e.getMessage() );
	    	  item.setDate( 1 );
	    	  newJeis.add( item );
	    	e.printStackTrace();
	    } finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
		
		return newJeis;
	}
}
