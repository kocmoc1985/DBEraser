#
#
#MainTable 
select * from MainTable where Datum >='2014-05-20' and Datum <= '2014-05-23' order by Datum asc
#
#
#CurvesTable
select CurvesTable.BatchId, Tik, torque, speed, gapLeft, gapRight, temperature from CurvesTable
inner join MainTable on MainTable.BatchId = CurvesTable.BatchId
where Datum >='2014-05-21 15:15:00' and Datum <= '2014-05-21 15:50:00' 
order by CurvesTable.BatchId,Tik asc
#
#
#Calculations
select Calculations.BatchId, totaltime, torqueav, gapLav, gapRav, speedAv, energySum, tempAv, param_1, param_2, param_3, param_4, param_5 from Calculations
inner join MainTable on MainTable.BatchId = Calculations.BatchId
where Datum >='2014-05-21 15:15:00' and Datum <= '2014-05-21 15:50:00' 
order by Calculations.BatchId