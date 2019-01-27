package com.micro

public class MyJob {

    def execute(def context) {
        log.info "Started Running my job :"+context
        sleepInSeconds(10)
        log.info "Ended Running my job :"+context
    }


    def sleepInSeconds(def sec) {
        try {
            Thread.sleep(sec*1000);

        } catch(e) {
            log.error "Exception while thread sleep: ",e
            throw e;
        }
    }
}
