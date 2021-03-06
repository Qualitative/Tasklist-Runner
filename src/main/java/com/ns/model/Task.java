package com.ns.model;

import java.time.Duration;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.google.common.collect.Lists;
import com.ns.util.TaskParserUtils;

@XmlRootElement(name = "task")
@XmlType(propOrder = { "name", "memoryUsage" })
@XmlAccessorType(XmlAccessType.NONE)
public class Task {

    public static final int DEFAULT = -1;

    private String name;
    private int pid = DEFAULT;
    private String sessionName;
    private int sessionNumber = DEFAULT;
    private long memoryUsage = DEFAULT;
    private Status status;
    private String userName;
    private Duration cpuTime;
    private String windowTitle;
    private List<String> modules = Lists.newArrayList();
    private List<String> services = Lists.newArrayList();

    @XmlElement(name = "name", required = true, nillable = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public int getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(int sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    @XmlElement(name = "memory", required = true)
    public long getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(long memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Duration getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(Duration cpuTime) {
        this.cpuTime = cpuTime;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }

    public List<String> getModules() {
        return modules;
    }

    public void setModules(List<String> modules) {
        this.modules = modules;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + pid;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Task other = (Task) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (pid != other.pid)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Task [name=").append(name).append(", pid=").append(pid)
                .append(", sessionName=").append(sessionName).append(", sessionNumber=").append(sessionNumber)
                .append(", memoryUsage=").append(memoryUsage).append(", status=").append(status).append(", userName=")
                .append(userName).append(", cpuTime=")
                .append(cpuTime == null ? "null" : TaskParserUtils.getDurationString(cpuTime))
                .append(", windowTitle=").append(windowTitle).append(", modules=").append(modules)
                .append(", services=").append(services).append("]").toString();
    }

}
