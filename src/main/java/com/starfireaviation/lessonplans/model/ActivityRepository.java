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
 * ActivityRepository.
 */
public interface ActivityRepository extends Repository<ActivityEntity, Long> {

    /**
     * Deletes an activity.
     *
     * @param activity Activity
     */
    void delete(ActivityEntity activity);

    /**
     * Gets all activities.
     *
     * @return list of Activity
     */
    Optional<List<ActivityEntity>> findAll();

    /**
     * Gets an activity.
     *
     * @param id Long
     * @return Activity
     */
    Optional<ActivityEntity> findById(Long id);

    /**
     * Saves an activity.
     *
     * @param activity Activity
     * @return Activity
     */
    ActivityEntity save(ActivityEntity activity);
}
