package fishinghelper.common_module.entity.common;

import fishinghelper.common_module.entity.fish.Fish;
import fishinghelper.common_module.entity.place.Place;
import fishinghelper.common_module.entity.user.User;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Survey.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Survey_ {

	public static final String AFTERNOON = "afternoon";
	public static final String FISH_LIST = "fishList";
	public static final String ID = "id";
	public static final String PLACE = "place";
	public static final String EVENING = "evening";
	public static final String USER = "user";
	public static final String MORNING = "morning";

	
	/**
	 * @see fishinghelper.common_module.entity.common.Survey#afternoon
	 **/
	public static volatile SingularAttribute<Survey, Boolean> afternoon;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Survey#fishList
	 **/
	public static volatile ListAttribute<Survey, Fish> fishList;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Survey#id
	 **/
	public static volatile SingularAttribute<Survey, Integer> id;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Survey#place
	 **/
	public static volatile SingularAttribute<Survey, Place> place;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Survey#evening
	 **/
	public static volatile SingularAttribute<Survey, Boolean> evening;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Survey
	 **/
	public static volatile EntityType<Survey> class_;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Survey#user
	 **/
	public static volatile SingularAttribute<Survey, User> user;
	
	/**
	 * @see fishinghelper.common_module.entity.common.Survey#morning
	 **/
	public static volatile SingularAttribute<Survey, Boolean> morning;

}

