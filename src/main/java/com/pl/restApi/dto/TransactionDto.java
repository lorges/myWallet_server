package com.pl.restApi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.pl.restApi.model.Tag;
import com.pl.restApi.validator.annotation.IsValidTransactionKind;
import com.pl.restApi.validator.annotation.IsValidTransactionType;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionDto implements Serializable {

    @NotNull
    private String id;

    @NotNull
    @Length(min = 5)
    private String transactionName;

    private String transactionDesc;

    @Past
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate transactionDate;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal transactionAmount;

    @NotNull
    @IsValidTransactionType
    private String transactionType;

    @IsValidTransactionKind
    private String transactionKind;

    /*private Set<TagDto> tags;*/

    public String getTransactionKind() {
        if("".equals(this.transactionKind)) {
            return null;
        }
        return transactionKind;
    }
}
