package com.example.livebetting.data.model.response;

import com.example.livebetting.data.model.dto.CouponDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserCouponsResponseModel {
    private List<CouponDto> coupons;
}
