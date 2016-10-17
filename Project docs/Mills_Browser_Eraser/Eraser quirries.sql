//'@The "Standard" query, which removes all of the comments for given day'
//
//
select BatchId from MainTable where datum
like '%2012-10-14%' 
//
//
// '@This will return all entries with ids which ends with 2, which means "mill 2 (device_type:3)"'
//
select BatchId from MainTable2 where batchid like '%2'

