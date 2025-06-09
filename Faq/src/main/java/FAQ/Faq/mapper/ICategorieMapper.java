package FAQ.Faq.mapper;

import FAQ.Faq.dto.CategorieDto;
import FAQ.Faq.dto.QuestionDto;
import FAQ.Faq.models.Categorie;
import FAQ.Faq.models.Question;

import java.util.List;

public interface ICategorieMapper {

    public CategorieDto toDto(Categorie categorie);
    public Categorie toEntity(CategorieDto categorieDto);
    public List<CategorieDto> toDtoList(List<Categorie> categories);
    public List<Categorie> toEntityList(List<CategorieDto> categorieDtos);
}
