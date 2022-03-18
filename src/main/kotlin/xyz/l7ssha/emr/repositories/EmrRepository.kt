package xyz.l7ssha.emr.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.entities.AbstractSoftDelete

@NoRepositoryBean
@Component
interface EmrRepository<T : AbstractSoftDelete> : JpaRepository<T, Long>, JpaSpecificationExecutor<T>
