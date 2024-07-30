package fishinghelper.common_module.entity.fish;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(FoodForFish.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class FoodForFish_ {

	public static final String NAME = "name";
	public static final String ID = "id";

	
	/**
	 * @see fishinghelper.common_module.entity.fish.FoodForFish#name
	 **/
	public static volatile SingularAttribute<FoodForFish, String> name;
	
	/**
	 * @see fishinghelper.common_module.entity.fish.FoodForFish#id
	 **/
	public static volatile SingularAttribute<FoodForFish, Integer> id;
	
	/**
	 * @see fishinghelper.common_module.entity.fish.FoodForFish
	 **/
	public static volatile EntityType<FoodForFish> class_;

}

