package hu.cubix.hr.akos0012.mapper;

import hu.cubix.hr.akos0012.dto.CompanyDTO;
import hu.cubix.hr.akos0012.model.Company;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDTO companyToDto(Company company);

    @Mapping(target = "employees", ignore = true)
    @Named("summary")
    CompanyDTO companyToSummaryDto(Company company);

    List<CompanyDTO> companiesToDtos(List<Company> companies);

    @IterableMapping(qualifiedByName = "summary")
    List<CompanyDTO> companiesToSummaryDtos(List<Company> companies);

    Company dtoToCompany(CompanyDTO companyDTO);

    default Page<CompanyDTO> pagedCompanyToDto(Page<Company> pagedCompany) {
        return pagedCompany.map(this::companyToDto);
    }

}
