package fishinghelper.common_module.entity.user;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Role.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Role_ {

	public static final String PRIVILEGES = "privileges";
	public static final String NAME = "name";
	public static final String ID = "id";

	
	/**
	 * @see fishinghelper.common_module.entity.user.Role#privileges
	 **/
	public static volatile ListAttribute<Role, Privileges> privileges;
	
	/**
	 * @see fishinghelper.common_module.entity.user.Role#name
	 **/
	public static volatile SingularAttribute<Role, String> name;
	
	/**
	 * @see fishinghelper.common_module.entity.user.Role#id
	 **/
	public static volatile SingularAttribute<Role, Integer> id;
	
	/**
	 * @see fishinghelper.common_module.entity.user.Role
	 **/
	public static volatile EntityType<Role> class_;

}

