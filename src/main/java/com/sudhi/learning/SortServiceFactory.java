package com.sudhi.learning;

import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;

public class SortServiceFactory extends ODataServiceFactory{

	@Override
	public ODataService createService(ODataContext ctx) throws ODataException {
		EdmProvider edm = new SortMetaData();
		ODataSingleProcessor sortProcessor = new SortProcessor();
		return createODataSingleProcessorService(edm, sortProcessor);
	}

}
