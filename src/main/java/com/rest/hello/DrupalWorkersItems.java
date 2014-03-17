package com.rest.hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: Desmond
 * Date: 24.02.14
 * Time: 17:59
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrupalWorkersItems {

    @JsonProperty(value = "target_id")
    private String targetId;

    public String getTargetId() {
        return targetId;
    }

    public DrupalWorkersItems(){

    }

    public DrupalWorkersItems(String targetId) {
        this.targetId = targetId;
    }

}
