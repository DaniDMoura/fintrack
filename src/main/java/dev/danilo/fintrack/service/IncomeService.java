package dev.danilo.fintrack.service;

import dev.danilo.fintrack.dto.request.IncomeAmountRequest;
import dev.danilo.fintrack.dto.request.IncomeRequest;
import dev.danilo.fintrack.dto.response.IncomeResponse;
import dev.danilo.fintrack.entity.Income;
import dev.danilo.fintrack.exception.IncomeNotFoundException;
import dev.danilo.fintrack.repository.IncomeRepository;
import dev.danilo.fintrack.util.FindAuthenticatedUser;
import dev.danilo.fintrack.util.mapper.IncomeMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class IncomeService {

  private final IncomeRepository incomeRepository;
  private final IncomeMapper incomeMapper;
  private final FindAuthenticatedUser findAuthenticatedUser;

  public IncomeService(
      IncomeRepository incomeRepository,
      IncomeMapper incomeMapper,
      FindAuthenticatedUser findAuthenticatedUser) {
    this.incomeRepository = incomeRepository;
    this.incomeMapper = incomeMapper;
    this.findAuthenticatedUser = findAuthenticatedUser;
  }

  public IncomeResponse findCurrentUserIncome() {
    return incomeMapper.toIncomeResponse(
        incomeRepository
            .findFirstByUserOrderByYearMonthDescCreatedAtDesc(
                findAuthenticatedUser.getAuthenticatedUser())
            .orElseThrow(IncomeNotFoundException::new));
  }

  @Transactional
  public IncomeResponse updateCurrentUserIncome(IncomeRequest incomeRequest) {
    Income dbIncome =
        incomeRepository
            .findFirstByUserOrderByYearMonthDescCreatedAtDesc(
                findAuthenticatedUser.getAuthenticatedUser())
            .orElseThrow(IncomeNotFoundException::new);

    dbIncome.setAmount(incomeRequest.amount());
    dbIncome.setDescription(incomeRequest.description());

    Income savedIncome = incomeRepository.save(dbIncome);

    return incomeMapper.toIncomeResponse(savedIncome);
  }

  @Transactional
  public IncomeResponse setCurrentUserIncomeAmount(IncomeAmountRequest incomeAmountRequest) {
    Income income =
        incomeRepository
            .findFirstByUserOrderByYearMonthDescCreatedAtDesc(
                findAuthenticatedUser.getAuthenticatedUser())
            .orElseThrow(IncomeNotFoundException::new);

    income.setAmount(incomeAmountRequest.amount());

    Income savedIncome = incomeRepository.save(income);

    return incomeMapper.toIncomeResponse(savedIncome);
  }

  @Transactional
  public IncomeResponse createIncome(IncomeRequest incomeRequest) {
    return incomeMapper.toIncomeResponse(
        incomeRepository.save(
            incomeMapper.toIncomeEntity(
                incomeRequest, findAuthenticatedUser.getAuthenticatedUser())));
  }
}
