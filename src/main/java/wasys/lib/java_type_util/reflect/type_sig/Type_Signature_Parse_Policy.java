/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/*
WAsys_Java_type_util
File: Type_Signature_Parse_Policy.java
Created on: Jul 3, 2020 2:46:37 PM
    @author https://github.com/911992
 
History:
    initial version: 0.1(20200701)
*/

package wasys.lib.java_type_util.reflect.type_sig;

import java.lang.reflect.Modifier;


/**
 * Hosts a policy about type scraping.
 * <p>
 * This type is immutable.
 * </p>
 * @author https://github.com/911992
 */
public class Type_Signature_Parse_Policy {
    /**
     * Access specifier for {@code public} fields.
     */
    public static final int ACCESS_SPECIFIER_PUBLIC = Modifier.PUBLIC;
    
    /**
     * Access specifier for {@code private} fields.
     */
    public static final int ACCESS_SPECIFIER_PRIVATE = Modifier.PRIVATE;
    
    /**
     * Access specifier for {@code protected} fields.
     */
    public static final int ACCESS_SPECIFIER_PROTECTED = Modifier.PROTECTED;
    
    /**
     * Access specifier for {@code package} fields.
     */
    public static final int ACCESS_SPECIFIER_PACKAGE = Type_Parser.PACKAGE_ACCESS_SPECIFIER;
    
    /**
     * Access specifier for {@code public}, {@code private}, or {@code protected} fields.
     */
    public static final int ACCESS_SPECIFIER_ALL = (ACCESS_SPECIFIER_PUBLIC | ACCESS_SPECIFIER_PRIVATE | ACCESS_SPECIFIER_PROTECTED | ACCESS_SPECIFIER_PACKAGE);
    
    /**
     * Default policy instance.
     * <p>It tells to scrap all {@code public}, {@code private}, or {@code protected} fields, excluding {@code transient}, and ones has inherited from parent(s)</p>
     */
    public static final Type_Signature_Parse_Policy DEFAULT_POLICY = new Type_Signature_Parse_Policy(ACCESS_SPECIFIER_ALL, false, false);
    
    /**
     * Specifies the allowed access specifier(s) of the field.
     */
    private final int access_specifier;
    
    /**
     * Specifies if {@code transient} fields are allowed, or should be ignored.
     */
    private final boolean include_transient;
    
    /**
     * Specifies if {@code static} fields are allowed, or should be ignored.
     */
    private final boolean include_static;
    
    /**
     * Specifies if the parent type(s) should be scraped or not.
     */
    private final boolean include_parent_fields;
    
    /**
     * Specifies the field order scraping when scraping inherited parent(s) are appreciated.
     */
    private final Field_Definition_Order field_order;

    /**
     * Default constructor for ignoring scrap for inherited type(s).
     * @param access_specifier field access specifier should be considered
     * @param include_transient tells if {@code transient} fields should be included
     * @param include_static tells if {@code static} fields should be included
     */
    public Type_Signature_Parse_Policy(int access_specifier, boolean include_transient, boolean include_static) {
        this(access_specifier, include_transient, include_static, false, null);
    }
    
    /**
     * Default constructor, including scrap for inherited types.
     * @param access_specifier field access specifier should be considered
     * @param include_transient tells if {@code transient} fields should be included
     * @param include_static tells if {@code static} fields should be included
     * @param field_order specifies the field scraping from parent-&gt;child, or viceversa
     */
    public Type_Signature_Parse_Policy(int access_specifier, boolean include_transient, boolean include_static, Field_Definition_Order field_order) {
        this(access_specifier, include_transient, include_static, true, field_order);
    }
    
    /**
     * (internal lib use) Default constructor.
     * @param access_specifier field access specifier should be considered
     * @param include_transient tells if {@code transient} fields should be included
     * @param include_static tells if {@code static} fields should be included
     * @param include_parent_fields tells if parent type(s) should be scraped or not
     * @param field_order specifies the field scraping from parent->child, or viceversa
     */
    private Type_Signature_Parse_Policy(int access_specifier, boolean include_transient, boolean include_static, boolean include_parent_fields, Field_Definition_Order field_order) {
        this.access_specifier = access_specifier;
        this.include_transient = include_transient;
        this.include_static = include_static;
        this.include_parent_fields = include_parent_fields;
        this.field_order = field_order;
    }    

    /**
     * Returns the mask of allowed access specifier(s).
     * <p>
     * Note: zero means no access-specifier, default {@code package} access in Java.
     * </p>
     * @return the {@code access_specifier} var
     */
    public int getAccess_specifier() {
        return access_specifier;
    }

    /**
     * Returns if {@code transient} fields should be included or not.
     * @return the {@code include_transient} var.
     */
    public boolean isInclude_transient() {
        return include_transient;
    }

    /**
     * Returns if {@code static} fields should be included or not.
     * @return the {@code include_static} var.
     */
    public boolean isInclude_static() {
        return include_static;
    }

    /**
     * Returns if scraping parent type(s) should be considered or not.
     * @return the {@code include_parent_fields} var.
     */
    public boolean isInclude_parent_fields() {
        return include_parent_fields;
    }
    
    /**
     * Returns the order of parent&lt;-&gt;child field scrap order.
     * <p>
     * <b>Note:</b> this returns a {@code null} when including parent fields is not required.
     * </p>
     * @return the {@code field_order} var.
     */
    public Field_Definition_Order getField_order() {
        return field_order;
    }
    
    
}
