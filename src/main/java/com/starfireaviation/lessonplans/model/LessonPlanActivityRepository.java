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

package com.starfireaviation.lessonplans.model;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * LessonPlanActivityRepository.
 */
public interface LessonPlanActivityRepository extends Repository<LessonPlanActivity, Long> {

    /**
     * Deletes a LessonPlanActivity.
     *
     * @param lessonPlanActivity LessonPlanActivity
     */
    void delete(LessonPlanActivity lessonPlanActivity);

    /**
     * Gets all LessonPlanActivity for a lesson plan.
     *
     * @param lessonPlanId LessonPlan ID
     * @return list of LessonPlanActivity
     */
    Optional<List<LessonPlanActivity>> findByLessonPlanId(Long lessonPlanId);

    /**
     * Gets all LessonPlanActivity for an activity.
     *
     * @param activityId Activity ID
     * @return list of LessonPlanActivity
     */
    Optional<List<LessonPlanActivity>> findByActivityId(Long activityId);

    /**
     * Gets a LessonPlanActivity.
     *
     * @param id Long
     * @return Activity
     */
    Optional<LessonPlanActivity> findById(Long id);

    /**
     * Saves a LessonPlanActivity.
     *
     * @param lessonPlanActivity LessonPlanActivity
     * @return LessonPlanActivity
     */
    LessonPlanActivity save(LessonPlanActivity lessonPlanActivity);
}
