package fishinghelper.common_module.entity.common;

import fishinghelper.common_module.entity.user.User;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Article.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Article_ {

	public static final String IMPORTANCE = "importance";
	public static final String TAGS_LIST = "tagsList";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String MISTAKES = "mistakes";
	public static final String ID = "id";
	public static final String USER = "user";
	public static final String PHOTOS = "photos";
	public static final String STATUS = "status";

	
	/**
	 * @see fishinghelper.common_module.entity.common.Article#importance
	 **/
	public static volatile SingularAttribute<Article, Boolean> importance;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Article#tagsList
	 **/
	public static volatile ListAttribute<Article, Tags> tagsList;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Article#name
	 **/
	public static volatile SingularAttribute<Article, String> name;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Article#description
	 **/
	public static volatile SingularAttribute<Article, String> description;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Article#mistakes
	 **/
	public static volatile ListAttribute<Article, Mistake> mistakes;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Article#id
	 **/
	public static volatile SingularAttribute<Article, Integer> id;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Article
	 **/
	public static volatile EntityType<Article> class_;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Article#user
	 **/
	public static volatile SingularAttribute<Article, User> user;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Article#photos
	 **/
	public static volatile ListAttribute<Article, Photo> photos;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Article#status
	 **/
	public static volatile SingularAttribute<Article, String> status;

}

