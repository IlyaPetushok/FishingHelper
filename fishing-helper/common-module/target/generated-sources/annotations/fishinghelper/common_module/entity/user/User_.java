package fishinghelper.common_module.entity.user;

import fishinghelper.common_module.entity.common.Article;
import fishinghelper.common_module.entity.common.Comment;
import fishinghelper.common_module.entity.common.Survey;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(User.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class User_ {

	public static final String PRIVILEGES = "privileges";
	public static final String COMMENTS = "comments";
	public static final String MAIL = "mail";
	public static final String ROLES = "roles";
	public static final String SURVEYS = "surveys";
	public static final String MAIL_STATUS = "mailStatus";
	public static final String LOGIN = "login";
	public static final String ARTICLE = "article";
	public static final String PASSWORD = "password";
	public static final String NAME = "name";
	public static final String DATE_REGISTRATION = "dateRegistration";
	public static final String ID = "id";

	
	/**
	 * @see fishinghelper.common_module.entity.user.User#privileges
	 **/
	public static volatile ListAttribute<User, Privileges> privileges;
	
	/**
	 * @see fishinghelper.common_module.entity.user.User#comments
	 **/
	public static volatile ListAttribute<User, Comment> comments;
	
	/**
	 * @see fishinghelper.common_module.entity.user.User#mail
	 **/
	public static volatile SingularAttribute<User, String> mail;
	
	/**
	 * @see fishinghelper.common_module.entity.user.User#roles
	 **/
	public static volatile ListAttribute<User, Role> roles;
	
	/**
	 * @see fishinghelper.common_module.entity.user.User#surveys
	 **/
	public static volatile ListAttribute<User, Survey> surveys;
	
	/**
	 * @see fishinghelper.common_module.entity.user.User#mailStatus
	 **/
	public static volatile SingularAttribute<User, Boolean> mailStatus;
	
	/**
	 * @see fishinghelper.common_module.entity.user.User#login
	 **/
	public static volatile SingularAttribute<User, String> login;
	
	/**
	 * @see fishinghelper.common_module.entity.user.User#article
	 **/
	public static volatile ListAttribute<User, Article> article;
	
	/**
	 * @see fishinghelper.common_module.entity.user.User#password
	 **/
	public static volatile SingularAttribute<User, String> password;
	
	/**
	 * @see fishinghelper.common_module.entity.user.User#name
	 **/
	public static volatile SingularAttribute<User, String> name;
	
	/**
	 * @see fishinghelper.common_module.entity.user.User#dateRegistration
	 **/
	public static volatile SingularAttribute<User, Date> dateRegistration;
	
	/**
	 * @see fishinghelper.common_module.entity.user.User#id
	 **/
	public static volatile SingularAttribute<User, Integer> id;
	
	/**
	 * @see fishinghelper.common_module.entity.user.User
	 **/
	public static volatile EntityType<User> class_;

}

