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

package com.starfireaviation.lessonplans.controller;

import com.starfireaviation.lessonplans.exception.AccessDeniedException;
import com.starfireaviation.lessonplans.exception.InvalidPayloadException;
import com.starfireaviation.lessonplans.exception.ResourceNotFoundException;
import com.starfireaviation.lessonplans.model.LessonPlan;
import com.starfireaviation.lessonplans.service.LessonPlanService;
import com.starfireaviation.lessonplans.validation.LessonPlanValidator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * LessonPlanController.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping({
        "/lessonplans"
})
public class LessonPlanController {

    /**
     * LessonPlanService.
     */
    private final LessonPlanService lessonPlanService;

    /**
     * LessonPlanValidator.
     */
    private final LessonPlanValidator lessonPlanValidator;

    /**
     * LessonPlanController.
     *
     * @param lpService   LessonPlanService
     * @param lpValidator LessonPlanValidator
     */
    public LessonPlanController(final LessonPlanService lpService,
                                final LessonPlanValidator lpValidator) {
        lessonPlanService = lpService;
        lessonPlanValidator = lpValidator;
    }

    /**
     * Creates a lessonPlan.
     *
     * @param lessonPlan LessonPlan
     * @param principal  Principal
     * @return LessonPlan
     * @throws ResourceNotFoundException when user is not found
     * @throws AccessDeniedException     when user doesn't have permission to
     *                                   perform operation
     * @throws InvalidPayloadException   when invalid data is provided
     */
    @PostMapping
    public LessonPlan post(@RequestBody final LessonPlan lessonPlan, final Principal principal)
            throws InvalidPayloadException,
            ResourceNotFoundException, AccessDeniedException {
        lessonPlanValidator.validate(lessonPlan);
        lessonPlanValidator.accessAdminOrInstructor(principal);
        return lessonPlanService.store(lessonPlan);
    }

    /**
     * Gets a lessonPlan.
     *
     * @param lessonPlanId Long
     * @param principal    Principal
     * @return LessonPlan
     * @throws ResourceNotFoundException when lesson plan is not found
     * @throws AccessDeniedException     when user doesn't have permission to
     *                                   perform operation
     */
    @GetMapping(path = {
            "/{lessonPlanId}"
    })
    public LessonPlan get(@PathVariable("lessonPlanId") final long lessonPlanId, final Principal principal)
            throws ResourceNotFoundException, AccessDeniedException {
        lessonPlanValidator.accessAdminOrInstructor(principal);
        return lessonPlanService.get(lessonPlanId);
    }

    /**
     * Updates a lessonPlan.
     *
     * @param lessonPlan LessonPlan
     * @param principal  Principal
     * @return LessonPlan
     * @throws ResourceNotFoundException when user is not found
     * @throws AccessDeniedException     when user doesn't have permission to
     *                                   perform operation
     * @throws InvalidPayloadException   when invalid data is provided
     */
    @PutMapping
    public LessonPlan put(@RequestBody final LessonPlan lessonPlan, final Principal principal)
            throws InvalidPayloadException,
            ResourceNotFoundException, AccessDeniedException {
        lessonPlanValidator.validate(lessonPlan);
        lessonPlanValidator.accessAdminOrInstructor(principal);
        return lessonPlanService.store(lessonPlan);
    }

    /**
     * Deletes a lessonPlan.
     *
     * @param lessonPlanId Long
     * @param principal    Principal
     * @return LessonPlan
     * @throws ResourceNotFoundException when lesson plan is not found
     * @throws AccessDeniedException     when user doesn't have permission to
     *                                   perform operation
     */
    @DeleteMapping(path = {
            "/{lessonPlanId}"
    })
    public LessonPlan delete(@PathVariable("lessonPlanId") final long lessonPlanId, final Principal principal)
            throws ResourceNotFoundException, AccessDeniedException {
        lessonPlanValidator.accessAdminOrInstructor(principal);
        return lessonPlanService.delete(lessonPlanId);
    }

    /**
     * Get all lessonPlans.
     *
     * @param principal Principal
     * @return list of LessonPlans
     * @throws ResourceNotFoundException when lesson plan is not found
     * @throws AccessDeniedException     when user doesn't have permission to
     *                                   perform operation
     */
    @GetMapping
    public List<LessonPlan> list(final Principal principal) throws ResourceNotFoundException, AccessDeniedException {
        lessonPlanValidator.accessAdminOrInstructor(principal);
        return lessonPlanService.getAll();
    }
}
