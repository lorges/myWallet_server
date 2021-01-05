package com.pl.restApi.mapper;

import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.model.Transaction;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TransactionMapper {

    @Named("mapToDto")
    TransactionDto toTransactionDto(Transaction transaction);

    @InheritConfiguration
    Transaction toTransaction(TransactionDto transactionDto);

    @Mapping(ignore = true, target = "id")
    void updateTransactiontFromDTO(TransactionDto transactionDto, @MappingTarget Transaction transaction);

    @IterableMapping(qualifiedByName = "mapToDto")
    List<TransactionDto> mapToTransactionDTOList(List<Transaction> transactionList);
}
