/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

/**
 *
 * @author KOCMOC
 */
public class ChooseLogoEntry {

    private boolean STATUS__REMOVED_AFTER_SETTING_LOGO = false;
    private boolean STATUS__LOGO_WAS_DELETED_BY_PLANNED = false;

    public void setSTATUS__LOGO_WAS_DELETED_BY_PLANNED(boolean STATUS__LOGO_WAS_DELETED_BY_PLANNED) {
        this.STATUS__LOGO_WAS_DELETED_BY_PLANNED = STATUS__LOGO_WAS_DELETED_BY_PLANNED;
    }

    public void setSTATUS__REMOVED_AFTER_SETTING_LOGO(boolean STATUS__REMOVED_AFTER_SETTING_LOGO) {
        this.STATUS__REMOVED_AFTER_SETTING_LOGO = STATUS__REMOVED_AFTER_SETTING_LOGO;
    }

    public boolean isSTATUS__LOGO_WAS_DELETED_BY_PLANNED() {
        return STATUS__LOGO_WAS_DELETED_BY_PLANNED;
    }

    public boolean isSTATUS__REMOVED_AFTER_SETTING_LOGO() {
        return STATUS__REMOVED_AFTER_SETTING_LOGO;
    }

}
