package java.lang.annotation;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Pragma {
    String value();    // version, such as "0.8.18"
    String minVersion() default "";  // minimum compatible version
    String maxVersion() default "";  // maximum compatible version
}