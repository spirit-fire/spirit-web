package com.spirit.api.resources;

import com.spirit.commons.common.ApiLogger;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/consoles/")
@Component
public class Consoles {

    @Path("/demo")
    @GET
    @Produces( { MediaType.APPLICATION_JSON })
    public String demo(){
        String response = "{\"msg\":\"OK\"}";
        ApiLogger.info("/consoles/demo response: " + response);
        return response;
    }
}
