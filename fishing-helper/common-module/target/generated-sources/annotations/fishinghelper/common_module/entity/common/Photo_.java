package fishinghelper.common_module.entity.common;

import fishinghelper.common_module.entity.fish.Fish;
import fishinghelper.common_module.entity.place.Place;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Photo.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Photo_ {

	public static final String PHOTO_PATH = "photoPath";
	public static final String FISH = "fish";
	public static final String ID = "id";
	public static final String PLACE = "place";
	public static final String ARTICLE = "article";

	
	/**
	 * @see fishinghelper.common_module.entity.common.Photo#photoPath
	 **/
	public static volatile SingularAttribute<Photo, String> photoPath;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Photo#fish
	 **/
	public static volatile SingularAttribute<Photo, Fish> fish;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Photo#id
	 **/
	public static volatile SingularAttribute<Photo, Integer> id;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Photo#place
	 **/
	public static volatile SingularAttribute<Photo, Place> place;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Photo
	 **/
	public static volatile EntityType<Photo> class_;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Photo#article
	 **/
	public static volatile SingularAttribute<Photo, Article> article;

}

