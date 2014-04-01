package com.allrounds.pcms.service;

public abstract class PcmsServiceException extends Throwable {
	
	private static final long serialVersionUID = 1436911901235265L;

	public static class CategoryNotFoundPcmsServiceException extends PcmsServiceException {
		private static final long serialVersionUID = -5614846321200078429L;}
	
}
