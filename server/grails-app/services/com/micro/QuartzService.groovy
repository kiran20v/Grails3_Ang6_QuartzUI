package com.micro

import grails.converters.JSON
import grails.gorm.transactions.Transactional
// import org.quartz.TriggerDescriptor
// import org.quartz.JobDescriptor
import grails.plugins.quartz.JobDescriptor
import grails.plugins.quartz.TriggerDescriptor

import org.quartz.JobDataMap
import org.quartz.JobExecutionContext
import org.springframework.transaction.annotation.Transactional

class QuartzService {

	static transactional = false
	def jobManagerService
//	def sessionFactory
	
	private static final String PAUSED = "PAUSED"
	private static final String ERROR = "ERROR"
	private static final String RUNNING = "RUNNING"
	private static final String WAITING = "WAITING"
	
	private static final String QUERY = "select QUARTZ_HISTORY_SEQ.nextval from dual"
	
	def getNextJobId() {
//		final session = sessionFactory.currentSession
//		final sqlQuery = session.createSQLQuery(QUERY)
//		def result = sqlQuery.list()
//		log.info "CDHInfo: QuartzService.getNextJobId - jobId : "+result
//		return result[0]?.intValue()
		return 1;
	}
	
	def getAllScheduledJobList() {
		def jobList = [];
		List<TriggerDescriptor> triggers = getAllTriggers()
		Set runningJobs = getLiveJobKeys()

		for(TriggerDescriptor trigDesc : triggers) {
			def job = [:]

			job['status'] = runningJobs.contains(trigDesc.getName()) ? RUNNING : WAITING
			if(trigDesc.getState().toString() == ERROR || trigDesc.getState().toString() == PAUSED) {
				job['status'] = trigDesc.getState().toString()
			}
			
			
			job['nextFireTime'] = trigDesc.getTrigger().getNextFireTime()
			job['endTime'] = trigDesc.getTrigger().getEndTime()
			job['startTime'] = trigDesc.getTrigger().getStartTime()

			JobDataMap map =  trigDesc.getTrigger().getJobDataMap()

			job['groupName']  = trigDesc.getGroup()
			job['jobTriggerName'] = trigDesc.getName()

			job['isPaused'] = (PAUSED.equals(trigDesc.getState().toString()))
			job['jobName'] = map.getString('jobName')
			job['cronTrigger'] = map.getString('cronTrigger')
			job['_title'] = map.getString('_title')
			jobList.push(job)
		}
		
		jobList.sort({it.status})
	 
		log.info "CDHInfo: QuartzService.getAllScheduledJobList - Job List: "+jobList
		return jobList
	}

	/**
	 * Unschedule quartz job
	 * @param quartzParams
	 * @return
	 */
	@Transactional
	def unscheduleQuartzJob(quartzParams) {
		log.info"QuartzInfo: QuartzService.unscheduleQuartzJob - Input Request : "+quartzParams.jobTriggerName
		try {

			List<TriggerDescriptor> triggers = getAllTriggers()
			for(TriggerDescriptor trigDesc : triggers) {
				log.debug "CDHDebug: QuartzService.unscheduleQuartzJob - Triggger Name:"+trigDesc.getName()
				if(quartzParams.jobTriggerName == trigDesc.getName()) {
					log.info "CDHInfo: QuartzService.unscheduleQuartzJob - Trigger matched Name:"+trigDesc.getName()
					jobManagerService.quartzScheduler.unscheduleJob(trigDesc.getTrigger().getKey())
					break
				}
			}
		} catch (Exception e) {
			log.error "CDHError: QuartzService.unscheduleQuartzJob -  ", e
		}
		return quartzParams
	}
	
	@Transactional
	def pauseAllJobs() {
		log.info"CDHInfo: QuartzService.pauseAllJobs"
		try {
			jobManagerService.quartzScheduler.pauseAll()
		} catch (Exception e) {
			log.error "CDHError: QuartzService.pauseAllJobs -  ", e
		}
	}
	
	
	@Transactional
	def pauseJob(quartzParams) {
		log.info"CDHInfo: QuartzService.pauseJob - Input Request : "+quartzParams.jobTriggerName
		try {

			List<TriggerDescriptor> triggers = getAllTriggers()
			for(TriggerDescriptor trigDesc : triggers) {
				log.debug "CDHDebug: QuartzService.unscheduleQuartzJob - Triggger Name:"+trigDesc.getName()
				if(quartzParams.jobTriggerName == trigDesc.getName()) {
					log.info "CDHInfo: QuartzService.unscheduleQuartzJob - Trigger matched Name:"+trigDesc.getName()
					jobManagerService.quartzScheduler.pauseJob(trigDesc.getTrigger().getJobKey())
					break
				}
			}
		} catch (Exception e) {
			log.error "CDHError: QuartzService.unscheduleQuartzJob -  ", e
		}
		return quartzParams
	}
	
	
	@Transactional
	def resumeJob(quartzParams) {
		log.info"CDHInfo: QuartzService.resumeJob - Input Request : "+quartzParams.jobTriggerName
		try {

			List<TriggerDescriptor> triggers = getAllTriggers()
			for(TriggerDescriptor trigDesc : triggers) {
				log.debug "CDHDebug: QuartzService.unscheduleQuartzJob - Triggger Name:"+trigDesc.getName()
				if(quartzParams.jobTriggerName == trigDesc.getName()) {
					log.info "CDHInfo: QuartzService.unscheduleQuartzJob - Trigger matched Name:"+trigDesc.getName()
					jobManagerService.quartzScheduler.resumeJob(trigDesc.getTrigger().getJobKey())
					break
				}
			}
		} catch (Exception e) {
			log.error "CDHError: QuartzService.unscheduleQuartzJob -  ", e
		}
		return quartzParams
	}
	
	@Transactional
	def resumeAllJobs() {
		log.info"CDHInfo: QuartzService.resumeAll"
		try {
			jobManagerService.quartzScheduler.resumeAll()
		} catch (Exception e) {
			log.error "CDHError: QuartzService.resumeAll -  ", e
		}
	}
	
	@Transactional
	def isSchedulerPaused() {
		log.info"CDHInfo: QuartzService.getSchedulerStatus"
		try {
			return jobManagerService.quartzScheduler.getPausedTriggerGroups()?.isEmpty()
		} catch (Exception e) {
			log.error "CDHError: QuartzService.getSchedulerStatus -  ", e
		}
		return false
	}
	
	def getJobHistoryFromDB(quartzParams) {
		def filter = ""
		if(!quartzParams?.isEmpty()) {
			filter = " where "
		}
		if (quartzParams.jobTitle) {
			filter = filter + " qh.jobTitle = '" + quartzParams.jobTitle +"' and"
		}
		if (quartzParams.startDate){
			filter = filter + "  qh.startDate >= to_date('" + quartzParams.startDate +"', 'mm/dd/yyyy')"
		}
		if (quartzParams.endDate){
			filter = filter + " and qh.endDate <= to_date('" + quartzParams.endDate +"', 'mm/dd/yyyy')+1"
		}
		if (quartzParams.status && quartzParams.status != 'all'){
			filter = filter + " and qh.status = '" + quartzParams.status.toUpperCase() +"'"
		}
		def history =  QuartzHistory.findAll(" from QuartzHistory qh "+filter+" order by qh.dateCreated desc", [max: 1000])
		return history
	}
	
	@Transactional
	public QuartzHistory persistJobDetails(QuartzHistory history) {
		try {
			history.id  = history.id ? history.id : getNextJobId()
			if(!history.save()) {
				history.errors.each {
					log.error "CDHError: QuartzService.persistJobDetails - Error while saving job history record: "+it
				}
			}
		} catch (Exception e) {
			log.error "CDHError: QuartzService.persistJobDetails - Exception while saving job history record : ", e
		}
		return history
	}
	
	private getAllTriggers() {
		def jobTriggerList = new ArrayList<TriggerDescriptor>();
		try {
			Map<String, List<JobDescriptor>> jobDescMap = jobManagerService.getAllJobs()
			if(!jobDescMap.isEmpty()) {
				for(Map.Entry<String, List<JobDescriptor>> desc : jobDescMap.entrySet()) {
					for(JobDescriptor jobDesc : desc.getValue()) {
						jobTriggerList.addAll(jobDesc.getTriggerDescriptors())
					}
				}
			}
		} catch (Exception e) {
			log.error "CDHError: QuartzService.getAllTriggers - ", e
		}
		return jobTriggerList
	}

	private getLiveJobKeys() {
		def runningJobs = new HashSet();
		try {

			def currentlyExecutingJobs = jobManagerService.getRunningJobs()
			for(JobExecutionContext context : currentlyExecutingJobs) {
				runningJobs.add(context.getTrigger().getKey().getName())
			}
		} catch (Exception e) {
			log.error "CDHError: QuartzService.getLiveJobKeys - ", e
		}
		return runningJobs
	}
}