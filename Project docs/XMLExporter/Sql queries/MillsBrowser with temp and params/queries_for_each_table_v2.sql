#
#
#MainTable 
select * from MainTable where BatchId >= 122 and BatchId <= 142 order by BatchId asc
#
#
#CurvesTable
select CurvesTable.BatchId, Tik, torque, speed, gapLeft, gapRight, temperature from CurvesTable
inner join MainTable on MainTable.BatchId = CurvesTable.BatchId
where MainTable.BatchId >= 122 and MainTable.BatchId <= 142 
order by CurvesTable.BatchId,Tik asc
#
#
#Calculations
select Calculations.BatchId, totaltime, torqueav, gapLav, gapRav, speedAv, energySum, tempAv, param_1, param_2, param_3, param_4, param_5 from Calculations
inner join MainTable on MainTable.BatchId = Calculations.BatchId
where MainTable.BatchId >= 122 and MainTable.BatchId <= 142
order by Calculations.BatchId asc