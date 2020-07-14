/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

/**
 * For the case when no SQL is used
 *
 * @author MCREMOTE
 */
public class RowDataInvertB extends RowDataInvert {
    
    private String initialValue;

    public RowDataInvertB(
            String field_original_name,
            String field_nick_name,
            Object unitOrObject,
            boolean string,
            boolean visible,
            boolean important) {
        this.fieldOrigName = field_original_name;
        this.fieldNickName = field_nick_name;
        this.unitOrObject = unitOrObject;
        this.isString = string;
        this.visible = visible;
        this.important = important;
    }

    public RowDataInvertB(
            int type,
            String additionalInfo,
            String field_original_name,
            String field_nick_name,
            String unit,
            boolean string,
            boolean visible,
            boolean important) {
        //
        this.type = type;
        this.additionalInfo = additionalInfo;
        this.fieldOrigName = field_original_name;
        this.fieldNickName = field_nick_name;
        this.unitOrObject = unit;
        this.isString = string;
        this.visible = visible;
        this.important = important;
    }

}
