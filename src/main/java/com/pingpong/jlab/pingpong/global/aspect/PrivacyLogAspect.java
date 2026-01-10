//package com.pingpong.jlab.pingpong.global.aspect;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.lang.reflect.Field;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.Enumeration;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import java.util.stream.Collectors;
//import kr.fingate.gs.core.annotation.PiLogger;
//import kr.fingate.gs.core.aop.exception.BizException;
//import kr.fingate.gs.core.aop.response.ResponseCode;
//import kr.fingate.gs.core.security.dto.UserTokenDto;
//import kr.fingate.gs.core.service.CoreService;
//import kr.fingate.gs.core.util.CoreRestApi;
//import kr.fingate.gs.core.vo.log.AccessLogVO;
//import kr.fingate.gs.core.vo.log.PrivacyLogVO;
//import lombok.Generated;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.json.simple.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.ModelAndView;
//
//@Aspect
//@Component
//public class PrivacyLogAspect {
//  @Generated
//  private static final Logger log = LoggerFactory.getLogger(PrivacyLogAspect.class);
//  final CoreService coreService;
//  final CoreRestApi coreRestApi;
//  final HttpServletRequest request;
//  @Value("${api.url.gateway}")
//  private String GATEWAY_URL;
//  @Value("${api.uri.gateway.log}")
//  private String LOG_URI;
//
//  @AfterReturning(
//      pointcut = " execution(* kr.fingate.gs..controller.CommonController.moveRoute(..)) ||  execution(* kr.fingate.gs..controller.OutController.getAuthFnctnIdList(..))"
//  )
//  public void accessLog(JoinPoint joinPoint) {
//    try {
//      AccessLogVO accessLogVO = this.getAccessLog();
//      if (accessLogVO.getRequestUrl().contains("local")) {
//        return;
//      }
//
//      if (accessLogVO.getUserNo() == 0L) {
//        return;
//      }
//
//      if (joinPoint.getTarget().getClass().getName().endsWith("CommonController")) {
//        accessLogVO.setLogType("M");
//        String toUrl = (String)Arrays.stream(joinPoint.getArgs()).filter((arg) -> {
//          return arg instanceof JSONObject;
//        }).map((json) -> {
//          return (String)((JSONObject)json).get("toUrl");
//        }).findFirst().orElse("");
//        accessLogVO.setRequestUrl(toUrl);
//      } else {
//        accessLogVO.setLogType("S");
//        accessLogVO.setRequestUrl("/");
//      }
//
//      this.sendLog(accessLogVO);
//    } catch (Exception var4) {
//      log.error(var4.getMessage());
//    }
//
//  }
//
//  @AfterReturning(
//      pointcut = "@annotation(kr.fingate.gs.core.annotation.PiLogger)",
//      returning = "value"
//  )
//  public void privacyLog(JoinPoint joinPoint, Object value) {
//    try {
//      PrivacyLogVO privacyLogVO = this.getPrivacyLog(joinPoint);
//      if (privacyLogVO.getRequestUrl().contains("local")) {
//        return;
//      }
//
//      if (privacyLogVO.getUserNo() == 0L) {
//        return;
//      }
//
//      MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
//      PiLogger piLogger = (PiLogger)methodSignature.getMethod().getAnnotation(PiLogger.class);
//      String anoValue = piLogger.value();
//      boolean isFile = piLogger.isFile();
//      String fileName = piLogger.fileName();
//      if (anoValue == null) {
//        anoValue = "";
//      }
//
//      UserTokenDto userTokenDto = this.coreService.getUserInfo();
//      privacyLogVO.setClientNo(userTokenDto.getClientNo());
//      privacyLogVO.setLoginId(userTokenDto.getLoginId());
//      privacyLogVO.setUserNo(userTokenDto.getUserNo());
//      privacyLogVO.setSystemCode(this.getSystemCode());
//      privacyLogVO.setPrvcyCntnt(anoValue);
//      if (!(value instanceof ModelAndView) && !isFile) {
//        privacyLogVO.setLogType("P");
//        this.sendLog(privacyLogVO);
//      } else {
//        privacyLogVO.setLogType("F");
//        this.setPrivacyDownInfo(privacyLogVO, joinPoint);
//        if (isFile) {
//          privacyLogVO.setFileName(fileName);
//        } else {
//          this.setModelAndViewInfo((ModelAndView)value, privacyLogVO);
//        }
//
//        this.sendLog(privacyLogVO);
//      }
//    } catch (Exception var10) {
//      log.error(var10.getMessage());
//    }
//
//  }
//
//  private AccessLogVO getAccessLog() {
//    UserTokenDto userTokenDto = this.coreService.getTokenInfo();
//    AccessLogVO accessLogVO = new AccessLogVO();
//    BeanUtils.copyProperties(userTokenDto, accessLogVO);
//    accessLogVO.setClientIp(this.getClientIp());
//    accessLogVO.setSystemCode(this.getSystemCode());
//    accessLogVO.setRequestUrl(this.request.getRequestURI());
//    accessLogVO.setRequestDate((new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA)).format(new Date()));
//    return accessLogVO;
//  }
//
//  private PrivacyLogVO getPrivacyLog(JoinPoint joinPoint) {
//    PrivacyLogVO privacyLogVO = new PrivacyLogVO();
//    BeanUtils.copyProperties(this.getAccessLog(), privacyLogVO);
//    List<Object> argList = (List)Arrays.stream(joinPoint.getArgs()).filter((arg) -> {
//      return !(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse);
//    }).collect(Collectors.toList());
//    Gson gson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
//    privacyLogVO.setParamValue(gson.toJson(argList));
//    return privacyLogVO;
//  }
//
//  private void setPrivacyDownInfo(PrivacyLogVO privacyLogVO, JoinPoint joinPoint) throws Exception {
//    Object[] args = joinPoint.getArgs();
//    Object[] var4 = args;
//    int var5 = args.length;
//
//    for(int var6 = 0; var6 < var5; ++var6) {
//      Object arg = var4[var6];
//      if (arg != null) {
//        if (arg instanceof Map) {
//          Map<?, ?> map = (Map)arg;
//          if (map.containsKey("prvcyMaskYn")) {
//            privacyLogVO.setPrvcyMaskYn(String.valueOf(map.get("prvcyMaskYn")));
//          }
//
//          if (map.containsKey("dwnldReason")) {
//            privacyLogVO.setDwnldReason(String.valueOf(map.get("dwnldReason")));
//          }
//        } else {
//          try {
//            Field prvcyMaskYnField = this.findField(arg.getClass(), "prvcyMaskYn");
//            if (prvcyMaskYnField != null) {
//              prvcyMaskYnField.setAccessible(true);
//              Object value = prvcyMaskYnField.get(arg);
//              if (value != null) {
//                privacyLogVO.setPrvcyMaskYn(String.valueOf(value));
//              }
//            }
//
//            Field reasonField = this.findField(arg.getClass(), "dwnldReason");
//            if (reasonField != null) {
//              reasonField.setAccessible(true);
//              Object value = reasonField.get(arg);
//              if (value != null) {
//                privacyLogVO.setDwnldReason(String.valueOf(value));
//              }
//            }
//          } catch (IllegalAccessException var12) {
//            throw new BizException(ResponseCode.ACCESS_NOT_ALLOWED);
//          }
//        }
//      }
//    }
//
//  }
//
//  private Field findField(Class<?> clazz, String fieldName) {
//    Class<?> current = clazz;
//
//    while(current != null) {
//      try {
//        return current.getDeclaredField(fieldName);
//      } catch (NoSuchFieldException var5) {
//        current = current.getSuperclass();
//      }
//    }
//
//    return null;
//  }
//
//  private void setModelAndViewInfo(ModelAndView mav, PrivacyLogVO privacyLogVO) {
//    String viewName = mav.getViewName();
//    if (viewName.equals("xlsxView") || viewName.equals("downloadView")) {
//      int listSize = false;
//      String fileName = "";
//      Map<String, Object> model = mav.getModel();
//      Iterator var7 = model.keySet().iterator();
//
//      while(var7.hasNext()) {
//        String key = (String)var7.next();
//        Object excelValue = model.get(key);
//        if (excelValue instanceof String) {
//          fileName = excelValue.toString();
//          break;
//        }
//      }
//
//      privacyLogVO.setFileName(fileName);
//    }
//  }
//
//  private void sendLog(Object param) {
//    if (!this.LOG_URI.isEmpty()) {
//      try {
//        this.coreRestApi.post(String.format("%s%s", this.GATEWAY_URL, this.LOG_URI), param);
//      } catch (Exception var3) {
//        log.error(var3.getMessage());
//      }
//
//    }
//  }
//
//  private String getSystemCode() {
//    Enumeration<String> headerNames = this.request.getHeaderNames();
//    String systemCode = "";
//
//    while(headerNames.hasMoreElements()) {
//      String name = (String)headerNames.nextElement();
//      if (name.equalsIgnoreCase("systemcode")) {
//        systemCode = this.request.getHeader(name);
//        break;
//      }
//    }
//
//    return systemCode;
//  }
//
//  private String getClientIp() {
//    String publicIp = this.request.getHeader("Clientip");
//    String ip = this.request.getHeader("X-Forwarded-For");
//    if (ip == null) {
//      ip = this.request.getHeader("Proxy-Client-IP");
//    }
//
//    if (ip == null) {
//      ip = this.request.getHeader("WL-Proxy-Client-IP");
//    }
//
//    if (ip == null) {
//      ip = this.request.getHeader("HTTP_CLIENT_IP");
//    }
//
//    if (ip == null) {
//      ip = this.request.getHeader("HTTP_X_FORWARDED_FOR");
//    }
//
//    if (ip == null) {
//      ip = this.request.getRemoteAddr();
//    }
//
//    if (ip != null && ip.equals("127.0.0.1") && publicIp != null && !publicIp.equals("127.0.0.1")) {
//      ip = publicIp;
//    }
//
//    if (ip == null) {
//      ip = "0.0.0.0";
//    }
//
//    return ip;
//  }
//
//  @Generated
//  public PrivacyLogAspect(final CoreService coreService, final CoreRestApi coreRestApi, final HttpServletRequest request) {
//    this.coreService = coreService;
//    this.coreRestApi = coreRestApi;
//    this.request = request;
//  }
//}
//
