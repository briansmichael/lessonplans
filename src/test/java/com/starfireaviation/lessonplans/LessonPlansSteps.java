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

package com.starfireaviation.lessonplans;

import com.starfireaviation.common.model.LessonPlan;
import com.starfireaviation.lessonplans.model.LessonPlanEntity;
import com.starfireaviation.common.CommonConstants;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class LessonPlansSteps {

    /**
     * URL.
     */
    protected static final String URL = "http://localhost:8080";

    /**
     * ORGANIZATION.
     */
    protected static final String ORGANIZATION = "TEST_ORG";

    /**
     * RestTemplate.
     */
    protected RestTemplate restTemplate = new RestTemplateBuilder()
            .errorHandler(new RestTemplateResponseErrorHandler()).build();

    @Autowired
    protected TestContext testContext;

    @Before
    public void init() {
        testContext.reset();
    }

    @Given("^I have a lesson plan$")
    public void iHaveALessonPlan() throws Throwable {
        testContext.setLessonPlan(new LessonPlan());
    }

    @And("^The lesson plan has title with (.*) characters$")
    public void theLessonPlanHasTitleWithXCharacters(final int characterCount) {
        // TODO
    }

    @And("^The lesson plan has summary with (.*) characters$")
    public void theLessonPlanHasSummaryWithXCharacters(final int characterCount) {
        // TODO
    }

    @And("^A lesson plan exists$")
    public void aLessonPlanExists() throws Throwable {
        // TODO
    }

    @When("^I submit the lesson plan$")
    public void iAddTheLessonPlan() throws Throwable {
        log.info("I submit the lesson plan");
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (testContext.getOrganization() != null) {
            headers.add(CommonConstants.ORGANIZATION_HEADER_KEY, testContext.getOrganization());
        }
        if (testContext.getCorrelationId() != null) {
            headers.add(CommonConstants.CORRELATION_ID_HEADER_KEY, testContext.getCorrelationId());
        }
        //final HttpEntity<Question> httpEntity = new HttpEntity<>(testContext.getQuestion(), headers);
        //testContext.setResponse(restTemplate.postForEntity(URL, httpEntity, Void.class));
    }

    @When("^I get the lesson plan$")
    public void iGetTheLessonPlan() throws Throwable {
        // TODO
    }

    @When("^I submit the lesson plan for update$")
    public void iSubmitTheLessonPlanForUpdate() throws Throwable {
        // TODO
    }

    @When("^I delete the lesson plan$")
    public void iDeleteTheLessonPlan() throws Throwable {
        // TODO
    }

    @When("^I get all lesson plans$")
    public void iGetAllLessonPlans() throws Throwable {
        // TODO
    }

    @And("^A lesson plan should be received$")
    public void aLessonPlanShouldBeReceived() throws Throwable {
        // TODO
    }

    @And("^The lesson plan should be removed$")
    public void theLessonPlanShouldBeRemoved() throws Throwable {
        // TODO
    }

}
