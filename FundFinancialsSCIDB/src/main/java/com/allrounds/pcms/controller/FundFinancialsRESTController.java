package com.allrounds.pcms.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(value = "financials/full", method = RequestMethod.GET)
	@ResponseBody
	public FundFinancials getRestMembers(Model model) {
		try {
			return this.fundFinancialsFactory.createFundFinancials( new FundFinancialsParameters() );
		} catch (PcmsServiceException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "financials/dates", method = RequestMethod.GET)
	@ResponseBody
	public FundFinancials getRestMembersForDates(Model model, @PathVariable("startdate") String startDate,  @PathVariable("enddate") String endDate) {
		try {
			final FundFinancialsParameters params = new FundFinancialsParameters();
			params.setStartDate(Date.valueOf(startDate));
			params.setEndDate(Date.valueOf(endDate));
			return this.fundFinancialsFactory.createFundFinancials( params );
		} catch (PcmsServiceException e) {
			e.printStackTrace();
			return null;
		}
	}
}
