package FAQ.Faq.mapper;

import FAQ.Faq.dto.ReponseDto;
import FAQ.Faq.models.Reponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReponseMapper implements IReponseMapper {


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReponseDto toDto(Reponse resp) {
        return modelMapper.map(resp, ReponseDto.class);
    }
    @Override
    public Reponse toEntity(ReponseDto respDto) {
        return modelMapper.map(respDto, Reponse.class);
    }
    @Override
    public List<ReponseDto> toDtoList(List<Reponse> reponses) {
        return reponses.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<Reponse> toEntityList(List<ReponseDto> reponsesDtos) {
        return reponsesDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }


}
