package com.micro

class QuartzJob {

    String jobName
    String status
    Date nextFireTime
    Date endTime
    Date startTime
    String groupName
    String jobTriggerName
    Boolean isPaused
    String cronTrigger
    String title



    static constraints = {
        jobName nullable:false, blank:false
        status nullable:false, blank:false
    }
}
