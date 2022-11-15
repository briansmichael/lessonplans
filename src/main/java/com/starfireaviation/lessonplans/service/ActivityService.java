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

import com.starfireaviation.common.exception.ResourceNotFoundException;
import com.starfireaviation.lessonplans.model.ActivityEntity;
import com.starfireaviation.lessonplans.model.ActivityRepository;
import com.starfireaviation.lessonplans.model.LessonPlanActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ActivityService.
 */
@Slf4j
@Service
public class ActivityService {

    /**
     * ActivityRepository.
     */
    private final ActivityRepository activityRepository;

    /**
     * LessonPlanActivityRepository.
     */
    private final LessonPlanActivityRepository lessonPlanActivityRepository;

    /**
     * ActivityService.
     *
     * @param lpaRepository LessonPlanActivityRepository
     * @param aRepostory   ActivityRepository
     */
    public ActivityService(final LessonPlanActivityRepository lpaRepository,
                           final ActivityRepository aRepostory) {
        lessonPlanActivityRepository = lpaRepository;
        activityRepository = aRepostory;
    }

    /**
     * Creates an activity.
     *
     * @param activity Activity
     * @return Activity
     * @throws ResourceNotFoundException when resultant activity is not found
     */
    public ActivityEntity store(final ActivityEntity activity) throws ResourceNotFoundException {
        if (activity == null) {
            return null;
        }
        return activityRepository.save(activity);
    }

    /**
     * Deletes an activity.
     *
     * @param activityId Long
     * @throws ResourceNotFoundException when activity is not found
     */
    public void delete(final Long activityId) throws ResourceNotFoundException {
        lessonPlanActivityRepository
                .findByActivityId(activityId)
                .orElse(new ArrayList<>())
                .forEach(lessonPlanActivityRepository::delete);
        activityRepository.delete(get(activityId));
    }

    /**
     * Gets all activities.
     *
     * @return list of Activity
     */
    public List<ActivityEntity> getAll() {
        return activityRepository.findAll().orElseThrow();
    }

    /**
     * Gets an activity.
     *
     * @param activityId Long
     * @return Activity
     * @throws ResourceNotFoundException when activity is not found
     */
    public ActivityEntity get(final long activityId) throws ResourceNotFoundException {
        final ActivityEntity activity = activityRepository.findById(activityId).orElse(null);
        if (activity == null) {
            throw new ResourceNotFoundException(String.format("No activity found for ID [%s]", activityId));
        }
        return activity;
    }

}
