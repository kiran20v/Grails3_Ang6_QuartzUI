package com.micro 

class QRequest {

	String statusCode
//	Integer minDaysRevalidate
//	Integer maxRowsPerQuery
//	Integer delayBtwQueries
//	Integer waitTime
//	Integer maxJobRunTime
//	String _title
//	String jobName
//	String comment
//	String cronTrigger
//	String title
//	String isRunNow
//	String jobId
//	Integer noOfHoursToWait
//	Integer monthNumber
//	Integer delayBtwRequests
//	String source
//	Integer expDays
//	String solrUrl
//	String jobPurpose
//	String jobType
//	Integer noOfDays
	
	
	static constraints = {
		statusCode(nullable: true)
	}

//	static constraints = {
//		_title blank: false, nullable: false
//		jobName blank: false, nullable: false
//		title blank: false, nullable: false
//		comment blank: true, nullable: true
//		minDaysRevalidate min: 0, max:999,nullable: true
//		maxRowsPerQuery min:1,max:1000,nullable: true
//		delayBtwQueries min:1,max:600,nullable: true
//		waitTime min:1,max:1800,nullable: true
//		maxJobRunTime min:60,max:3600,nullable: true
//		cronTrigger (validator: {val, obj ->
//			if (obj.isRunNow == 'schedule' && !val)
//				return ['error']
//		},nullable: true)
//		statusCode (validator: {val, obj ->
//			if (obj.title == ('phone' || 'email' || 'sfUpsert' || 'updateQueue'))
//				return ['error']
//		},nullable: true)
//		isRunNow nullable: true
//		jobId blank: true, nullable: true
//		noOfHoursToWait min: 0, max:999,nullable: true
//		monthNumber blank: true, nullable: true
//		delayBtwRequests min: 0, max:600,nullable: true
//		source (validator: {val, obj ->
//			if (obj.title == ('bridgerOFACRequest') && !val)
//				return ['error']
//		},nullable: true)
//		expDays (validator: {val, obj ->
//			if (obj.title == ('resetBridgerPassword') && !val)
//				return ['error']
//		},nullable: true)
//		solrUrl (validator: {val, obj ->
//			if (obj.title == ('solrRefresh') && !val)
//				return ['error']
//		},nullable: true)
//		jobPurpose blank: true, nullable: true
//		jobType blank: true, nullable: true
//		noOfDays (validator: {val, obj ->
//			if (obj.jobType == ('Delta') && !val)
//				return ['error']
//		},nullable: true)
//	}

}