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
					String sortID = getKeyValue(uriInfo.getKeyPredicates().get(0));
					return EntityProvider.writeFeed(contentType, entitySet, DataStore.getNumbersForSort(sortID), EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
				}
			}
			throw new ODataNotImplementedException();
	}
	  
	  private String getKeyValue(KeyPredicate key) throws ODataException {
		  EdmProperty property = key.getProperty();
		  EdmSimpleType type = (EdmSimpleType) property.getType();
		  return type.valueOfString(key.getLiteral(), EdmLiteralKind.DEFAULT, property.getFacets(), String.class);
		    }
	  

	
/*	@SuppressWarnings("unchecked")
	@EdmFunctionImport(name="SelectionSort", entitySet="SortNumbers", returnType=@ReturnType(type=Type.ENTITY, isCollection = true))
	public List<Integer> selectionSort(@EdmFunctionImportParameter(name="numberList")){
		
	}*/
}
