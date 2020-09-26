/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/*
WAsys_Java_type_util
File: Field_Filter_Entity.java
Created on: Sep 26, 2020 7:40:00 AM
    @author https://github.com/911992
 
History:
    initial version: 0.2.1(20200926)
        • To be used as typ-arg as field filter instead of plain `Field` type.
*/

package wasys.lib.java_type_util.reflect.type_sig;

import java.lang.reflect.Field;
import wasys.lib.java_type_util.reflect.type_sig.annotation.Field_Info;


/**
 * Entity class that holds related annotations, types and meta data related to a type's field.
 * <p>
 * This class is used specially for field filtering during a type parsing process. 
 * </p>
 * @author https://github.com/911992
 * @since 0.2.1
 */
public final class Field_Filter_Entity {
    /**
     * Holds the related field reflection ptr.
     */
    private Field field;
    
    /**
     * Holds the related annotation related to this field(if present).
     * <p>
     * It's null if
     * </p>
     */
    private Field_Info field_info;

    /**
     * Default constructor to set both field and related field_info.
     * @param arg_field the field ptr should be persisted (supposed to be non-null)
     * @param arg_field_info the field info(annotation) related to the field(if present)
     */
    public Field_Filter_Entity(Field arg_field, Field_Info arg_field_info) {
        this.field = arg_field;
        this.field_info = arg_field_info;
    }

    /**
     * Constructor to set the field ptr, but {@code null} for its {@link Field_Info} annotation.
     * @param arg_field the field ptr should be persisted (supposed to be non-null)
     */
    public Field_Filter_Entity(Field arg_field) {
        this(arg_field,null);
    }

    /**
     * (internal lib use) constructor to just initialize an instance with fields as null/default.
     */
    Field_Filter_Entity() {
    }
    
    /**
     * Returns the field this entity holds.
     * <p>
     * The returning value supposed to be non-{@code null}
     * </p>
     * @return the {@code field} field
     */
    public Field getField() {
        return field;
    }

    /**
     * Returns the field info of related field.
     * <p>
     * The return type is {@code null} if the related field has no any {@link Field_Info} annotated instance
     * </p>
     * @return the {@code field_info} field
     */
    public Field_Info getField_info() {
        return field_info;
    }

    /**
     * (internal lib use) Sets the field ptr of this instance.
     * @param field to be set to {@code field} field
     */
    void setField(Field field) {
        this.field = field;
    }

    /**
     * (internal lib use) Sets the field-info ptr of this instance.
     * @param field_info to be set to {@code field_info} field
     */
    void setField_info(Field_Info field_info) {
        this.field_info = field_info;
    }
    
}
