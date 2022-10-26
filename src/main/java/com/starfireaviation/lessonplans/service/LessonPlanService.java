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

package com.starfireaviation.lessonplans.service;

import com.starfireaviation.lessonplans.exception.ResourceNotFoundException;
import com.starfireaviation.lessonplans.model.Activity;
import com.starfireaviation.lessonplans.model.ActivityRepository;
import com.starfireaviation.lessonplans.model.LessonPlan;
import com.starfireaviation.lessonplans.model.LessonPlanRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * LessonPlanService.
 */
@Slf4j
public class LessonPlanService {

    /**
     * LessonPlanRepository.
     */
    private final LessonPlanRepository lessonPlanRepository;

    /**
     * ActivityRepository.
     */
    private final ActivityRepository activityRepository;

    /**
     * LessonPlanService.
     *
     * @param lpRepository LessonPlanRepository
     * @param aRepostory   ActivityRepository
     */
    public LessonPlanService(final LessonPlanRepository lpRepository,
                             final ActivityRepository aRepostory) {
        lessonPlanRepository = lpRepository;
        activityRepository = aRepostory;
    }

    /**
     * Creates a lessonPlan.
     *
     * @param lessonPlan LessonPlan
     * @return LessonPlan
     * @throws ResourceNotFoundException when resultant lesson plan is not found
     */
    public LessonPlan store(final LessonPlan lessonPlan) throws ResourceNotFoundException {
        if (lessonPlan == null) {
            return null;
        }
        final LessonPlan lessonPlanEntity = lessonPlanRepository.save(lessonPlan);
        if (lessonPlan.getActivities() != null) {
            lessonPlan
                    .getActivities()
                    .forEach(activity -> {
                        activity.setLessonPlan(lessonPlanEntity);
                        activityRepository.save(activity);
                    });
        }
        return get(lessonPlanEntity.getId());
    }

    /**
     * Deletes a lessonPlan.
     *
     * @param id Long
     * @return LessonPlan
     * @throws ResourceNotFoundException when lesson plan is not found
     */
    public LessonPlan delete(final long id) throws ResourceNotFoundException {
        final LessonPlan lessonPlan = get(id);
        if (lessonPlan != null) {
            if (lessonPlan.getActivities() != null) {
                lessonPlan
                        .getActivities()
                        .forEach(activityRepository::delete);
            }
            lessonPlanRepository.delete(lessonPlan);
        }
        return lessonPlan;
    }

    /**
     * Gets all lessonPlan.
     *
     * @return list of LessonPlan
     * @throws ResourceNotFoundException when lesson plan is not found
     */
    public List<LessonPlan> getAll() throws ResourceNotFoundException {
        final List<LessonPlan> lessonPlans = new ArrayList<>();
        final List<LessonPlan> lessonPlanEntities = lessonPlanRepository.findAll();
        for (final LessonPlan lessonPlanEntity : lessonPlanEntities) {
            lessonPlans.add(get(lessonPlanEntity.getId()));
        }
        return lessonPlans;
    }

    /**
     * Gets a lessonPlan.
     *
     * @param id Long
     * @return LessonPlan
     * @throws ResourceNotFoundException when lesson plan is not found
     */
    public LessonPlan get(final long id) throws ResourceNotFoundException {
        final LessonPlan lessonPlan = lessonPlanRepository.findById(id);
        if (lessonPlan == null) {
            throw new ResourceNotFoundException(String.format("No lesson plan found for ID [%s]", id));
        }
        final List<Activity> activityEntities = activityRepository.findActivityByLessonPlanId(id);
        if (activityEntities != null) {
            lessonPlan.setActivities(new ArrayList<>(activityEntities));
        }
        return lessonPlan;
    }

}
