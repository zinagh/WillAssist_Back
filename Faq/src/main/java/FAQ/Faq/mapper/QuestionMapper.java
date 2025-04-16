package FAQ.Faq.mapper;

import FAQ.Faq.dto.QuestionDto;
import FAQ.Faq.models.Question;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class QuestionMapper implements IQuestionMapper{
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public QuestionDto toDto(Question resp) {
        return modelMapper.map(resp, QuestionDto.class);
    }
    @Override
    public Question toEntity(QuestionDto questionDto) {
        return modelMapper.map(questionDto, Question.class);
    }
    @Override
    public List<QuestionDto> toDtoList(List<Question> questions) {
        return questions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<Question> toEntityList(List<QuestionDto> questionDtos) {
        return questionDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

}
