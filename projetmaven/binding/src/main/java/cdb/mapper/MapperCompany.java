package cdb.mapper;

import cdb.dto.CompanyDTO;
import cdb.model.Company;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapperCompany {

    /**
     * Company translated to DTO.
     *
     * @param company to be translated
     * @return dto
     */
    public CompanyDTO toDto(Company company) {
        if (company == null) {
            return null;
        }
        return new CompanyDTO.Builder().withId(company.getId())
                .withName(company.getName()).build();
    }

    /**
     * Translate companies to companiesdto.
     *
     * @param companyList to be translated
     * @return list of companydto
     */
    public List<CompanyDTO> toDtos(List<Company> companyList) {
        if (companyList == null) {
            return new ArrayList<>();
        }

        List<CompanyDTO> translated = new ArrayList<>();
        for (Company company : companyList) {
            translated.add(toDto(company));
        }

        return translated;
    }

}
