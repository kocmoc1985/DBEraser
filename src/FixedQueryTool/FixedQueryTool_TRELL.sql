/*This query is 100 % Tested*/

/*Creation of **resultsN** View*/

SELECT     TOP (100) PERCENT dbo.Limits.Quality, dbo.Result.OrderCode AS [Order], dbo.Result.BatchNo, dbo.Result.TestNo, dbo.[Procedure].TestCode, 
                      dbo.[Procedure].Description, dbo.ResultTest.Result AS TestResult, dbo.LimitTest.LSL, dbo.LimitTest.USL, dbo.TAG.Name, dbo.Result.TestDate
FROM         dbo.Limits INNER JOIN
                      dbo.LimitTest ON dbo.Limits.ID = dbo.LimitTest.LimitID INNER JOIN
                      dbo.[Procedure] ON dbo.Limits.ProcID = dbo.[Procedure].ID INNER JOIN
                      dbo.ProcTest ON dbo.LimitTest.ProctestID = dbo.ProcTest.ID AND dbo.[Procedure].ID = dbo.ProcTest.ProcID INNER JOIN
                      dbo.Result ON dbo.Limits.ID = dbo.Result.LimitID INNER JOIN
                      dbo.TAG ON dbo.ProcTest.TagID = dbo.TAG.ID INNER JOIN
                      dbo.ResultTest ON dbo.LimitTest.ID = dbo.ResultTest.LimitTestID AND dbo.Result.ID = dbo.ResultTest.ResultID
WHERE     (dbo.Result.Status <> 'DELETED')
