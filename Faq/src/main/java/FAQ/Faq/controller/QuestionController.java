package FAQ.Faq.controller;

import FAQ.Faq.dto.QuestionDto;
import FAQ.Faq.dto.ReponseDto;
import FAQ.Faq.models.Question;
import FAQ.Faq.models.Reponse;
import FAQ.Faq.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {
    @Autowired
    private IQuestionService questionService;

    @PostMapping("/addQ")
    public ResponseEntity<Question> addQuestion(@RequestBody QuestionDto questionDto) {
        Question savedQuestion = questionService.addQuestion(questionDto);

        return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
    }

    @GetMapping("/retrieveAllQ")
    public ResponseEntity<List<QuestionDto>> getAllQuestions() {
        List<QuestionDto> questions = questionService.retrieveAllQs();
        return ResponseEntity.ok(questions);
    }
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable  Long id) {
        QuestionDto questionDto = questionService.retrieveQuestion(id);
        return ResponseEntity.ok(questionDto);
    }
    @PostMapping("/reponse/{questionId}")
    public ResponseEntity<Reponse> addReponseToQuestion(
            @PathVariable Long questionId,
            @RequestBody ReponseDto reponseDto) {
        return ResponseEntity.ok(questionService.addReponse(questionId, reponseDto));
    }

    @GetMapping("/byQuestion/{questionId}")
    public ReponseDto getResponseByQuestionId(@PathVariable Long questionId) {
        return questionService.retrieveReponse(questionId);
    }
    @DeleteMapping("/deleteQuestion/{id}")
    public void deleteQuestion(@PathVariable Long id) {
            questionService.deleteQuestionAndReponse(id);
    }


    @PutMapping("/updateQ/{id}")
    public QuestionDto updateQuestion(@PathVariable Long id, @RequestBody QuestionDto updatedQuestionDto) {
        return questionService.updateQuestion(id, updatedQuestionDto);
    }

    @PutMapping("/updateR/{id}")
    public ReponseDto updateR(@PathVariable Long id, @RequestBody ReponseDto updatedRDto) {
        return questionService.updateResponse(id, updatedRDto);
    }
    @GetMapping("/search")
    public List<QuestionDto> searchQuestions(@RequestParam("keyword") String keyword) {
        return questionService.searchQuestionsByKeyword(keyword);
    }
    @GetMapping("/searchByCreatedBy")
    public List<QuestionDto> searchQuestionsByCreatedBy(@RequestParam("createdBy") String createdBy) {
        return questionService.searchQuestionsByCreatedBy(createdBy);
    }

}
