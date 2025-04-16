package FAQ.Faq.mapper;

import FAQ.Faq.dto.ReponseDto;
import FAQ.Faq.models.Reponse;

import java.util.List;

public interface IReponseMapper {
    public ReponseDto toDto(Reponse resp);
    public Reponse toEntity(ReponseDto respDto);
    public List<ReponseDto> toDtoList(List<Reponse> reponses);
    public List<Reponse> toEntityList(List<ReponseDto> reponsesDtos);
}
