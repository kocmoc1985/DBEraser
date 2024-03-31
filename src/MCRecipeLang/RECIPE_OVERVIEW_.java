/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipeLang;

import static MCRecipeLang.LNG.LANG_ENG;
import forall.HelpA;
import javax.swing.JTabbedPane;

/**
 *
 * @author KOCMOC
 */
public class RECIPE_OVERVIEW_ {

    //=======================RECIPE_OVEVIEW=====================================
    public static void RECIPE_OVERVIEW_TABS(JTabbedPane jtp) {
        if (LANG_ENG == false) {
            HelpA.changeTabName(jtp, "NOTES", "ЗАМЕТКИ"); // NOTIZEN
            HelpA.changeTabName(jtp, "NOTES DETAILS", "ЗАМЕТКИ, ПОДРОБНОСТИ"); // NOTIZEN DETALLIERT

        }
    }

    public static String RECIPE_OVERVIEW__RECIPE_ORIGIN() {
        return LANG_ENG ? "RECIPE ORIGIN" : "НАЗВАНИЕ РЕЦЕПТА"; // REZEPT ORIGINEL
    }

    public static String RECIPE_OVERVIEW__RECIPE_STAGE() {
        return LANG_ENG ? "RECIPE STAGE" : "СТАДИЯ РЕЦЕПТА"; // REZEPT PHASE
    }

    public static String RECIPE_OVERVIEW__RECIPE_VERSION() {
        return LANG_ENG ? "RECIPE VERSION" : "ВЕРСИЯ РЕЦЕПТА"; // REZEPT VERSION
    }

    public static String RECIPE_OVERVIEW__RECIPE_ADDITIONAL() {
        return LANG_ENG ? "RECIPE ADDITIONAL" : "РЕЦЕПТ ДОПОЛНЕНИЕ"; // REZEPT ZUSATZ
    }

    public static String RECIPE_OVERVIEW__RECIPE_DESCRIPTION() {
        return LANG_ENG ? "RECIPE DESCRIPTION" : "ОПИСАНИЕ РЕЦЕПТА";//REZEPT BESCHREIBUNG
    }

    public static String RECIPE_OVERVIEW__POLYMER_GROUP() {
        return LANG_ENG ? "POLYMER GROUP" : "ПОЛИМЕРНАЯ ГРУППА"; // POLYMER GRUPPE
    }

    public static String RECIPE_OVERVIEW__MIXER() {
        return LANG_ENG ? "MIXER" : "СМЕСИТЕЛЬ"; //MISCHER
    }

    public static String RECIPE_OVERVIEW__STATUS() {
        return LANG_ENG ? "STATUS" : "СТАТУС"; //STATUS
    }

    public static String RECIPE_OVERVIEW__CLASS() {
        return LANG_ENG ? "CLASS" : "КАТЕГОРИЯ"; //KATEGORIE
    }

    //===
    public static String RECIPE_OVERVIEW__INGREDIENTS_CHKBOX() {
        return LANG_ENG ? "Ingredients" : "ИНГРЕДИЕНТЫ"; // INGREDIENZEN
    }

    public static String RECIPE_OVERVIEW__OR_CHKBOX() {
        return LANG_ENG ? "OR" : "ЗАКАЗ"; // ODER
    }

    //===
    public static String RECIPE_OVERVIEW__INGREDIENT_1() {
        return LANG_ENG ? "INGREDIENT 1" : "ИНГРЕДИЕНТ 1"; // INGREDIENZ 1
    }

    public static String RECIPE_OVERVIEW__INGREDIENT_2() {
        return LANG_ENG ? "INGREDIENT 2" : "ИНГРЕДИЕНТ 2"; // INGREDIENZ 2
    }

    //===
    public static String RECIPE_OVERVIEW__RECIPE_SET() {
        return LANG_ENG ? "RECIPES SET" : "НАБОР РЕЦЕПТОВ"; // SATZ DER REZEPTE
    }

    public static String RECIPE_OVERVIEW__RECIPE_INFO() {
        return LANG_ENG ? "INFO" : "ИНФО"; // INFO
    }

    public static String RECIPE_OVERVIEW__SORT_BY_NOTE_VALUE() {
        return LANG_ENG ? "SORT BY NOTE VALUE" : "СОРТИРОВКА ПО ЗАМЕТКАМ"; // SORTIEREN NACH NOTENWERT
    }

    //<===Company = QEW====>
    public static String RECIPE_OVERVIEW__COLOR() { 
        return LANG_ENG ? "COLOR" : "ЦВЕТ"; // FARBE
    }

    public static String RECIPE_OVERVIEW__INDUSTRY() {
        return LANG_ENG ? "INDUSTRY" : "ИНДУСТРИЯ"; // INDUSTRIE
    }

    public static String RECIPE_TYPE() {
        return LANG_ENG ? "RECIPE TYPE" : "ТИП РЕЦЕПТА"; // REZEPT TYP
    }

    public static String CURING_SYSTEM() {
        return LANG_ENG ? "CURING SYSTEM" : "ВУЛК. СПОСОБ"; // VULK. SYSTEM
    }

    public static String CURING_PROCESS() {
        return LANG_ENG ? "CURING PROCESS" : "ВУЛК. ПРОЦЕСС"; // VULK. PROZESS
    }

    public static String FILTER_TYPE() {
        return LANG_ENG ? "FILTER TYPE" : "ТИП ФИЛЬТРА"; // FILTER TYP
    }

    public static String CERTIFICATE() {
        return LANG_ENG ? "CERTIFICATE" : "СЕРТИФИКАТ"; // ZERTIFIKAT
    }

    public static String SHELF_LIFE_MIN() {
        return LANG_ENG ? "SHELF LIFE MIN" : "СРОК ГОДНОСТИ МИН."; // HALTBARKEIT MIN
    }

    public static String SHELF_LIFE_MAX() {
        return LANG_ENG ? "SHELF LIFE MAX" : "СРОК ГОДНОСТИ МАКС."; // HALTBARKEIT MAX
    }

    public static String HARDNESS_SHA_MIN() {
        return LANG_ENG ? "HARDNESS SHA MIN" : "ТВЕРДОСТЬ SHA МИН."; // HÄRTE SHA MIN
    }

    public static String HARDNESS_SHA_MAX() {
        return LANG_ENG ? "HARDNESS SHA MAX" : "ТВЕРДОСТЬ SHA МАКС."; // HÄRTE SHA MAX
    }

    public static String CUSTOMER() {
        return LANG_ENG ? "CUSTOMER" : "ЗАКАЗЧИК"; // KUNDE
    }
    //</===Company = QEW====>

    //<===Company = CP====>
    public static String KOST_VON() {
        return LANG_ENG ? "COST FROM" : "ЦЕНА ОТ"; // KOSTEN VON
    }

    public static String KOST_BIS() {
        return LANG_ENG ? "COST TO" : "ЦЕНА ДО"; // KOSTEN BIS
    }

    public static String VK_PREIS_VON() {
        return LANG_ENG ? "VK-PRICE FROM" : "VK-ЦЕНА ОТ"; //VK-PREIS VON
    } 

    public static String VK_PREIS_BIS() {
        return LANG_ENG ? "VK-PRICE TO" : "VK-ЦЕНА ДО"; // VK-PREIS BIS
    } 

    public static String DICHTE_VON() {
        return LANG_ENG ? "DENSITY FROM" : "ПЛОТНОСТЬ ОТ"; // DICHTE VON
    }

    public static String DICHTE_BIS() {
        return LANG_ENG ? "DENSITY TO" : "ПЛОТНОСТЬ ДО"; // DICHTE BIS
    }

    public static String CHARGE_KG_VON() {
        return LANG_ENG ? "BATCH FROM" : "ВЕС СМЕСИ ОТ"; // CHARGE VON
    }

    public static String CHARGE_KG_BIS() {
        return LANG_ENG ? "BATCH TO" : "ВЕС СМЕСИ ДО"; // CHARGE BIS
    }
    
    public static String HALTBARKEIT_VON() {
        return LANG_ENG ? "SHELFLIFE FROM" : "СРОК ГОДНОСТИ ОТ"; // HALTBARKEIT VON
    } 
    
    public static String HALTBARKEIT_BIS() {
        return LANG_ENG ? "SHELFLIFE TO" : "СРОК ГОДНОСТИ ДО"; // HALTBARKEIT BIS
    }
    
    public static String HÄRTE_VON() {
        return LANG_ENG ? "HARDNESS FROM" : "ТВЕРДОСТЬ ОТ"; // HÄRTE VON
    }
    
    public static String HÄRTE_BIS() {
        return LANG_ENG ? "HARDNESS TO" : "ТВЕРДОСТЬ ДО"; // HÄRTE BIS
    }

    //</===Company = CP====>
}
