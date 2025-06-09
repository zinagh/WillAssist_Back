package FAQ.Faq.mapper;

import FAQ.Faq.dto.CategorieDto;
import FAQ.Faq.dto.QuestionDto;
import FAQ.Faq.models.Categorie;
import FAQ.Faq.models.Question;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategorieMapper implements ICategorieMapper{
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategorieDto toDto(Categorie categorie) {
        return modelMapper.map(categorie, CategorieDto.class);
    }
    @Override
    public Categorie toEntity(CategorieDto categorieDto) {
        return modelMapper.map(categorieDto, Categorie.class);
    }
    @Override
    public List<CategorieDto> toDtoList(List<Categorie> categories) {
        return categories.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<Categorie> toEntityList(List<CategorieDto> categorieDtos) {
        return categorieDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

}
