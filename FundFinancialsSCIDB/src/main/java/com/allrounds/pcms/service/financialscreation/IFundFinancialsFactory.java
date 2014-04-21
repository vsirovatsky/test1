package com.allrounds.pcms.service.financialscreation;

import com.allrounds.pcms.dao.DAODetailsParams;
import com.allrounds.pcms.service.FundFinancials;
import com.allrounds.pcms.service.ItemsDetails;
import com.allrounds.pcms.service.PcmsServiceException;

public interface IFundFinancialsFactory {
	
	FundFinancials createFundFinancials( FundFinancialsParameters params ) throws PcmsServiceException;
	
	ItemsDetails createDetails ( DAODetailsParams params ) throws PcmsServiceException;
}
