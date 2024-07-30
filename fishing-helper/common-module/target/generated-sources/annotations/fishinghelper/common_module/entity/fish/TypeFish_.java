package fishinghelper.common_module.entity.fish;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TypeFish.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class TypeFish_ {

	public static final String NAME = "name";
	public static final String ID = "id";

	
	/**
	 * @see fishinghelper.common_module.entity.fish.TypeFish#name
	 **/
	public static volatile SingularAttribute<TypeFish, String> name;
	
	/**
	 * @see fishinghelper.common_module.entity.fish.TypeFish#id
	 **/
	public static volatile SingularAttribute<TypeFish, Integer> id;
	
	/**
	 * @see fishinghelper.common_module.entity.fish.TypeFish
	 **/
	public static volatile EntityType<TypeFish> class_;

}

