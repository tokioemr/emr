package xyz.l7ssha.emr.service.entity

import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import xyz.l7ssha.emr.entities.user.User

@Service
class UserEntityService : EntityService<User>() {
    fun findByEmail(email: String) = repository.findOne(
        Specification.where { root, _, criteriaBuilder -> criteriaBuilder.equal(root.get<String>("email"), email) }
    )
}
