package spring.api.uteating.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import spring.api.uteating.dto.EmployerRegisterRequest;
import spring.api.uteating.dto.UpdateStatusRequest;
import spring.api.uteating.entity.Employer;
import spring.api.uteating.model.EmployerModel;

@Mapper(componentModel = "spring")
public abstract class EmployerMapper {
    @Mapping(target = "id", source = "userId")
    @Mapping(target = "company_name", source = "companyName")
    @Mapping(target = "full_name", source = "fullName")
    public abstract EmployerModel toEmployerModel(Employer employer);

    public abstract Employer toEmployer(EmployerRegisterRequest employerRegisterRequest);

    public abstract void updateStatusFromDto(UpdateStatusRequest updateStatusRequest, @MappingTarget Employer employer);
}
