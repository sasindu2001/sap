package lk.kdu.sap.dto;

import lombok.*;

/**
 * @author Sasindu Malshan
 * @project SAP
 * @date 4/26/2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DiscountModelDTO {
    String no;
    String name;
    String code;
    String discount;
}
