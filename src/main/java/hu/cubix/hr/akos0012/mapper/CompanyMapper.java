package hu.cubix.hr.akos0012.mapper;

import hu.cubix.hr.akos0012.dto.CompanyDTO;
import hu.cubix.hr.akos0012.model.Company;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    public CompanyDTO companyToDto(Company company);

    public List<CompanyDTO> companiesToDtos(List<Company> companies);

    public Company dtoToCompany(CompanyDTO companyDTO);

}
