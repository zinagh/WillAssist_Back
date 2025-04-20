package FAQ.Faq.service;

import FAQ.Faq.dto.QuestionDto;
import FAQ.Faq.dto.ReponseDto;
import FAQ.Faq.mapper.IQuestionMapper;
import FAQ.Faq.mapper.ReponseMapper;
import FAQ.Faq.models.Question;
import FAQ.Faq.models.Reponse;
import FAQ.Faq.repositories.QuestionRepo;
import FAQ.Faq.repositories.ReponseRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
   @Override
   public QuestionDto retrieveQuestion(Long questionId) {
      Optional<Question> questionOptional = questionRepository.findById(questionId);
      if (questionOptional.isPresent()) {
         return questionMapper.toDto(questionOptional.get());
      } else {
         throw new EntityNotFoundException("Question with ID " + questionId + " not found");
      }
   }
   @Override
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
      Date currentDate = new Date();
      reponse.setCreationDate(currentDate);

      reponse.setQuestion(question);
      question.setReponse(reponse);

      questionRepository.save(question);
      return reponse;
   }

   @Override
   public ReponseDto retrieveReponse(Long questionId) {
      Optional<Reponse> reponseOptional = reponseRepository.findByQuestionId(questionId);
      if (reponseOptional.isPresent()) {
         return responseMapper.toDto(reponseOptional.get());
      } else {
         throw new EntityNotFoundException("Response for question ID " + questionId + " not found");
      }
   }
   @Override
   @Transactional
   public void deleteQuestionAndReponse(Long questionId) {
      Question question = questionRepository.findById(questionId)
              .orElseThrow(() -> new EntityNotFoundException("Question with ID " + questionId + " not found"));

      // Optional: if you want to be extra safe with bidirectional relation
      if (question.getReponse() != null) {
         Reponse reponse = question.getReponse();
         reponse.setQuestion(null); // break the link
         reponseRepository.delete(reponse); // not strictly necessary if cascade works
      }

      questionRepository.delete(question); // This should cascade and delete the response
   }

   @Override
   @Transactional
   public QuestionDto updateQuestion(Long questionId, QuestionDto updatedQuestionDto) {
      Question existingQuestion = questionRepository.findById(questionId)
              .orElseThrow(() -> new EntityNotFoundException("Question with ID " + questionId + " not found"));

      if (updatedQuestionDto.getQuestionText() != null) {
         existingQuestion.setQuestionText(updatedQuestionDto.getQuestionText());
      }
      if (updatedQuestionDto.getCreatedby() != null) {
         existingQuestion.setCreatedby(updatedQuestionDto.getCreatedby());
      }

      Question savedQuestion = questionRepository.save(existingQuestion);
      return questionMapper.toDto(savedQuestion);
   }

   @Override
   @Transactional
   public ReponseDto updateResponse(Long reponseId, ReponseDto updatedResponseDto){
      Reponse existingResponse= reponseRepository.findById(reponseId)
              .orElseThrow(() -> new EntityNotFoundException("Response with ID " + reponseId + " not found"));

      if (updatedResponseDto.getResponseText() != null) {
         existingResponse.setResponseText(updatedResponseDto.getResponseText());
      }


      Reponse savedQuestion = reponseRepository.save(existingResponse);
      return responseMapper.toDto(savedQuestion);
   }
   public List<QuestionDto> searchQuestionsByKeyword(String keyword) {
      List<Question> questions = questionRepository.findByQuestionTextContainingIgnoreCase(keyword);
      return questionMapper.toDtoList(questions);
   }

   public List<QuestionDto> searchQuestionsByCreatedBy(String createdBy) {
      List<Question> questions = questionRepository.findByCreatedbyContainingIgnoreCase(createdBy);
      return questionMapper.toDtoList(questions);
   }

}
