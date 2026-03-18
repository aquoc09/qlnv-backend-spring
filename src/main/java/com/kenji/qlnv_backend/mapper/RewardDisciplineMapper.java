package com.kenji.qlnv_backend.mapper;

import com.kenji.qlnv_backend.dto.request.RewardDisciplineRequest;
import com.kenji.qlnv_backend.dto.response.RewardDisciplineResponse;
import com.kenji.qlnv_backend.entity.RewardDiscipline;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RewardDisciplineMapper {

    @Mapping(target = "employee", ignore = true)
    RewardDiscipline toRewardDiscipline(RewardDisciplineRequest request);

    RewardDisciplineResponse toRewardDisciplineResponse(RewardDiscipline rewardDiscipline);

    @Mapping(target = "employee", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRewardDiscipline(@MappingTarget RewardDiscipline rewardDiscipline,
                                RewardDisciplineRequest request);
}
