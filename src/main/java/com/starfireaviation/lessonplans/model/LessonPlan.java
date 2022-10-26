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

import com.starfireaviation.model.CommonConstants;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * LessonPlan.
 */
@Data
@Entity
@Table(name = "LESSON_PLAN")
public class LessonPlan implements Serializable {

    /**
     * Default SerialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Created At.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt = new Date();

    /**
     * Updated At.
     */
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Date updatedAt = new Date();

    /**
     * Title.
     */
    @Column(name = "title", nullable = false, length = CommonConstants.TWO_HUNDRED_FIFTY_FIVE)
    private String title;

    /**
     * Summary.
     */
    @Column(name = "summary", nullable = false, length = CommonConstants.TWO_THOUSAND)
    private String summary;

    /**
     * Objective.
     */
    @Column(name = "objective", length = CommonConstants.TWO_THOUSAND)
    private String objective;

    /**
     * Content.
     */
    @Column(name = "content", length = CommonConstants.TWO_THOUSAND)
    private String content;

    /**
     * Schedule.
     */
    @Column(name = "schedule", length = CommonConstants.TWO_THOUSAND)
    private String schedule;

    /**
     * Equipment.
     */
    @Column(name = "equipment", length = CommonConstants.TWO_THOUSAND)
    private String equipment;

    /**
     * Instructor's Actions.
     */
    @Column(name = "instructor_actions", length = CommonConstants.TWO_THOUSAND)
    private String instructorActions;

    /**
     * Student's Actions.
     */
    @Column(name = "student_actions", length = CommonConstants.TWO_THOUSAND)
    private String studentActions;

    /**
     * Completion Standards.
     */
    @Column(name = "completion_standards", length = CommonConstants.TWO_THOUSAND)
    private String completionStandards;

    /**
     * Activities.
     */
    @Transient
    private List<Activity> activities;

}
