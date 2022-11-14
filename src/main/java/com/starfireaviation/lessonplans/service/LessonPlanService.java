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
import com.starfireaviation.lessonplans.model.LessonPlanActivity;
import com.starfireaviation.lessonplans.model.LessonPlanActivityRepository;
import com.starfireaviation.lessonplans.model.LessonPlanEntity;
import com.starfireaviation.lessonplans.model.LessonPlanRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
     * LessonPlanRepository.
     */
    private final LessonPlanActivityRepository lessonPlanActivityRepository;

    /**
     * ActivityRepository.
     */
    private final ActivityRepository activityRepository;

    /**
     * LessonPlanService.
     *
     * @param lpRepository LessonPlanRepository
     * @param lpaRepository LessonPlanActivityRepository
     * @param aRepostory   ActivityRepository
     */
    public LessonPlanService(final LessonPlanRepository lpRepository,
                             final LessonPlanActivityRepository lpaRepository,
                             final ActivityRepository aRepostory) {
        lessonPlanRepository = lpRepository;
        lessonPlanActivityRepository = lpaRepository;
        activityRepository = aRepostory;
    }

    /**
     * Creates a lessonPlan.
     *
     * @param lessonPlan LessonPlan
     * @return LessonPlan
     * @throws ResourceNotFoundException when resultant lesson plan is not found
     */
    public LessonPlanEntity store(final LessonPlanEntity lessonPlan) throws ResourceNotFoundException {
        if (lessonPlan == null) {
            return null;
        }
        return lessonPlanRepository.save(lessonPlan);
    }

    /**
     * Deletes a lessonPlan.
     *
     * @param lessonPlanId Long
     * @throws ResourceNotFoundException when lesson plan is not found
     */
    public void delete(final Long lessonPlanId) throws ResourceNotFoundException {
        lessonPlanActivityRepository
                .findByLessonPlanId(lessonPlanId)
                .orElse(new ArrayList<>())
                .forEach(lessonPlanActivityRepository::delete);
        lessonPlanRepository.delete(get(lessonPlanId));
    }

    /**
     * Gets all lessonPlan.
     *
     * @return list of LessonPlan
     */
    public List<LessonPlanEntity> getAll() {
        return lessonPlanRepository.findAll().orElseThrow();
    }

    /**
     * Gets a lessonPlan.
     *
     * @param lessonPlanId Long
     * @return LessonPlan
     * @throws ResourceNotFoundException when lesson plan is not found
     */
    public LessonPlanEntity get(final long lessonPlanId) throws ResourceNotFoundException {
        final LessonPlanEntity lessonPlan = lessonPlanRepository.findById(lessonPlanId).orElse(null);
        if (lessonPlan == null) {
            throw new ResourceNotFoundException(String.format("No lesson plan found for ID [%s]", lessonPlanId));
        }
        return lessonPlan;
    }

    /**
     * Gets list of Activities for the given LessonPlan.
     *
     * @param lessonPlanId LessonPlan ID
     * @return list of ActivityEntity
     */
    public List<ActivityEntity> getActivitiesForLessonPlan(final Long lessonPlanId) {
        return lessonPlanActivityRepository
                .findByLessonPlanId(lessonPlanId)
                .orElse(new ArrayList<>())
                .stream()
                .map(lessonPlanActivity -> activityRepository
                        .findById(lessonPlanActivity.getActivityId())
                        .orElse(new ActivityEntity()))
                .collect(Collectors.toList());
    }

    /**
     * Links Activity to a LessonPlan.
     *
     * @param lessonPlanId LessonPlan ID
     * @param activityEntities list of Activity
     */
    public void linkActivities(final Long lessonPlanId, final List<ActivityEntity> activityEntities) {
        final List<Long> existing = new ArrayList<>();
        final List<Long> activityIds = activityEntities
                .stream()
                .map(ActivityEntity::getId)
                .collect(Collectors.toList());
        lessonPlanActivityRepository
                .findByLessonPlanId(lessonPlanId)
                .orElse(new ArrayList<>())
                .stream()
                .filter(lessonPlanActivity -> !activityIds.contains(lessonPlanActivity.getActivityId()))
                .forEach(lessonPlanActivity -> existing.add(activityRepository
                        .findById(lessonPlanActivity.getActivityId())
                        .orElse(new ActivityEntity())
                        .getId()));
        for (final ActivityEntity activityEntity : activityEntities) {
            if (!existing.contains(activityEntity.getId())) {
                final LessonPlanActivity lessonPlanActivity = new LessonPlanActivity();
                lessonPlanActivity.setActivityId(activityEntity.getId());
                lessonPlanActivity.setLessonPlanId(lessonPlanId);
                lessonPlanActivity.setCreatedAt(new Date());
                lessonPlanActivity.setUpdatedAt(new Date());
                lessonPlanActivityRepository.save(lessonPlanActivity);
            }
        }
        // TODO handle removing IDs that should be de-linked
    }
}
