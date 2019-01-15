package com.micro

class  extends QuartzHistory {

	String jobTitle
	String jobName
	Date startDate
	Date endDate
	String cronExpr
	String status
	Integer execTime
	Integer itemCount
	Integer errorCount
	String userId
	Date dateCreated

    static constraints = {
    
    }

     static namedQueries = {
        query { search ->
            if (!Boolean.valueOf(search.includeDeleted)) {
                eq('deleted', false)
            }

            if (search.containsKey('id')) {
                eq('id', search.id)
            }
        }
    }
}