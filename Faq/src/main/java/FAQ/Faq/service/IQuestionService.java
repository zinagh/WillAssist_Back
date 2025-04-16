package FAQ.Faq.service;

import FAQ.Faq.dto.QuestionDto;
import FAQ.Faq.dto.ReponseDto;
import FAQ.Faq.models.Question;
import FAQ.Faq.models.Reponse;

import java.util.List;

public interface IQuestionService {
    Question addQuestion(QuestionDto questionDto );
    List<QuestionDto> retrieveAllQs();
    Reponse addReponse(Long questionId, ReponseDto reponseDto);
   /*
    public Question modifyQst(QuestionDto questionDto);
    void removeQ(Long idQ);
        Question retrieveQ(Long idQ) ;

*/
}
