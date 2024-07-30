package fishinghelper.common_module.entity.place;

import fishinghelper.common_module.entity.common.Comment;
import fishinghelper.common_module.entity.common.Mistake;
import fishinghelper.common_module.entity.common.Photo;
import fishinghelper.common_module.entity.common.Survey;
import fishinghelper.common_module.entity.fish.Fish;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Place.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Place_ {

	public static final String OWNER = "owner";
	public static final String COMMENTS = "comments";
	public static final String COORDINATES = "coordinates";
	public static final String REQUIRE_PAYMENT = "requirePayment";
	public static final String RATING = "rating";
	public static final String SURVEYS = "surveys";
	public static final String DESCRIPTION = "description";
	public static final String MISTAKES = "mistakes";
	public static final String PHOTOS = "photos";
	public static final String TYPE_PLACE = "typePlace";
	public static final String FISH = "fish";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String STATUS = "status";

	
	/**
	 * @see fishinghelper.common_module.entity.place.Place#owner
	 **/
	public static volatile SingularAttribute<Place, Owner> owner;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Place#comments
	 **/
	public static volatile ListAttribute<Place, Comment> comments;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Place#coordinates
	 **/
	public static volatile SingularAttribute<Place, String> coordinates;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Place#requirePayment
	 **/
	public static volatile SingularAttribute<Place, Boolean> requirePayment;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Place#rating
	 **/
	public static volatile SingularAttribute<Place, Integer> rating;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Place#surveys
	 **/
	public static volatile ListAttribute<Place, Survey> surveys;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Place#description
	 **/
	public static volatile SingularAttribute<Place, String> description;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Place#mistakes
	 **/
	public static volatile ListAttribute<Place, Mistake> mistakes;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Place#photos
	 **/
	public static volatile ListAttribute<Place, Photo> photos;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Place#typePlace
	 **/
	public static volatile SingularAttribute<Place, TypePlace> typePlace;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Place#fish
	 **/
	public static volatile ListAttribute<Place, Fish> fish;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Place#name
	 **/
	public static volatile SingularAttribute<Place, String> name;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Place#id
	 **/
	public static volatile SingularAttribute<Place, Integer> id;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Place
	 **/
	public static volatile EntityType<Place> class_;
	
	/**
	 * @see fishinghelper.common_module.entity.place.Place#status
	 **/
	public static volatile SingularAttribute<Place, String> status;

}

