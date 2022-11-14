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
 * LessonPlanRepository.
 */
public interface LessonPlanRepository extends Repository<LessonPlanEntity, Long> {

    /**
     * Deletes a lessonPlan.
     *
     * @param lessonPlan LessonPlanEntity
     */
    void delete(LessonPlanEntity lessonPlan);

    /**
     * Gets all lessonPlan.
     *
     * @return list of LessonPlanEntity
     */
    Optional<List<LessonPlanEntity>> findAll();

    /**
     * Gets a lessonPlan.
     *
     * @param id Long
     * @return LessonPlanEntity
     */
    Optional<LessonPlanEntity> findById(Long id);

    /**
     * Saves a lessonPlan.
     *
     * @param lessonPlan LessonPlanEntity
     * @return LessonPlanEntity
     */
    LessonPlanEntity save(LessonPlanEntity lessonPlan);
}
