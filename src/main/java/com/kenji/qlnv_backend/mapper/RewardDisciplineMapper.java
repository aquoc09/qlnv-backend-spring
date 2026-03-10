package com.kenji.qlnv_backend.mapper;

import com.kenji.qlnv_backend.dto.request.RewardDisciplineRequest;
import com.kenji.qlnv_backend.dto.response.RewardDisciplineResponse;
import com.kenji.qlnv_backend.entity.RewardDiscipline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RewardDisciplineMapper {

    @Mapping(target = "employee", ignore = true)
    RewardDiscipline toRewardDiscipline(RewardDisciplineRequest request);

    RewardDisciplineResponse toRewardDisciplineResponse(RewardDiscipline rewardDiscipline);

    @Mapping(target = "employee", ignore = true)
    void updateRewardDiscipline(@MappingTarget RewardDiscipline rewardDiscipline,
                                RewardDisciplineRequest request);
}
