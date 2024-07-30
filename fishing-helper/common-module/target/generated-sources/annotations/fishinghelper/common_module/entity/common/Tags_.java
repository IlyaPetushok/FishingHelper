package fishinghelper.common_module.entity.common;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Tags.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Tags_ {

	public static final String NAME = "name";
	public static final String ID = "id";

	
	/**
	 * @see fishinghelper.common_module.entity.common.Tags#name
	 **/
	public static volatile SingularAttribute<Tags, String> name;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Tags#id
	 **/
	public static volatile SingularAttribute<Tags, Integer> id;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Tags
	 **/
	public static volatile EntityType<Tags> class_;

}

