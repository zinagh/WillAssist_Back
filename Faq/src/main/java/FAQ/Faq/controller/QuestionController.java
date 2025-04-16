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

    @PostMapping("/reponse/{questionId}")
    public ResponseEntity<Reponse> addReponseToQuestion(
            @PathVariable Long questionId,
            @RequestBody ReponseDto reponseDto) {
        return ResponseEntity.ok(questionService.addReponse(questionId, reponseDto));
    }
}
