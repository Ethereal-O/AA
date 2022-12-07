//package com.example.bookstore.Utils;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class Interceptor implements WebMvcConfigurer {
////    @Bean
////    public Adapter sessionValidateInterceptor(){
////        return new Adapter();
////    }
////
////    @Override
////    public void addInterceptors(InterceptorRegistry registry){
////        registry.addInterceptor(sessionValidateInterceptor()).addPathPatterns("/**")
////                .excludePathPatterns("/Login").excludePathPatterns("/Logout")
////                .excludePathPatterns("/Register").excludePathPatterns("/checkSession");
////    }
//
//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
////        corsConfiguration.addAllowedOrigin("http://localhost:3000");
////        corsConfiguration.addAllowedOrigin("http://localhost:8070");
////        corsConfiguration.addAllowedOrigin("http://localhost:3001");
////        corsConfiguration.addAllowedOrigin("https://localhost:3000");
////        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.setAllowCredentials(true);
//        return corsConfiguration;
//    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig());
//        return new CorsFilter(source);
//    }
//}
