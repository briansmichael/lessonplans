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
import com.starfireaviation.groundschool.jobs.UpdateCourses;
import com.starfireaviation.groundschool.model.ActivityRepository;
import com.starfireaviation.groundschool.model.AddressRepository;
import com.starfireaviation.groundschool.model.AnswerRepository;
import com.starfireaviation.groundschool.model.CommentRepository;
import com.starfireaviation.groundschool.model.EventParticipantRepository;
import com.starfireaviation.groundschool.model.EventRepository;
import com.starfireaviation.groundschool.model.LessonPlanRepository;
import com.starfireaviation.groundschool.model.QuestionReferenceMaterialRepository;
import com.starfireaviation.groundschool.model.QuestionRepository;
import com.starfireaviation.groundschool.model.QuizQuestionRepository;
import com.starfireaviation.groundschool.model.QuizRepository;
import com.starfireaviation.groundschool.model.ReferenceMaterialRepository;
import com.starfireaviation.groundschool.model.UserRepository;
import com.starfireaviation.groundschool.service.AddressService;
import com.starfireaviation.groundschool.service.AnswerService;
import com.starfireaviation.groundschool.service.CommentService;
import com.starfireaviation.groundschool.service.EventService;
import com.starfireaviation.groundschool.service.LessonPlanService;
import com.starfireaviation.groundschool.service.LessonService;
import com.starfireaviation.groundschool.service.NotificationService;
import com.starfireaviation.groundschool.service.QuestionService;
import com.starfireaviation.groundschool.service.QuizService;
import com.starfireaviation.groundschool.service.ReferenceMaterialService;
import com.starfireaviation.groundschool.service.UserService;
import com.starfireaviation.groundschool.util.GSDecryptor;
import com.starfireaviation.groundschool.validation.CommentValidator;
import com.starfireaviation.groundschool.validation.EventValidator;
import com.starfireaviation.groundschool.validation.LessonValidator;
import com.starfireaviation.groundschool.validation.QuizValidator;
import com.starfireaviation.groundschool.validation.ReferenceMaterialValidator;
import com.starfireaviation.groundschool.validation.UserValidator;
import com.starfireaviation.lessonplans.model.ActivityRepository;
import com.starfireaviation.lessonplans.model.LessonPlanRepository;
import com.starfireaviation.lessonplans.service.LessonPlanService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
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
     * @param aRepostory   ActivityRepository
     * @return LessonPlanService
     */
    @Bean
    public LessonPlanService lessonPlanService(final LessonPlanRepository lpRepository,
                                               final ActivityRepository aRepostory) {
        return new LessonPlanService(lpRepository, aRepostory);
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

}
