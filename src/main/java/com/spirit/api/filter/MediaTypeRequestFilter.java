package com.spirit.api.filter;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * delete .json suffix, such as:
 * /consoles/demo.json => /consoles/demo
 */
public class MediaTypeRequestFilter implements ContainerRequestFilter {

    private static Map<String, MediaType> mediaTypes;
    static {
        mediaTypes = new HashMap<String, MediaType>();
        mediaTypes.put("json", MediaType.APPLICATION_JSON_TYPE);
        mediaTypes.put("xml", MediaType.APPLICATION_XML_TYPE);
        mediaTypes.put("html", MediaType.TEXT_HTML_TYPE);
    }

    /**
     * Filter the request.
     * <p>
     * An implementation may modify the state of the request or
     * create a new instance.
     *
     * @param request the request.
     * @return the request.
     */
    @Override
    public ContainerRequest filter(ContainerRequest request) {
        String path = request.getPath();
        if (path.length() < 2) {
            return request;
        }
        int index = path.lastIndexOf('.');
        if (index <= 0 || index == path.length() - 1) {
            return request;
        }
        String suffix = path.substring(index + 1).toLowerCase();
        MediaType type = mediaTypes.get(suffix);

        String newPath = path.substring(0, index);
        URI baseUri = request.getBaseUri();
        URI requestUri = request.getRequestUri();
        URI newRequestUri = request.getRequestUriBuilder().replacePath(baseUri.getPath() + newPath).build();
        request.setUris(baseUri, newRequestUri);

        return request;
    }
}
