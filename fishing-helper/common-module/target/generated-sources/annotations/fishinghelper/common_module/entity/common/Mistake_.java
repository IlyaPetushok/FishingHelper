package fishinghelper.common_module.entity.common;

import fishinghelper.common_module.entity.place.Place;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Mistake.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Mistake_ {

	public static final String ID = "id";
	public static final String TEXT = "text";
	public static final String PLACE = "place";
	public static final String ARTICLE = "article";

	
	/**
	 * @see fishinghelper.common_module.entity.common.Mistake#id
	 **/
	public static volatile SingularAttribute<Mistake, Integer> id;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Mistake#text
	 **/
	public static volatile SingularAttribute<Mistake, String> text;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Mistake#place
	 **/
	public static volatile SingularAttribute<Mistake, Place> place;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Mistake
	 **/
	public static volatile EntityType<Mistake> class_;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Mistake#article
	 **/
	public static volatile SingularAttribute<Mistake, Article> article;

}

