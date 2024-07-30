package fishinghelper.common_module.entity.common;

import fishinghelper.common_module.entity.place.Place;
import fishinghelper.common_module.entity.user.User;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Comment.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Comment_ {

	public static final String GRADE = "grade";
	public static final String ID = "id";
	public static final String TEXT = "text";
	public static final String PLACE = "place";
	public static final String USER = "user";

	
	/**
	 * @see fishinghelper.common_module.entity.common.Comment#grade
	 **/
	public static volatile SingularAttribute<Comment, Integer> grade;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Comment#id
	 **/
	public static volatile SingularAttribute<Comment, Integer> id;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Comment#text
	 **/
	public static volatile SingularAttribute<Comment, String> text;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Comment#place
	 **/
	public static volatile SingularAttribute<Comment, Place> place;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Comment
	 **/
	public static volatile EntityType<Comment> class_;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Comment#user
	 **/
	public static volatile SingularAttribute<Comment, User> user;

}

