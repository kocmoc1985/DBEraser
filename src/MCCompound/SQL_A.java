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
    public static String fnSequenceGet(String param1, String param2) {
        return "SELECT * FROM [fn_Sequence_Get]" + " ('"
                + param1 + "','"
                + param2 + "') "
                + "ORDER by _step ";
    }

    public static String delete_create_all_recipe() {
        return "DELETE_CREATE_ALL_RECIPENew";
    }

    public static String generate_CSVColumn(String recipeName, String release, int column, String order, int ammount, String recipeName2, String release2) {
        return "generate_CSVColumn_1 '" + recipeName + "','"
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

    public static String generate_Empty_CSVColumn(int column) {
        return "generate_Empty_CSVColumn " + column;
    }
}
