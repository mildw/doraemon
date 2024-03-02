package com.example.core.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@UtilityClass
public class NetworkUtils {

    public UserAgent UserAgent() {
        String uaString = NetworkUtils.getHttpServletRequest().getHeader("User-Agent");

        Parser uaParser = new Parser();
        Client c = uaParser.parse(uaString);

        return UserAgent.parseUserAgent(c);
    }

    public HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        assert servletRequestAttributes != null;
        return servletRequestAttributes.getRequest();
    }

    public HttpServletResponse getHttpServletResponse() {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        assert servletRequestAttributes != null;
        return servletRequestAttributes.getResponse();
    }

}