/*
 *  Copyright (C) 2022 Starfire Aviation, LLC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.starfireaviation.lessonplans.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.starfireaviation.common.CommonConstants;
import com.starfireaviation.lessonplans.model.ActivityRepository;
import com.starfireaviation.lessonplans.model.LessonPlanActivityRepository;
import com.starfireaviation.lessonplans.model.LessonPlanRepository;
import com.starfireaviation.lessonplans.service.LessonPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.time.Duration;

/**
 * ServiceConfig.
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({ ApplicationProperties.class })
public class ServiceConfig {

    /**
     * LessonPlanService.
     *
     * @param lpRepository LessonPlanRepository
     * @param lpaRepository LessonPlanActivityRepository
     * @param aRepostory   ActivityRepository
     * @return LessonPlanService
     */
    @Bean
    public LessonPlanService lessonPlanService(final LessonPlanRepository lpRepository,
                                               final LessonPlanActivityRepository lpaRepository,
                                               final ActivityRepository aRepostory) {
        return new LessonPlanService(lpRepository, lpaRepository, aRepostory);
    }

    /**
     * HttpClient.
     *
     * @return HttpClient
     */
    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

    /**
     * ObjectMapper.
     *
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    /**
     * Creates a rest template with default timeout settings. The bean definition
     * will be updated to accept timeout
     * parameters once those are part of the Customer settings.
     *
     * @param restTemplateBuilder RestTemplateBuilder
     * @param props   ApplicationProperties
     *
     * @return Rest Template with request, read, and connection timeouts set
     */
    @Bean
    public RestTemplate restTemplate(
            final RestTemplateBuilder restTemplateBuilder,
            final ApplicationProperties props) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(props.getConnectTimeout()))
                .setReadTimeout(Duration.ofMillis(props.getReadTimeout()))
                .additionalMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    /**
     * Hazelcast LessonPlans Instance.
     *
     * @return HazelcastInstance
     */
    @Bean("lessonplans")
    public HazelcastInstance hazelcastQuestionsInstance() {
        return Hazelcast.newHazelcastInstance(
                new Config().addMapConfig(
                        new MapConfig("lessonplans")
                                .setTimeToLiveSeconds(CommonConstants.THREE_HUNDRED)
                                .setMaxIdleSeconds(CommonConstants.THREE_HUNDRED)));
    }

}
