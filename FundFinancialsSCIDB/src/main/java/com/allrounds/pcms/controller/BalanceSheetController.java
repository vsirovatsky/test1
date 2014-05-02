package com.allrounds.pcms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.allrounds.pcms.dao.TestConnection;

@Controller
@RequestMapping("/balancesheet")
public class BalanceSheetController {

	private static final Logger logger = LoggerFactory.getLogger(BalanceSheetController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		//final IFundFinancialsBalanceSheet bs = FundFinancialsBalanceSheetFactory.INSTANCE.generate();
		
		//model.addAttribute("balance", bs );
		
		final TestConnection tc = new TestConnection();
		final List<String> messageslist = new ArrayList<String>();
		messageslist.add( "ENTER QUERY AND RUN" );
		model.addAttribute("testquery", tc.getQuery() );
		model.addAttribute("messageslist", messageslist );
		
		return "balancesheet";
	}
	
	@RequestMapping(method = RequestMethod.POST, params = { "aql" } )
	public String sumbitFormAQL(@RequestParam("testquery") String testquery, @RequestParam String aql, Model model) {
		
		final TestConnection tc = new TestConnection();
		tc.setQuery( testquery );
		final List<String> messageslist = tc.getTestInformationAQL();
		model.addAttribute("testquery", tc.getQuery() );
		model.addAttribute("messageslist", messageslist );
		
		return "balancesheet";
	}
	
	@RequestMapping(method = RequestMethod.POST, params = { "afl" } )
	public String sumbitFormAFL(@RequestParam("testquery") String testquery, @RequestParam String afl, Model model) {
		
		final TestConnection tc = new TestConnection();
		tc.setQuery( testquery );
		final List<String> messageslist = tc.getTestInformationAFL();
		model.addAttribute("testquery", tc.getQuery() );
		model.addAttribute("messageslist", messageslist );
		
		return "balancesheet";
	}
	
	@RequestMapping(method = RequestMethod.POST, params = { "testaction" } )
	public String sumbitTestAction(@RequestParam("testquery") String testquery, @RequestParam String testaction, Model model) {
		try {
			final TestConnection tc = new TestConnection();
			tc.setQuery( testquery );
			final List<String> messageslist = tc.getAction();
			model.addAttribute("testquery", tc.getQuery() );
			model.addAttribute("messageslist", messageslist );
		} catch ( Exception ex ) {
			List<String> messageslist = new ArrayList<String>();
			messageslist.add( ex.getMessage() );
			model.addAttribute("testquery", "" );
			model.addAttribute("messageslist", messageslist );
		}
		return "balancesheet";
	}
	
}
