package dev.jeantr35.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendDataDTO extends BestChoicesDTO{
    private String subject;
    private String to;
}
