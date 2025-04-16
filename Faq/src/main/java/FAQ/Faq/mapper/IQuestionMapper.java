package FAQ.Faq.mapper;

import FAQ.Faq.dto.QuestionDto;
import FAQ.Faq.models.Question;

import java.util.List;

public interface IQuestionMapper {
    public QuestionDto toDto(Question question);
    public Question toEntity(QuestionDto questionDto);
    public List<QuestionDto> toDtoList(List<Question> questions);
    public List<Question> toEntityList(List<QuestionDto> questionDtos);
}
