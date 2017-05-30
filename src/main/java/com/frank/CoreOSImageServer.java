package com.frank;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Controller
public class CoreOSImageServer extends WebMvcConfigurerAdapter {

private static final Logger log = LoggerFactory.getLogger(CoreOSImageServer.class);

@Value("${coreos.image.folder}")
private String coreosImageFolder;

private static String ipxe;
private static String bootstrap;

static {
    try {
        ipxe = new String(FileCopyUtils.copyToByteArray(new ClassPathResource("boot.ipxe").getInputStream()),
               StandardCharsets.UTF_8);
        bootstrap = new String(FileCopyUtils.copyToByteArray(new ClassPathResource("cloud-config-bootstrap.sh").getInputStream()),
                StandardCharsets.UTF_8);
        
    } catch (IOException e) {
        e.printStackTrace();
    }
}

@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    
    log.info("Bind registry with image folder: " + coreosImageFolder);
    
    registry
      .addResourceHandler("/iso/**")
      .addResourceLocations(coreosImageFolder);
 }


@ResponseBody
@RequestMapping("/boot")
public String boot(HttpServletRequest request) {
    log.info("Start to boot");
    log.info("" + request.getParameterMap());
    return ipxe;
    
}

@ResponseBody
@RequestMapping("/cloud-config-bootstrap.sh")
public String bootstrap(HttpServletRequest request) {
    log.info("Download bootstrap script");
    log.info("" + request.getParameterMap());
    return bootstrap;
    
}

}