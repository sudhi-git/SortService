package com.sudhi.learning;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.exception.ODataNotImplementedException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetFunctionImportUriInfo;

import static com.sudhi.learning.SortMetaData.ENTITY_SET_NAME_NUMBERS;
import static com.sudhi.learning.SortMetaData.ENTITY_SET_NAME_SORTIDS;

public class SortProcessor extends ODataSingleProcessor{
	  @Override
	public ODataResponse readEntitySet(GetEntitySetUriInfo uriInfo, String contentType) throws ODataException {

			EdmEntitySet entitySet;
			if (uriInfo.getNavigationSegments().size() == 0) {
			  entitySet = uriInfo.getStartEntitySet();
			  DataStore.fillStaticData();
			  if (ENTITY_SET_NAME_SORTIDS.equals(entitySet.getName())) {
			    return EntityProvider.writeFeed(contentType, entitySet, DataStore.getSortID(), EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
			  } else if (ENTITY_SET_NAME_NUMBERS.equals(entitySet.getName())) {
			    return EntityProvider.writeFeed(contentType, entitySet, DataStore.getNumberList(), EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
			  }
			  throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
			}else if(uriInfo.getNavigationSegments().size() == 1){
				entitySet = uriInfo.getTargetEntitySet();
				if(ENTITY_SET_NAME_NUMBERS.equals(entitySet.getName())){
					String sortID = String.valueOf(getKeyValue(uriInfo.getKeyPredicates().get(0), "string"));
					return EntityProvider.writeFeed(contentType, entitySet, DataStore.getNumbersForSort(sortID), EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
				}
			}
			throw new ODataNotImplementedException();
	}
	  
	  @Override
	public ODataResponse readEntity(GetEntityUriInfo uriInfo, String contentType) throws ODataException {
		  if (uriInfo.getNavigationSegments().size() == 1) {
			  //navigation first level, simplified example for illustration purposes only
			  EdmEntitySet entitySet = uriInfo.getTargetEntitySet();
			  if (ENTITY_SET_NAME_SORTIDS.equals(entitySet.getName())) {
				  int numberID = Integer.valueOf(String.valueOf(getKeyValue(uriInfo.getKeyPredicates().get(0), "int")));
					return EntityProvider.writeFeed(contentType, entitySet, DataStore.getSortIDForNumber(numberID), EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
			  }
		  }
		  throw new ODataNotImplementedException();
	}

	private Object getKeyValue(KeyPredicate key, String typeRequest) throws ODataException {
		  EdmProperty property = key.getProperty();
		  Object obj = new Object();
		  EdmSimpleType type = (EdmSimpleType) property.getType();
		  if(typeRequest=="string"){
			  obj = type.valueOfString(key.getLiteral(), EdmLiteralKind.DEFAULT, property.getFacets(), String.class);  
		  }else if(typeRequest=="int"){
			  obj = type.valueOfString(key.getLiteral(), EdmLiteralKind.DEFAULT, property.getFacets(), Integer.class);
		  }
		  return obj;
	}

	@Override
	public ODataResponse executeFunctionImport(GetFunctionImportUriInfo uriInfo, String contentType)
			throws ODataException {
		// TODO Auto-generated method stub
		return super.executeFunctionImport(uriInfo, contentType);
	}
	  
	

}
