package fishinghelper.common_module.entity.place;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TypePlace.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class TypePlace_ {

	public static final String PLACES = "places";
	public static final String NAME = "name";
	public static final String ID = "id";

	
	/**
	 * @see fishinghelper.common_module.entity.place.TypePlace#places
	 **/
	public static volatile ListAttribute<TypePlace, Place> places;
	
	/**
	 * @see fishinghelper.common_module.entity.place.TypePlace#name
	 **/
	public static volatile SingularAttribute<TypePlace, String> name;
	
	/**
	 * @see fishinghelper.common_module.entity.place.TypePlace#id
	 **/
	public static volatile SingularAttribute<TypePlace, Integer> id;
	
	/**
	 * @see fishinghelper.common_module.entity.place.TypePlace
	 **/
	public static volatile EntityType<TypePlace> class_;

}

