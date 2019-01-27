package com.micro

import grails.converters.JSON
import grails.gorm.transactions.Transactional
// import grails.plugin.springsecurity.annotation.Secured
// import grails.plugin.springsecurity.SpringSecurityUtils

/**
 * @author hanu
 *
 */
// @Secured(['ROLE_USER'])
class QuartzJobsController {
	
	
		QuartzService quartzService
		static allowedMethods = [save: "POST"
								 ,runQuartzJob:"PUT"
								 ,index:"GET"
								 ,getJobsStatus:"GET"
								 ,delete:"DELETE"
								 ,getJobHistory:"POST"
								 ,pauseAllJobs:"GET"
								 ,resumeAllJobs:"GET"
								 ,pauseJob:"POST"
								 ,resumeJob:"POST"
								 ,getSchedulerStatus:"GET"]
		def messageSource
		
		def quartzJobMapFactory
		
		def getJobsStatus(){
			def jobs = quartzService.getAllScheduledJobList()
			def status = 200
			def jobList = []
			jobs.each{ job ->
				if(job.status == QuartzConstants.STATUS_ERROR){
					status = 417
				}
				jobList.add(job.jobName +" - "+job.status)
			}
			response.status = status
			render jobList as JSON
		}
		
		def index() {
			render quartzService.getAllScheduledJobList() as JSON
		}
	
	
		def delete() {
			def quartzParams = ["jobTriggerName":params.id]
			render quartzService.unscheduleQuartzJob(quartzParams) as JSON
		}
	
		@Transactional
		def save() {
			def quartzParams = request.JSON
			log.error "Request params from Quartz create: {}"+ quartzParams
			def qRequest = new QRequest()
			bindData(qRequest,quartzParams)
			if(qRequest.validate()) {
				log.info "DMQ-Info: Scheulde Quartz Job - params : {}", quartzParams
				try {
					def jobClass = quartzJobMapFactory.get(quartzParams.jobName)
					if(quartzParams.isRunNow == "runNow") {
						jobClass?.triggerNow(quartzParams)
					} else {
						jobClass?.schedule(quartzParams.cronTrigger, quartzParams)
					}
				} catch (Exception e) {
					log.error "DMQ-Error: Error occured at Scheulde Quartz job : ", e
				}
				render quartzParams
			}
			else {
				def responseMap = [:]
				def errors = []
				qRequest?.errors?.allErrors?.each {
					errors.add(messageSource.getMessage(it, null))
				}
				response.status = 400
				responseMap['errors'] = errors
				log.debug "DMQ-Debug: Quartz UI validation-errors - "+errors
				render responseMap as JSON
			}
		}
		
		@Transactional
		def runQuartzJob() {
			def quartzParams = request.JSON
			def qRequest = new QRequest()
			bindData(qRequest,quartzParams)
			if(qRequest.validate()) {
				log.info "DMQ-Info: Run Quartz Job params : "+quartzParams
				try {
					quartzJobMapFactory.get(quartzParams.title)?.triggerNow(quartzParams)
				} catch (Exception e) {
					log.error "DMQ-Error: Error occured at Run Quartz Job : ", e
				}
				render "Success"
			}
			else {
				def responseMap = [:]
				def errors = []
				qRequest?.errors?.allErrors?.each {
					errors.add(messageSource.getMessage(it, null))
				}
				response.status = 400
				responseMap['errors'] = errors
				log.debug "DMQ-Debug: Quartz UI validation-errors - "+errors
				render responseMap as JSON
			}
		}
		
		@Transactional
		def pauseJob() {
			def quartzParams = request.JSON
			quartzService.pauseJob(quartzParams)
			render "success"
		}
		
		@Transactional
		def resumeJob() {
			def quartzParams = request.JSON
			quartzService.resumeJob(quartzParams)
			render "success"
		}
		
		@Transactional
		def pauseAllJobs() {
			quartzService.pauseAllJobs()
			render "success"
		}
		
		@Transactional
		def resumeAllJobs() {
			quartzService.resumeAllJobs()
			render "success"
		}
		
		def getJobHistory() {
			def quartzHistoryMapList = []
			def quartzParams = request.JSON
			def quartzHistoryList = quartzService.getJobHistoryFromDB(quartzParams)
			for (def quartzHistory : quartzHistoryList){
				def qHistory =  [:]
				qHistory.jobTitle = quartzHistory.jobTitle
				qHistory.jobName = quartzHistory.jobName
				qHistory.cronExpr = quartzHistory.cronExpr
				qHistory.startDate = quartzHistory.startDate
				qHistory.endDate = quartzHistory.endDate
				qHistory.execTime = quartzHistory.execTime
				qHistory.status = quartzHistory.status
				qHistory.itemCount = quartzHistory.itemCount
				qHistory.errorCount = quartzHistory.errorCount
				qHistory.userId = quartzHistory.userId
				
				quartzHistoryMapList.push(qHistory)
			}
			render quartzHistoryMapList as JSON
		}
		
		def getSchedulerStatus(){
			render quartzService.isSchedulerPaused()
		}
	
	

}