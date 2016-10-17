/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCCompound;

/**
 *
 * @author mcab
 */
public class SQL_B {

    public static String test_1_querry() {
        return "select * from MCRecipeUsers";
    }

    public static String select_query_1() {
        return "SELECT top 5 VENDOR.Vendor_ID,dbo.VENDOR.VendorNo, dbo.VENDOR.VendorName, dbo.VENDOR.Adress, dbo.VENDOR.ZipCode, dbo.VENDOR.City, dbo.VENDOR.Country,\n"
                + " dbo.VENDOR.Phone, dbo.VENDOR.Fax, dbo.VENDOR.Website, dbo.VENDOR.FreeInfo, dbo.VENDOR.Status, dbo.Vendor_Contact.ContactName,\n"
                + " dbo.Vendor_Contact.position, dbo.Vendor_Contact.Phone AS Person_Phone, dbo.Vendor_Contact.Email AS Person_email,\n"
                + " dbo.Vendor_Contact.Status AS Person_Status\n"
                + " FROM dbo.VENDOR LEFT OUTER JOIN\n"
                + " dbo.Vendor_Contact ON dbo.VENDOR.VENDOR_ID = dbo.Vendor_Contact.VENDOR_ID";
    }

    public static String updatePositions(String seqPosition, String actSeqPos) {
        return "update Production_Plan_Csv_Temp set " + ProdPlan.SEQUENCE
                + " = " + ProdPlan.SEQUENCE + "+" + 1
                + " where " + ProdPlan.SEQUENCE + " >= " + seqPosition
                + " and " + ProdPlan.SEQUENCE + " < " + actSeqPos + "";
    }

    public static String updatePositionsB(String seqPosition, String id) {
        return "update Production_Plan_Csv_Temp set " + ProdPlan.SEQUENCE
                + " = " + seqPosition
                + " where " + ProdPlan.ID + "=" + id;
    }

    public static String updatePositionsErase(String seqPosition) {
        return "update Production_Plan_Csv_Temp set " + ProdPlan.SEQUENCE
                + " = " + ProdPlan.SEQUENCE + "-" + 1
                + " where " + ProdPlan.SEQUENCE + " > " + seqPosition;
    }

    public static void main(String[] args) {
//        System.out.println("" + updatePositions("3"));
    }

    private static String prodPlanGetHigherThen(int seqPosition) {
        return "select * from Production_Plan_Csv_Temp "
                + " where " + ProdPlan.SEQUENCE + " > " + seqPosition;
    }

    public static String prodPlanGetHighestSeqNr() {
        return "select top 1 * from Production_Plan_Csv_Temp order by " + ProdPlan.SEQUENCE + " desc";
    }

    public static String prodPlanAddToTempTable(String prodOrder) {
        return "insert into Production_Plan_Csv_Temp"
                + " select * from Production_Plan_Csv"
                + " where " + ProdPlan.PROD_ORDER + " = " + prodOrder;
    }

    public static String deleteEntryTempTable(String id) {
        return "delete from Production_Plan_Csv_Temp"
                + " where " + ProdPlan.ID + "=" + id;
    }
    
    public static String deleteAllFromTempTable() {
        return "delete from Production_Plan_Csv_Temp";
    }

    public static String updateSequenceProdPlanTemp(String id, int i) {
        return "update Production_Plan_Csv_Temp set "
                + ProdPlan.SEQUENCE + " =" + i
                + " where " + ProdPlan.ID + "=" + id + "";
    }

    public static String getProductionPlan() {
        return "select * from Production_Plan_Csv";
    }

    public static String getProductionPlanTemp() {
        return "select * from Production_Plan_Csv_Temp order by " + ProdPlan.SEQUENCE + " asc";
    }

    public static String getOneOrderByPlanIdTemp(String prodOrder) {
        return "select * from Production_Plan_Csv_Temp"
                + " WHERE " + ProdPlan.ID + " = '"
                + prodOrder + "'";
    }

    public static String getOneOrderByPlanId(String prodOrder) {
        return "select * from Production_Plan_Csv"
                + " WHERE " + ProdPlan.PROD_ORDER + " = '"
                + prodOrder + "'";
    }

    public static String buildRecipeCsv() {
        return "select * from " + "[Recipe.csv] order by id asc";
    }
    
    public static String getRecipeCsv() {
        return "select * from " + "[REcipe_CSV_1] order by id asc";
    }

    public static String test_1_delete_querry(int id) {
        return "delete from Vendor where vendor_id = " + id;
    }
    
    private static String getProductionPlan_2() {
        return "select "
                + "row_number() over (order by PLANDATE desc, PLANID) as ID,"
                + "LEFT(CONVERT(VARCHAR(19),PLANDATE,126),10) as PLANDATE,"
                + "PLANID,"
                + "ORDERNO,"
                + "RCODE,"
                + "MTYPE,"
                + "STATUS,"
                + "BATCHQTY,"
                + "PRODQTY,"
                + "FIRSTBATCH,"
                + "PRIORITY,"
                + "CANCELQTY,"
                + "BOOKEDQTY,"
                + "LEFT(CONVERT(VARCHAR(19),PRODDATE,126),10) as PRODDATE,"
                + "ORIGIN,"
                + "MODIFIED,"
                + "REMARK,"
                + "LEFT(CONVERT(VARCHAR(19),LASTUPDATE,126),10) as LASTUPDATE,"
                + "UPDATEDBY"
                + " from"
                + " Production_Plan";
    }

    private static String getOneOrderByPlanId_2(String planid) {
        return "select "
                + "LEFT(CONVERT(VARCHAR(19),PLANDATE,126),10) as PLANDATE,"
                + "PLANID,"
                + "ORDERNO,"
                + "RCODE,"
                + "MTYPE,"
                + "STATUS,"
                + "BATCHQTY,"
                + "PRODQTY,"
                + "FIRSTBATCH,"
                + "PRIORITY,"
                + "CANCELQTY,"
                + "BOOKEDQTY,"
                + "LEFT(CONVERT(VARCHAR(19),PRODDATE,126),10) as PRODDATE,"
                + "ORIGIN,"
                + "MODIFIED,"
                + "REMARK,"
                + "LEFT(CONVERT(VARCHAR(19),LASTUPDATE,126),10) as LASTUPDATE,"
                + "UPDATEDBY"
                + " from"
                + " Production_Plan"
                + " WHERE "
                + " PLANID ='" + planid + "'";
    }
}
