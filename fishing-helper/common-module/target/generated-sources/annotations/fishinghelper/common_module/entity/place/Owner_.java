package fishinghelper.common_module.entity.place;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Owner.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Owner_ {

	public static final String NUMBER = "number";
	public static final String PLACES = "places";
	public static final String NAME = "name";
	public static final String ID = "id";

	
	/**
	 * @see fishinghelper.common_module.entity.place.Owner#number
	 **/
	public static volatile SingularAttribute<Owner, String> number;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Owner#places
	 **/
	public static volatile ListAttribute<Owner, Place> places;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Owner#name
	 **/
	public static volatile SingularAttribute<Owner, String> name;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Owner#id
	 **/
	public static volatile SingularAttribute<Owner, Integer> id;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Owner
	 **/
	public static volatile EntityType<Owner> class_;

}

