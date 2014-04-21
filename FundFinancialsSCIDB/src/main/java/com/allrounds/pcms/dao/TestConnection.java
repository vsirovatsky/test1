package com.allrounds.pcms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import java.util.Set;
import java.util.TreeSet;

import org.scidb.jdbc.IResultSetWrapper;
import org.scidb.jdbc.IStatementWrapper;

import com.allrounds.pcms.domain.JournalEntryItem;

public class TestConnection {

	//private String query = "select * from test3 WHERE (jeidate > 115) AND (jeidate < 150)";
	private String query = "select * from test3";
	
	public TestConnection() {}
	
	public List<String> getTestInformationAQL(){
		return getTestInformationImpl( this.query, false );
	}
	
	public List<String> getTestInformationAFL(){
		return getTestInformationImpl( this.query, true );
	}
	
	private List<String> getTestInformationImpl( String query, boolean isAFL ){
		final List<String> result = new ArrayList<String>();
		result.add("Testing query: " + query);
		result.add("isAFL: " + isAFL);
		
		try
	    {
	      Class.forName("org.scidb.jdbc.Driver");
	    }
	    catch (ClassNotFoundException e)
	    {
	    	result.add("Driver is not in the CLASSPATH -> " + e);
	    	return result;
	    }
		
		Connection conn = null;
		try
	    {
		  conn = DriverManager.getConnection("jdbc:scidb://localhost/");
	      Statement st = conn.createStatement();
	      
	      if ( isAFL ) {
	    	  IStatementWrapper stWrapper = st.unwrap(IStatementWrapper.class);
	    	  stWrapper.setAfl(true);
	      }
	      
	      // create array A<a:string>[x=0:2,3,0, y=0:2,3,0];
	      // select * into A from
	      // array(A, '[["a","b","c"]["d","e","f"]["123","456","789"]]');
	      //ResultSet res = st.executeQuery("select * from array(<a:string>[x=0:2,3,0, y=0:2,3,0],'[[\"a\",\"b\",\"c\"][\"d\",\"e\",\"f\"][\"123\",\"456\",\"789\"]]')");
	      ResultSet res = st.executeQuery( query );
	      ResultSetMetaData meta = res.getMetaData();

	      result.add("Source array name: " + meta.getTableName(0));
	      result.add(meta.getColumnCount() + " columns: ");

	      IResultSetWrapper resWrapper = res.unwrap(IResultSetWrapper.class);
	      final Set<String> columns = new TreeSet<String>();
	      for (int i = 1; i <= meta.getColumnCount(); i++)
	      {
	    	  columns.add( meta.getColumnName(i) );
	    	  result.add(meta.getColumnName(i) + " - " + meta.getColumnTypeName(i)
	           + " - is attribute:" + resWrapper.isColumnAttribute(i));
	      }
	      result.add("===== DATA: =====");

	      
	      while(!res.isAfterLast())
	      {
	    	  //result.add("investor: " + res.getString("investor") + ", debit: " + res.getDouble("debit") + ", credit: " + res.getDouble("credit") + ", on " + res.getInt("jeidate"));
	    	  result.add("sum: " + res.getDouble("sum"));
	          res.next();
	      }
	    }
	    catch (SQLException e)
	    {
	    	result.add(e.getMessage());
	    	result.add(e.getSQLState());
	    } finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				result.add(e.getMessage());
		    	result.add(e.getSQLState());
			}
	    }
		
		return result;
	}

	public void setQuery(String testquery) {
		this.query = testquery;
	}
	
	public String getQuery() {
		return this.query;
	}
	
	public List<String> getAction(){
		final List<String> result = new ArrayList<String>();
		
		try
	    {
	      Class.forName("org.scidb.jdbc.Driver");
	    }
	    catch (ClassNotFoundException e)
	    {
	    	result.add("Driver is not in the CLASSPATH -> " + e);
	    	return result;
	    }
		
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
	    	  result.add("index: " + index + " " + resChartsCats.getString("chart") + " " + resChartsCats.getString("chartcategory"));
	    	  resChartsCats.next();
	      }
	      
	      
	      ResultSet res = st.executeQuery( "select chart_id,sum(debit),sum(credit) from test2 group by chart_id" );
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
	    	  result.add("item: " + item.getChartofaccounts() + " " + item.getChartcategory() + " " + item.getDebit() + " " + item.getCredit());
	          res.next();
	      }
	      st.close();
	    }
	    catch (Exception e)
	    {
	    	result.add( e.getMessage() );
	    } finally {
	    	try {
				conn.close();
			} catch (Exception e) {
				result.add( e.getMessage() );
			}
	    }
		return result;
	}

	public List<String> getAction1() {
		final List<String> result = new ArrayList<String>();
		
		try
	    {
	      Class.forName("org.scidb.jdbc.Driver");
	    }
	    catch (ClassNotFoundException e)
	    {
	    	result.add("Driver is not in the CLASSPATH -> " + e);
	    	return result;
	    }
		
		Connection conn = null;
		Statement st = null;
		try
	    {
		  conn = DriverManager.getConnection("jdbc:scidb://localhost/");
	      st = conn.createStatement();
	      
	      //IStatementWrapper stWrapper = st.unwrap(IStatementWrapper.class);
    	  //stWrapper.setAfl(true);
	      result.add("===== DATA: =====");
	      
	      printForCategory(st, result, "Accounts Payable");
	      printForCategory(st, result, "Accounts Receivable");
	      printForCategory(st, result, "Cash");
	      printForCategory(st, result, "Equity");
	      printForCategory(st, result, "Expense");
	      printForCategory(st, result, "Income");
	      printForCategory(st, result, "Non Marketable Securities");
	      printForCategory(st, result, "Other Current Asset");
	      printForCategory(st, result, "Other Current Liability");
	      printForCategory(st, result, "Other Expense");
	      printForCategory(st, result, "Other Income");
	      printForCategory(st, result, "Realized Gain");
	      printForCategory(st, result, "Short Term Asset");
	      
	      long ct1 = System.currentTimeMillis();
	      final ResultSet resTotal = st.executeQuery( "SELECT sum(debit),sum(credit),count(*) FROM diamondhead" );
	      StringBuilder sb = new StringBuilder();
	      while(!resTotal.isAfterLast()) {
	    	  sb.append("ALL RECORDS (");
	    	  sb.append(resTotal.getDouble("count_2"));
	    	  sb.append("): sum of debits: ");
	    	  sb.append(resTotal.getDouble("sum"));
	    	  sb.append(", sum of credits: ");
	    	  sb.append(resTotal.getDouble("sum_1"));
	    	  resTotal.next();
	      }
	      long ct2 = System.currentTimeMillis();
	      sb.append( ", time (miliseconds): " );
		  sb.append( ct2 - ct1 );
		  result.add( sb.toString() );
	    } catch (SQLException e) {
  	    	result.add(e.getMessage());
  	    	result.add(e.getSQLState());
  	    } finally {
  	    	try {
  	    		if (st!=null) st.close();
  				if (conn!=null) conn.close();
  			} catch (SQLException e) {
  				result.add(e.getMessage());
  		    	result.add(e.getSQLState());
  			}
  	    }
		return result;
	}
	
	private void printForCategory( Statement st, List<String> result, String category ) throws SQLException {
		long ct1 = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT sum(debit),sum(credit),count(*) FROM diamondhead WHERE chartcategory = '");
		sb.append( category );
		sb.append( '\'' );
		final ResultSet res = st.executeQuery( sb.toString() );
		
		sb = new StringBuilder();
		while(!res.isAfterLast())
	    {
			sb.append(category);
			sb.append('(');
			sb.append(res.getDouble("count_2"));
			sb.append("): ");
	        sb.append("sum of debits: ");
	        sb.append(res.getDouble("sum"));
	        sb.append(", sum of credits: ");
	        sb.append(res.getDouble("sum_1"));
	        res.next();
	    }
		long ct2 = System.currentTimeMillis();
		
		sb.append( ", time (miliseconds): " );
		sb.append( ct2 - ct1 );
		result.add( sb.toString() );
	}
}
