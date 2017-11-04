package com.spirit.api.servlet;

import javax.servlet.http.HttpServletRequest;

public interface SpiritServletFilter {

     void before(HttpServletRequest request);

     void after(HttpServletRequest request);
}
