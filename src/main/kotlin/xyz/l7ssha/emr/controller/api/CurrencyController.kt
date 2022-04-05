package xyz.l7ssha.emr.controller.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import xyz.l7ssha.emr.configuration.security.AuthenticationFacade
import xyz.l7ssha.emr.dto.RSQLSpecification
import xyz.l7ssha.emr.dto.price.currency.CurrencyCreateInputDto
import xyz.l7ssha.emr.entities.products.Currency
import xyz.l7ssha.emr.mapper.PriceMapper
import xyz.l7ssha.emr.service.entity.CurrencyEntityService

@RestController
@RequestMapping("/api/currencies")
class CurrencyController(
    @Autowired private val currencyService: CurrencyEntityService,
    @Autowired private val priceMapper: PriceMapper,
    @Autowired private val authenticationFacade: AuthenticationFacade
) {
    @GetMapping
    fun getAllAction(spec: RSQLSpecification<Currency>, pageable: Pageable) =
        currencyService.findAll(spec.getFiltersAndSpecification(), pageable)
            .map { priceMapper.currencyToOutputDto(it) }

    @GetMapping("/{id}")
    fun getItemAction(@PathVariable id: Long) =
        currencyService.getById(id)
            .let { priceMapper.currencyToOutputDto(it) }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE_CURRENCIES')")
    fun postItemAction(@RequestBody inputDto: CurrencyCreateInputDto) =
        priceMapper.inputDtoToCurrency(inputDto)
            .let { currencyService.save(it) }
            .let { priceMapper.currencyToOutputDto(it) }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('DELETE_CURRENCIES')")
    fun deleteItemAction(@PathVariable id: Long) =
        currencyService.delete(currencyService.getById(id), authenticationFacade.loggedInUser)
}
