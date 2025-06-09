package FAQ.Faq.service;

import FAQ.Faq.dto.CategorieDto;
import FAQ.Faq.dto.QuestionDto;
import FAQ.Faq.dto.ReponseDto;
import FAQ.Faq.models.Categorie;
import FAQ.Faq.models.Question;
import FAQ.Faq.models.Reponse;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface IQuestionService {
     Question addQuestion(QuestionDto questionDto );
     List<QuestionDto> retrieveAllQs();
     Reponse addReponse(Long questionId, ReponseDto reponseDto);
     QuestionDto retrieveQuestion(Long questionId);
     ReponseDto retrieveReponse(Long reponseId) ;
     void deleteQuestionAndReponse(Long questionId) ;
     QuestionDto updateQuestion(Long questionId, QuestionDto updatedQuestionDto);
     ReponseDto updateResponse(Long reponseId, ReponseDto updatedResponseDto);
     List<QuestionDto> searchQuestionsByKeyword(String keyword) ;
     List<QuestionDto> searchQuestionsByCreatedBy(String createdBy) ;
     List<QuestionDto> getQuestionsByCreationDate(String date) throws ParseException ;
     Categorie addCat(CategorieDto categorieDto);
     void deleteCat(Long id);
     Categorie updateCat(Long id, CategorieDto categorieDto);
     List<Categorie> getAllCategories();
    Categorie getCategorieById(Long id);
     List<CategorieDto> searchCategorieByName(String keyword);
     void importExcelFile(MultipartFile file);
     void deleteQuestionsAndResponses(List<Long> questionIds);

}
