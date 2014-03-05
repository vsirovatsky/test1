package com.allrounds.pcms.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.allrounds.pcms.service.FundFinancialsBalanceSheetFactory;
import com.allrounds.pcms.service.IFundFinancialsBalanceSheet;

@Controller
public class BalanceSheetController {

private static final Logger logger = LoggerFactory.getLogger(BalanceSheetController.class);
	
	@RequestMapping(value = "/balancesheet", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		final IFundFinancialsBalanceSheet bs = FundFinancialsBalanceSheetFactory.INSTANCE.generate();
		
		model.addAttribute("balance", bs );
		
		return "balancesheet";
	}
}
