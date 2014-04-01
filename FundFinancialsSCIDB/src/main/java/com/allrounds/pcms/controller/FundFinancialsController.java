package com.allrounds.pcms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.allrounds.pcms.service.FundFinancials;
import com.allrounds.pcms.service.PcmsServiceException;
import com.allrounds.pcms.service.financialscreation.FundFinancialsParameters;
import com.allrounds.pcms.service.financialscreation.IFundFinancialsFactory;

@Controller
@RequestMapping("/fundfinancials")
public class FundFinancialsController {
	
	private static final Logger logger = LoggerFactory.getLogger(FundFinancialsController.class);
	
	@Autowired
	private IFundFinancialsFactory fundFinancialsFactory;

	@RequestMapping(method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.debug("FundFinancialsController called");
		long t1 = System.currentTimeMillis();
		FundFinancials ff = null;
		final List<String> messages = new ArrayList<String>();
		try {
			final FundFinancialsParameters params = new FundFinancialsParameters();
			ff = fundFinancialsFactory.createFundFinancials( params );
			model.addAttribute("ff", ff );
		} catch (PcmsServiceException e) {
			messages.add(e.toString());
		}
		long t2 = System.currentTimeMillis();
		model.addAttribute("messages", messages);
		model.addAttribute("runningtime", (t2 - t1) );
		logger.debug("FundFinancialsController performed");
		
		return "fundfinancials";
	}
}
