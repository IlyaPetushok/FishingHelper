package fishinghelper.common_module.entity.fish;

import fishinghelper.common_module.entity.common.Photo;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Fish.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Fish_ {

	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String TYPE_FISH = "typeFish";
	public static final String FOOD_FOR_FISH = "foodForFish";
	public static final String PHOTOS = "photos";

	
	/**
	 * @see fishinghelper.common_module.entity.fish.Fish#name
	 **/
	public static volatile SingularAttribute<Fish, String> name;
	
	/**
	 * @see fishinghelper.common_module.entity.fish.Fish#description
	 **/
	public static volatile SingularAttribute<Fish, String> description;
	
	/**
	 * @see fishinghelper.common_module.entity.fish.Fish#id
	 **/
	public static volatile SingularAttribute<Fish, Integer> id;
	
	/**
	 * @see fishinghelper.common_module.entity.fish.Fish#typeFish
	 **/
	public static volatile ListAttribute<Fish, TypeFish> typeFish;
	
	/**
	 * @see fishinghelper.common_module.entity.fish.Fish
	 **/
	public static volatile EntityType<Fish> class_;
	
	/**
	 * @see fishinghelper.common_module.entity.fish.Fish#foodForFish
	 **/
	public static volatile ListAttribute<Fish, FoodForFish> foodForFish;
	
	/**
	 * @see fishinghelper.common_module.entity.fish.Fish#photos
	 **/
	public static volatile ListAttribute<Fish, Photo> photos;

}

