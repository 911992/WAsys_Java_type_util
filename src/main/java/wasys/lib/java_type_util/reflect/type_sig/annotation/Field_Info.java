/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/*
WAsys_Java_type_util
File: Field_Info.java
Created on: Sep 26, 2020 6:32:20 AM
    @author https://github.com/911992
 
History:
    initial version: 0.2.1(20200926)
        • Primary usage is about specifying customized info of a field for parsing, such as getter/setter methods, and marked as ignore
*/

package wasys.lib.java_type_util.reflect.type_sig.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import wasys.lib.java_type_util.reflect.type_sig.Type_Field_Signature;
import wasys.lib.java_type_util.reflect.type_sig.Type_Signature_Parse_Policy;


/**
 * Annotation to specify custom field info when needed.
 * <p>
 * By default, parser consider all fields inclusive, with default naming policy for finding getter and setter methods.
 * </p>
 * <p>
 * In case a field should a custom(out of default {@code getAaa} and {@code setAaa} naming way) getter, or setter function, it(the field) should be annotated using this annotation with desired info.
 * </p>
 * <p>
 * Not only getter and setter names, a field would be marked as skipped, or has a user meta data(as {@code String}) for easier identification when custom/managed field filtering is desired.
 * </p>
 * @author https://github.com/911992
 * @since 0.2.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Field_Info {
    /**
     * Tells if this field should be skipped during class parsing process.
     * <p>
     * <b>Important Note:</b> please mind skipped fields could be ignored(including in parsing) using related {@link Type_Signature_Parse_Policy} used for type parsing process.
     * </p>
     * @return {@code true} if this field should be ignored implicitly during type parsing, {@code false} otherwise
     */
    public boolean skip_this_field() default false;
    
    /**
     * Specifies the getter method's name of the related field.
     * <p>
     * By default, target getter function is identified by default {@code getAaa} naming policy.
     * </p>
     * <p>
     * The specified function MUST return an exact type of field, and accepts no any input argument.
     * </p>
     * @return name of the function that should be consider as getter routine for annotated field
     */
    public String getter_method() default "";
    
    /**
     * Specifies teh setter method's name of the related field.
     * <p>
     * By default, target setter function is identified by default {@code setAaa} naming policy.
     * </p>
     * <p>
     * The specified function MUST accepts only one input argument that is exact type of field. Return type is no matter if {@code void} or whatever.
     * </p>
     * @return name of the function that should be considered as setter routine for related field
     */
    public String setter_method() default "";
    
    /**
     * Holds user meta info.
     * <p>
     * This <i>could</i> be handy during custom field filtering, to filter fields come with a specific user's meta info.
     * </p>
     * <p>
     * This user meta info could be grabbed from related {@link Type_Field_Signature} after a type is parsed too.
     * </p>
     * @return the user's specified meta info
     */
    public String user_meta() default "";
}
