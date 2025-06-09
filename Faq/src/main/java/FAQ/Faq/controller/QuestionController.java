package FAQ.Faq.controller;

import FAQ.Faq.dto.CategorieDto;
import FAQ.Faq.dto.QuestionDto;
import FAQ.Faq.dto.ReponseDto;
import FAQ.Faq.models.Categorie;
import FAQ.Faq.models.Question;
import FAQ.Faq.models.Reponse;
import FAQ.Faq.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/searchByCreationDate")
    public List<QuestionDto> searchQuestionsByCreationDate(@RequestParam String creationDate) throws Exception {
        return questionService.getQuestionsByCreationDate(creationDate);
    }

    @PostMapping("/add")
    public Categorie addCategorie(@RequestBody CategorieDto categorieDto) {
        return questionService.addCat(categorieDto);
    }

    @GetMapping("/all")
    public List<Categorie> getAllCategories() {
        return questionService.getAllCategories();
    }

    @GetMapping("/getByIdCat/{id}")
    public Categorie getCategorieById(@PathVariable Long id) {
        return questionService.getCategorieById(id);
    }

    @PutMapping("/update/{id}")
    public Categorie updateCategorie(@PathVariable Long id, @RequestBody CategorieDto categorieDto) {
        return questionService.updateCat(id, categorieDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategorie(@PathVariable Long id) {
        questionService.deleteCat(id);
    }

    @GetMapping("/searchCategorieByName")
    public List<CategorieDto> searchCategorieByName(@RequestParam String keyword) {
        return questionService.searchCategorieByName(keyword);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        try {
            questionService.importExcelFile(file);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

    @DeleteMapping("/questions")
    public void deleteQuestions(@RequestBody List<Long> questionIds) {
        questionService.deleteQuestionsAndResponses(questionIds);
    }

}
