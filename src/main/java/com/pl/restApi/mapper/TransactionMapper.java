package com.pl.restApi.mapper;

import com.pl.restApi.dto.TransactionDto;
import com.pl.restApi.model.Transaction;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper( TransactionMapper.class );

    TransactionDto toTransactionDto(Transaction transaction);

    @InheritConfiguration
    Transaction toTransaction(TransactionDto transactionDto);
}
