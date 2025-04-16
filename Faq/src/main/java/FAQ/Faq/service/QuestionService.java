package FAQ.Faq.service;

import FAQ.Faq.dto.QuestionDto;
import FAQ.Faq.dto.ReponseDto;
import FAQ.Faq.mapper.IQuestionMapper;
import FAQ.Faq.mapper.ReponseMapper;
import FAQ.Faq.models.Question;
import FAQ.Faq.models.Reponse;
import FAQ.Faq.repositories.QuestionRepo;
import FAQ.Faq.repositories.ReponseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QuestionService implements IQuestionService {
   @Autowired
   private QuestionRepo questionRepository;
   @Autowired
   private IQuestionMapper questionMapper;

   @Autowired
   private ReponseRepo reponseRepository;

   @Autowired
   private ReponseMapper responseMapper;
   @Override
   @Transactional
   public Question addQuestion(QuestionDto questionDto) {
      Question question = questionMapper.toEntity(questionDto);
      Date currentDate = new Date();
      question.setCreationDate(currentDate);



      return questionRepository.save(question);
   }


   public List<QuestionDto> retrieveAllQs(){
      List<Question> questionList=questionRepository.findAll();
      return questionMapper.toDtoList(questionList);

   }


   @Override
   @Transactional
   public Reponse addReponse(Long questionId, ReponseDto reponseDto) {
      System.out.println("Received ReponseDto: " + reponseDto);
      Question question = questionRepository.findById(questionId)
              .orElseThrow(() -> new RuntimeException("Question not found"));

      Reponse reponse = responseMapper.toEntity(reponseDto);

      reponse.setQuestion(question); // Line ~61
      question.setReponse(reponse);

      questionRepository.save(question);
      return reponse;
   }



}
