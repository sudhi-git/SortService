package com.sudhi.learning;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.AssociationEnd;
import org.apache.olingo.odata2.api.edm.provider.AssociationSet;
import org.apache.olingo.odata2.api.edm.provider.AssociationSetEnd;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.edm.provider.EntityContainer;
import org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.Key;
import org.apache.olingo.odata2.api.edm.provider.NavigationProperty;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.PropertyRef;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.api.exception.ODataException;

public class SortMetaData extends EdmProvider{
	  static final String ENTITY_SET_NAME_SORTIDS = "SortIDs";
	  static final String ENTITY_NAME_SORTID = "SortID";
	  static final String ENTITY_SET_NAME_NUMBERS = "Numbers";
	  static final String ENTITY_NAME_NUMBER = "Number";
	  private static final String NAMESPACE = "com.sudhi.learning";
	  private static final FullQualifiedName ENTITY_TYPE_1_1 = new FullQualifiedName(NAMESPACE, ENTITY_NAME_SORTID);
	  private static final FullQualifiedName ENTITY_TYPE_1_2 = new FullQualifiedName(NAMESPACE, ENTITY_NAME_NUMBER);
	  private static final FullQualifiedName ASSOCIATION_SORT_NUMBERS = new FullQualifiedName(NAMESPACE, "Sort_To_Numbers");
	  private static final String ENTITY_CONTAINER = "SortNumbersEntityContainer";
	  private static final String ROLE_1_2 = "Sort_to_Numbers";
	  private static final String ROLE_1_1 = "Number_to_Sort";
	  private static final String ASSOCIATION_SET = "Sort_Numbers";

	  public List<Schema> getSchemas() throws ODataException {
		  List<Schema> schemas = new ArrayList<Schema>();

		  Schema schema = new Schema();
		  schema.setNamespace(NAMESPACE);

		  List<EntityType> entityTypes = new ArrayList<EntityType>();
		  entityTypes.add(getEntityType(ENTITY_TYPE_1_1));
		  entityTypes.add(getEntityType(ENTITY_TYPE_1_2));
		  schema.setEntityTypes(entityTypes);

		  List<EntityContainer> entityContainers = new ArrayList<EntityContainer>();
		  EntityContainer entityContainer = new EntityContainer();
		  entityContainer.setName(ENTITY_CONTAINER);

		  List<EntitySet> entitySets = new ArrayList<EntitySet>();
		  entitySets.add(getEntitySet(ENTITY_CONTAINER, ENTITY_SET_NAME_SORTIDS));
		  entitySets.add(getEntitySet(ENTITY_CONTAINER, ENTITY_SET_NAME_NUMBERS));
		  entityContainer.setEntitySets(entitySets);

		  List<Association> associations = new ArrayList<Association>();
		  associations.add(getAssociation(ASSOCIATION_SORT_NUMBERS));
		  schema.setAssociations(associations);

		  List<AssociationSet> associationSets = new ArrayList<AssociationSet>();
		  associationSets.add(getAssociationSet(ENTITY_CONTAINER, ASSOCIATION_SORT_NUMBERS, ENTITY_SET_NAME_NUMBERS, ROLE_1_2));
		  entityContainer.setAssociationSets(associationSets);
		  
		  entityContainers.add(entityContainer);
		  schema.setEntityContainers(entityContainers);

		  schemas.add(schema);

		  return schemas;
		  }

	  @Override
	  public EntityType getEntityType(FullQualifiedName edmFQName) throws ODataException {
		  if (NAMESPACE.equals(edmFQName.getNamespace())) {
			  if (ENTITY_TYPE_1_1.getName().equals(edmFQName.getName())) {

			    //Properties
			    List<Property> properties = new ArrayList<Property>();
			    properties.add(new SimpleProperty().setName("SortId").setType(EdmSimpleTypeKind.String));
		
			    //Key
			    List<PropertyRef> keyProperties = new ArrayList<PropertyRef>();
			    keyProperties.add(new PropertyRef().setName("SortId"));
			    Key key = new Key().setKeys(keyProperties);
			    
			    //Navigation Properties
			    List<NavigationProperty> navigationProperties = new ArrayList<NavigationProperty>();
			    navigationProperties.add(new NavigationProperty().setName("Numbers")
			        .setRelationship(ASSOCIATION_SORT_NUMBERS).setFromRole(ROLE_1_2).setToRole(ROLE_1_1));
			    
			    return new EntityType().setName(ENTITY_TYPE_1_1.getName())
			        .setProperties(properties)
			        .setNavigationProperties(navigationProperties)
			        .setKey(key);
			  } else if(ENTITY_TYPE_1_2.getName().equals(edmFQName.getName())){
			    //Properties
			    List<Property> properties = new ArrayList<Property>();
			    properties.add(new SimpleProperty().setName("NumberId").setType(EdmSimpleTypeKind.Int16));
	
			    //Key
			    List<PropertyRef> keyProperties = new ArrayList<PropertyRef>();
			    keyProperties.add(new PropertyRef().setName("NumberId"));
			    Key key = new Key().setKeys(keyProperties);
			    
			    return new EntityType().setName(ENTITY_TYPE_1_2.getName())
			        .setProperties(properties)
			        .setKey(key);		  
			  }
		  }
		  return null;
	}
	  
	  @Override
	  public EntitySet getEntitySet(String entityContainer, String name) throws ODataException {
		  if (ENTITY_CONTAINER.equals(entityContainer)) {
		    if (ENTITY_SET_NAME_NUMBERS.equals(name)) {
		      return new EntitySet().setName(name).setEntityType(ENTITY_TYPE_1_2);
		    }else if(ENTITY_SET_NAME_SORTIDS.equals(name)){
		    	return new EntitySet().setName(name).setEntityType(ENTITY_TYPE_1_1);
		    }
		  }
		  return null;
	}

	  public Association getAssociation(FullQualifiedName edmFQName) throws ODataException {
		  if (NAMESPACE.equals(edmFQName.getNamespace())) {
		    if (ASSOCIATION_SORT_NUMBERS.getName().equals(edmFQName.getName())) {
		      return new Association().setName(ASSOCIATION_SORT_NUMBERS.getName())
		          .setEnd1(new AssociationEnd().setType(ENTITY_TYPE_1_1).setRole(ROLE_1_1).setMultiplicity(EdmMultiplicity.MANY))
		          .setEnd2(new AssociationEnd().setType(ENTITY_TYPE_1_2).setRole(ROLE_1_2).setMultiplicity(EdmMultiplicity.ONE));
		    }
		  }
		  return null;
		  }

	  public AssociationSet getAssociationSet(String entityContainer, FullQualifiedName association, String sourceEntitySetName, String sourceEntitySetRole) throws ODataException {
		  if (ENTITY_CONTAINER.equals(entityContainer)) {
		    if (ASSOCIATION_SORT_NUMBERS.equals(association)) {
		      return new AssociationSet().setName(ASSOCIATION_SET)
		          .setAssociation(ASSOCIATION_SORT_NUMBERS)
		          .setEnd1(new AssociationSetEnd().setRole(ROLE_1_2).setEntitySet(ENTITY_SET_NAME_NUMBERS))
		          .setEnd2(new AssociationSetEnd().setRole(ROLE_1_1).setEntitySet(ENTITY_SET_NAME_SORTIDS));
		    }
		  }
		  return null;
		  }

	@Override
	public EntityContainerInfo getEntityContainerInfo(String name) throws ODataException {
		if (name == null || "SortNumbersEntityContainer".equals(name)) {
			  return new EntityContainerInfo().setName("SortNumbersEntityContainer").setDefaultEntityContainer(true);
			}
			return null;
	}
	  
	  
/*	@Override
	public FunctionImport getFunctionImport(String entityContainer, String name) throws ODataException {
		if(entityContainer==ENTITY_CONTAINER){
			FunctionImport fi = new FunctionImport();
			List<FunctionImportParameter> fipList = new ArrayList<FunctionImportParameter>();
			FunctionImportParameter fip = new FunctionImportParameter();
			fip.setName("Identifier");
			fip.setType(EdmSimpleTypeKind.Int16);
			fi.setName("SelectionSort");
			fi.setParameters(fipList);
		}
		return super.getFunctionImport(entityContainer, name);
	}*/
	


}
