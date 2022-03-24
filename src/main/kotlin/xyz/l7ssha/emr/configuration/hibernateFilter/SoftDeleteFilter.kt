package xyz.l7ssha.emr.configuration.hibernateFilter

import org.hibernate.annotations.Filter

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Filter(name = "deletedAtFilter", condition = "deletedAt IS NULL")
annotation class SoftDeleteFilter
