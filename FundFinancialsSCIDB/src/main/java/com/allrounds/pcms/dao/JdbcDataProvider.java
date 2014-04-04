package com.allrounds.pcms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.allrounds.pcms.domain.ChartOfAccount;
import com.allrounds.pcms.domain.JournalEntry;
import com.allrounds.pcms.domain.JournalEntryItem;
import com.allrounds.pcms.domain.RegisteredInvestor;

public class JdbcDataProvider implements IDataProvider {
	
	private List<JournalEntryItem> jeis;
	
	public JdbcDataProvider() throws ClassNotFoundException{
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
		if ( this.jeis == null ) {
			fillUpTheData();
		}
		return this.jeis;
	}
	
	private synchronized void fillUpTheData(){
		if ( this.jeis != null ) return;
		List<JournalEntryItem> newJeis = new ArrayList<JournalEntryItem>(150000);
		
		Connection conn = null;
		try
	    {
		  conn = DriverManager.getConnection("jdbc:scidb://localhost/");
	      Statement st = conn.createStatement();
	      
	      ResultSet res = st.executeQuery( "select * from diamondhead" );
	      //ResultSetMetaData meta = res.getMetaData();

	      //IResultSetWrapper resWrapper = res.unwrap(IResultSetWrapper.class);
	      while(!res.isAfterLast())
	      {
	    	  JournalEntryItem item = new JournalEntryItem();
	    	  item.setCredit( res.getDouble("credit") );
	    	  item.setDebit( res.getDouble("debit") );
	    	  item.setChartofaccounts( res.getString("chart") ); 
	    	  item.setInvestor( res.getString("investor") );
	    	  item.setJeId( res.getString("jeid") );
	    	  item.setChartcategory( res.getString("chartcategory") );
	    	  item.setDate(res.getInt("jeidate"));
	    	  newJeis.add( item );
	    	 //result.add("investor: " + res.getString("investor") + ", debit: " + res.getDouble("debit") + ", credit: " + res.getDouble("credit") + ", on " + res.getInt("jeidate"));
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
		
		this.jeis = newJeis;
	}

	@Override
	public List<JournalEntryItem> getAllItems(DAOParams params) {
		// TODO implement usage of dates
		return getAllItems();
	}

}
