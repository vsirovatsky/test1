package com.allrounds.pcms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allrounds.pcms.service.FundFinancials;
import com.allrounds.pcms.service.PcmsServiceException;
import com.allrounds.pcms.service.financialscreation.FundFinancialsParameters;
import com.allrounds.pcms.service.financialscreation.IFundFinancialsFactory;

@Controller
@RequestMapping("api")
public class FundFinancialsRESTController {
	
	@Autowired
	private IFundFinancialsFactory fundFinancialsFactory;

	@RequestMapping("financials/full")
	@ResponseBody
	public FundFinancials getRestMembers(Model model) {
		try {
			return this.fundFinancialsFactory.createFundFinancials( new FundFinancialsParameters() );
		} catch (PcmsServiceException e) {
			e.printStackTrace();
			return null;
		}
	}
}
