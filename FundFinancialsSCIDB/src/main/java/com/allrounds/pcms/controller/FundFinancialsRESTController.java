package com.allrounds.pcms.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allrounds.pcms.dao.DAODetailsParams;
import com.allrounds.pcms.dao.DAODetailsParams.TAB_NAME;
import com.allrounds.pcms.service.FundFinancials;
import com.allrounds.pcms.service.ItemsDetails;
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
	
	@RequestMapping(value = "financials/dates/{startdate}/{enddate}", method = RequestMethod.GET)
	@ResponseBody
	public FundFinancials getRestMembersForDates(Model model, @PathVariable("startdate") String startDate,  @PathVariable("enddate") String endDate) {
		try {
			final FundFinancialsParameters params = new FundFinancialsParameters();
			if ((startDate != null) && !startDate.equals("blank")) params.setStartDate( Date.valueOf(startDate) );
			if ((endDate != null) && !startDate.equals("blank")) params.setEndDate( Date.valueOf(endDate) );
			return this.fundFinancialsFactory.createFundFinancials( params );
		} catch (PcmsServiceException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "financials/details/{tabid}/{chart}/{startdate}/{enddate}/{page}", method = RequestMethod.GET)
	@ResponseBody
	public ItemsDetails getRestDetailsForDates(Model model, @PathVariable("tabid") String tabid, @PathVariable("chart") String chart,
			@PathVariable("startdate") String startDate,  @PathVariable("enddate") String endDate, @PathVariable("page") String page ) {
		try {
			final DAODetailsParams params = new DAODetailsParams();
			if ((startDate != null) && !startDate.equals("blank")) params.setStartDate( Date.valueOf(startDate) );
			if ((endDate != null) && !startDate.equals("blank")) params.setEndDate( Date.valueOf(endDate) );
			params.setChart(chart);
			params.setPage(Integer.valueOf(page));
			params.setTab( (tabid == "1") ? TAB_NAME.BALANCE : ( (tabid == "2") ? TAB_NAME.STATEMENTS : ( (tabid == "3") ? TAB_NAME.PARTNER_CAPITAL : TAB_NAME.CASH_FLOW ) ) );
			return this.fundFinancialsFactory.createDetails( params );
		} catch (PcmsServiceException e) {
			e.printStackTrace();
			return null;
		}
	}
}
