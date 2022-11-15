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

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.starfireaviation.common.exception.AccessDeniedException;
import com.starfireaviation.common.exception.InvalidPayloadException;
import com.starfireaviation.common.exception.ResourceNotFoundException;
import com.starfireaviation.common.model.Activity;
import com.starfireaviation.lessonplans.model.ActivityEntity;
import com.starfireaviation.lessonplans.service.ActivityService;
import com.starfireaviation.lessonplans.validation.ActivityValidator;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.stream.Collectors;

/**
 * ActivityController.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping({ "/activities" })
public class ActivityController {

    /**
     * ActivityService.
     */
    private final ActivityService activityService;

    /**
     * ActivityValidator.
     */
    private final ActivityValidator activityValidator;

    /**
     * Activity Cache.
     */
    private final IMap<Long, Activity> cache;

    /**
     * ActivityController.
     *
     * @param aService   ActivityService
     * @param aValidator ActivityValidator
     * @param hazelcastInstance HazelcastInstance
     */
    public ActivityController(final ActivityService aService,
                              final ActivityValidator aValidator,
                              @Qualifier("activities") final HazelcastInstance hazelcastInstance) {
        activityService = aService;
        activityValidator = aValidator;
        cache = hazelcastInstance.getMap("activities");
    }

    /**
     * Creates an activity.
     *
     * @param activity Activity
     * @param principal  Principal
     * @return Activity
     * @throws ResourceNotFoundException when user is not found
     * @throws AccessDeniedException     when user doesn't have permission to
     *                                   perform operation
     * @throws InvalidPayloadException   when invalid data is provided
     */
    @PostMapping
    public Activity post(@RequestBody final Activity activity, final Principal principal)
            throws InvalidPayloadException,
            ResourceNotFoundException, AccessDeniedException {
        activityValidator.validate(activity);
        activityValidator.accessAdminOrInstructor(principal);
        return map(activityService.store(map(activity)));
    }

    /**
     * Gets an activity.
     *
     * @param activityId Long
     * @param principal    Principal
     * @return Activity
     * @throws ResourceNotFoundException when activity is not found
     * @throws AccessDeniedException     when user doesn't have permission to
     *                                   perform operation
     */
    @GetMapping(path = { "/{activityId}" })
    public Activity get(@PathVariable("activityId") final Long activityId, final Principal principal)
            throws ResourceNotFoundException, AccessDeniedException {
        activityValidator.accessAdminOrInstructor(principal);
        if (cache.containsKey(activityId)) {
            return cache.get(activityId);
        }
        final Activity activity = map(activityService.get(activityId));
        cache.put(activityId, activity);
        return activity;
    }

    /**
     * Updates an activity.
     *
     * @param activity Activity
     * @param principal  Principal
     * @return Activity
     * @throws ResourceNotFoundException when user is not found
     * @throws AccessDeniedException     when user doesn't have permission to
     *                                   perform operation
     * @throws InvalidPayloadException   when invalid data is provided
     */
    @PutMapping
    public Activity put(@RequestBody final Activity activity, final Principal principal)
            throws InvalidPayloadException, ResourceNotFoundException, AccessDeniedException {
        activityValidator.validate(activity);
        activityValidator.accessAdminOrInstructor(principal);
        final Activity updatedActivity = map(activityService.store(map(activity)));
        cache.put(updatedActivity.getId(), updatedActivity);
        return updatedActivity;
    }

    /**
     * Deletes an activity.
     *
     * @param activityId Long
     * @param principal    Principal
     * @throws ResourceNotFoundException when activity is not found
     * @throws AccessDeniedException     when user doesn't have permission to
     *                                   perform operation
     */
    @DeleteMapping(path = { "/{activityId}" })
    public void delete(@PathVariable("activityId") final Long activityId, final Principal principal)
            throws ResourceNotFoundException, AccessDeniedException {
        activityValidator.accessAdminOrInstructor(principal);
        activityService.delete(activityId);
        cache.remove(activityId);
    }

    /**
     * Get all activities.
     *
     * @param principal Principal
     * @return list of Activity
     * @throws ResourceNotFoundException when activity is not found
     * @throws AccessDeniedException     when user doesn't have permission to
     *                                   perform operation
     */
    @GetMapping
    public List<Activity> list(final Principal principal) throws ResourceNotFoundException, AccessDeniedException {
        activityValidator.accessAdminOrInstructor(principal);
        return activityService.getAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    /**
     * Maps an ActivityEntity to an Activity.
     *
     * @param activityEntity ActivityEntity
     * @return Activity
     */
    private Activity map(final ActivityEntity activityEntity) {
        final Activity activity = new Activity();
        activity.setTitle(activityEntity.getTitle());
        activity.setId(activityEntity.getId());
        activity.setActivityType(activityEntity.getActivityType());
        activity.setDuration(activityEntity.getDuration());
        activity.setCreatedAt(activityEntity.getCreatedAt());
        activity.setUpdatedAt(activityEntity.getUpdatedAt());
        activity.setReferenceId(activityEntity.getReferenceId());
        return activity;
    }

    /**
     * Maps an Activity to an ActivityEntity.
     *
     * @param activity Activity
     * @return ActivityEntity
     */
    private ActivityEntity map(final Activity activity) {
        final ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setTitle(activity.getTitle());
        activityEntity.setId(activity.getId());
        activityEntity.setActivityType(activity.getActivityType());
        activityEntity.setDuration(activity.getDuration());
        activityEntity.setCreatedAt(activity.getCreatedAt());
        activityEntity.setUpdatedAt(activity.getUpdatedAt());
        activityEntity.setReferenceId(activity.getReferenceId());
        return activityEntity;
    }
}
