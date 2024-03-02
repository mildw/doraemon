package com.example.core.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ua_parser.Client;

@Getter
@AllArgsConstructor
public class UserAgent {

    private String ua;
    private String os;
    private String device;

    public static UserAgent parseUserAgent(Client c) {
        return new UserAgent(
                c.userAgent.family + ":" + c.userAgent.major + "." + c.userAgent.minor,
                c.os.family + ":" + c.os.major + "." + c.os.minor,
                c.device.family
        );
    }
}
