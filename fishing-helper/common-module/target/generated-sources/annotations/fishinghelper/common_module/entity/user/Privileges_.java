package fishinghelper.common_module.entity.user;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Privileges.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Privileges_ {

	public static final String NAME = "name";
	public static final String ID = "id";

	
	/**
	 * @see fishinghelper.common_module.entity.user.Privileges#name
	 **/
	public static volatile SingularAttribute<Privileges, String> name;
	
	/**
	 * @see fishinghelper.common_module.entity.user.Privileges#id
	 **/
	public static volatile SingularAttribute<Privileges, Integer> id;
	
	/**
	 * @see fishinghelper.common_module.entity.user.Privileges
	 **/
	public static volatile EntityType<Privileges> class_;

}

