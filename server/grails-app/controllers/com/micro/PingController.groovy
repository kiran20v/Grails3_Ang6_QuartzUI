package com.micro.controller

// import com.boot.chain.BasicChain
import grails.converters.JSON

import java.lang.management.ManagementFactory
import java.lang.management.RuntimeMXBean

/**
 * @author hanu
 *
 */
class PingController {

	static allowedMethods = [index:"GET"]

    // BasicChain complexChildChain

	public index() {
		RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
		Map systemAttr = new LinkedHashMap<String, String>();
		systemAttr.put("Name","Pentomath")
		systemAttr.put("Version","2.0")
		systemAttr.put("Current Time", new Date().toString())
		systemAttr.put("Start Time", new Date(runtimeBean.getStartTime()))
		String uptimeValue =  getUpTime(runtimeBean)
		systemAttr.put("Up a Time", uptimeValue)
		// complexChildChain.executeLogic(["domainObject":'Inside chain'])

        log.debug "Ping controller -debug : "+systemAttr
		render systemAttr as JSON
	}

	private String getUpTime(RuntimeMXBean runtimeBean) {
		String uptimeValue
		Long uptime = runtimeBean.getUptime()
		uptime = (uptime / 1000)
		if (uptime < 120) {
			uptimeValue = uptime.toString() + " secs"
		} else {
			uptimeValue = (uptime / 60) + " mins"
		}
		uptimeValue
	}
}
