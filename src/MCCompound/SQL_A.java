/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCCompound;

/**
 *
 * @author KOCMOC
 */
public class SQL_A {

    /**
     *
     * @param param1 - recipeName
     * @param param2 - release
     * @return
     */
    public static String recipeBasicRZPT(String param1, String param2) {
        return "SELECT "
                + "material,"
                + "PHR,"
                + "Weight,"
                + "ContainerNB,"
                + "Phase,"
                + "PercRubber,"
                + "density,"
                + "weighingID,"
                + "BalanceID,"
                + "MatIndex,"
                + "PriceKG,"
                + "PriceData,"
                + "Descr,"
                + "GRP,"
                + "SiloId"
                + " FROM [Recipe_Basic_RZPT]" + " ('"
                + param1 + "','"
                + param2 + "') "
                + "order by Phase, ContainerNB";
    }

    /**
     *
     * @param param1 - recipeName
     * @param param2 - release
     * @return
     */
    public static String fnSequenceGet(String PROC, String param1, String param2) {
        return "SELECT * FROM [" + PROC + "]" + " ('"
                + param1 + "','"
                + param2 + "') "
                + "ORDER by _step ";
    }

    public static String delete_create_all_recipe(String PROC) {
        return PROC;
    }

    public static String generate_CSVColumn(String PROC, String recipeName, String release, int column, String order, int ammount, String recipeName2, String release2) {
        return PROC + "'" + recipeName + "','"
                + release + "'," + column + ",'" + order + "',"
                + ammount + "," + quotes(recipeName2) + "," + quotes(release2);
    }

    private static String quotes(String str) {
        if (str.equals("null")) {
            return str;
        } else {
            return "'" + str + "'";
        }
    }

    public static String generate_Empty_CSVColumn(String PROC,int column) {
        return PROC + column;
    }
}
