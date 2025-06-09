package FAQ.Faq.service;

import FAQ.Faq.dto.CategorieDto;
import FAQ.Faq.dto.QuestionDto;
import FAQ.Faq.dto.ReponseDto;
import FAQ.Faq.mapper.CategorieMapper;
import FAQ.Faq.mapper.IQuestionMapper;
import FAQ.Faq.mapper.ReponseMapper;
import FAQ.Faq.models.Categorie;
import FAQ.Faq.models.Question;
import FAQ.Faq.models.Reponse;
import FAQ.Faq.repositories.CategorieRepo;
import FAQ.Faq.repositories.QuestionRepo;
import FAQ.Faq.repositories.ReponseRepo;
import jakarta.persistence.EntityNotFoundException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.mapping.Formula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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


   @Autowired
   private CategorieRepo categorieRepo;

   @Autowired
   private CategorieMapper categorieMapper;

   @Override
   @Transactional
   public Question addQuestion(QuestionDto questionDto) {
      Question question = questionMapper.toEntity(questionDto);
      Date currentDate = new Date();
      question.setCreationDate(currentDate);


      return questionRepository.save(question);
   }


   @Override
   @Transactional
   public Categorie addCat(CategorieDto categorieDto) {
      Categorie categorie = categorieMapper.toEntity(categorieDto);


      return categorieRepo.save(categorie);
   }

   @Override
   @Transactional(readOnly = true)
   public List<Categorie> getAllCategories() {
      return categorieRepo.findAll();
   }

   @Override
   @Transactional
   public Categorie updateCat(Long id, CategorieDto categorieDto) {
      Categorie existingCategorie = categorieRepo.findById(id)
              .orElseThrow(() -> new RuntimeException("Categorie not found with id: " + id));

      existingCategorie.setCategorieName(categorieDto.getCategorieName());
      // Update other fields if you add more later

      return categorieRepo.save(existingCategorie);
   }

   @Override
   @Transactional
   public void deleteCat(Long id) {
      Categorie existingCategorie = categorieRepo.findById(id)
              .orElseThrow(() -> new RuntimeException("Categorie not found with id: " + id));

      categorieRepo.delete(existingCategorie);
   }

   @Override
   @Transactional(readOnly = true)
   public Categorie getCategorieById(Long id) {
      return categorieRepo.findById(id)
              .orElseThrow(() -> new RuntimeException("Categorie not found with id: " + id));
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
   public List<QuestionDto> retrieveAllQs() {
      List<Question> questionList = questionRepository.findAll();
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

      // Validate question text (example validation: not empty or null)
      if (updatedQuestionDto.getQuestionText() != null) {
         String questionText = updatedQuestionDto.getQuestionText().trim();
         if (questionText.isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be empty");
         }
         existingQuestion.setQuestionText(questionText);
      }

      // Validate created by (example: ensure it's a valid user or not null)
      if (updatedQuestionDto.getCreatedby() != null) {
         String createdBy = updatedQuestionDto.getCreatedby().trim();
         if (createdBy.isEmpty()) {
            throw new IllegalArgumentException("Creator field cannot be empty");
         }
         existingQuestion.setCreatedby(createdBy);
      }

      // Validate and update the category if a new one is provided
      if (updatedQuestionDto.getCategorie() != null) {
         CategorieDto updatedCategorieDto = updatedQuestionDto.getCategorie();
         if (updatedCategorieDto.getIdCategorie() == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
         }

         // Convert CategorieDto to Categorie entity
         Categorie updatedCategorie = categorieRepo.findById(updatedCategorieDto.getIdCategorie())
                 .orElseThrow(() -> new EntityNotFoundException("Category with ID " + updatedCategorieDto.getIdCategorie() + " not found"));

         existingQuestion.setCategorie(updatedCategorie);
      }

      // Save the updated question
      Question savedQuestion = questionRepository.save(existingQuestion);

      // Map to DTO and return
      return questionMapper.toDto(savedQuestion);
   }

   @Override
   @Transactional
   public ReponseDto updateResponse(Long reponseId, ReponseDto updatedResponseDto) {
      Reponse existingResponse = reponseRepository.findById(reponseId)
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

   @Override
   public List<QuestionDto> getQuestionsByCreationDate(String date) throws ParseException {
      List<Question> questions = new ArrayList<>();

      if (date == null || date.isEmpty()) {
         questions = questionRepository.findAll();
         return questionMapper.toDtoList(questions);
      }

      // Try parsing the date
      Date parsedDate = null;
      SimpleDateFormat sdfIso = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH); // Ex: "2024-11-07"
      SimpleDateFormat sdfFullDate = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH); // Ex: "Nov 07, 2024"
      SimpleDateFormat sdfMonthDay = new SimpleDateFormat("MMM dd", Locale.ENGLISH); // Ex: "Nov 07"
      SimpleDateFormat sdfMonth = new SimpleDateFormat("MMM", Locale.ENGLISH); // Ex: "Nov"
      SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy", Locale.ENGLISH); // Ex: "2024"

      // Try parsing with ISO format first
      try {
         parsedDate = sdfIso.parse(date);
      } catch (ParseException e) {
         parsedDate = null;
      }

      // Then try "MMM dd, yyyy"
      if (parsedDate == null) {
         try {
            parsedDate = sdfFullDate.parse(date);
         } catch (ParseException e) {
            parsedDate = null;
         }
      }

      // Then try "MMM dd"
      if (parsedDate == null) {
         try {
            parsedDate = sdfMonthDay.parse(date);
            // Set the current year if only month and day provided
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
            parsedDate = calendar.getTime();
         } catch (ParseException e) {
            parsedDate = null;
         }
      }

      // Then try "MMM"
      if (parsedDate == null) {
         try {
            parsedDate = sdfMonth.parse(date);
            // Set first day of the month and current year
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
            parsedDate = calendar.getTime();
         } catch (ParseException e) {
            parsedDate = null;
         }
      }

      // Then try "yyyy"
      if (parsedDate == null && date.length() == 4 && isNumeric(date)) {
         try {
            parsedDate = sdfYear.parse(date);
         } catch (ParseException e) {
            parsedDate = null;
         }
      }

      // Get questions based on the parsed date
      questions = getQuestionsBasedOnDate(date, parsedDate);
      return questionMapper.toDtoList(questions);
   }

   private List<Question> getQuestionsBasedOnDate(String date, Date parsedDate) throws ParseException {
      List<Question> questions = new ArrayList<>();
      if (parsedDate == null) {
         throw new ParseException("Invalid date format", 0);
      }

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(parsedDate);

      int month = calendar.get(Calendar.MONTH) + 1;
      int day = calendar.get(Calendar.DAY_OF_MONTH);
      int year = calendar.get(Calendar.YEAR);

      if (date.length() == 4 && isNumeric(date)) {
         questions = questionRepository.findByYear(year);
      } else if (date.length() >= 3 && !date.contains(" ") && !date.contains("-")) {
         questions = questionRepository.findByMonth(month);
      } else if (date.length() <= 2 && isNumeric(date)) {
         questions = questionRepository.findByDay(day);
      } else {
         // 🎯 Si c'est une date complète, il faut préparer le début et fin de journée
         Calendar startCal = Calendar.getInstance();
         startCal.setTime(parsedDate);
         startCal.set(Calendar.HOUR_OF_DAY, 0);
         startCal.set(Calendar.MINUTE, 0);
         startCal.set(Calendar.SECOND, 0);
         startCal.set(Calendar.MILLISECOND, 0);

         Calendar endCal = Calendar.getInstance();
         endCal.setTime(parsedDate);
         endCal.set(Calendar.HOUR_OF_DAY, 23);
         endCal.set(Calendar.MINUTE, 59);
         endCal.set(Calendar.SECOND, 59);
         endCal.set(Calendar.MILLISECOND, 999);

         questions = questionRepository.findByCreationDateBetween(
                 startCal.getTime(),
                 endCal.getTime()
         );
      }

      return questions;
   }

   private boolean isNumeric(String str) {
      try {
         Integer.parseInt(str);
         return true;
      } catch (NumberFormatException e) {
         return false;
      }
   }

   public List<CategorieDto> searchCategorieByName(String keyword) {
      List<Categorie> categories = categorieRepo.findByCategorieNameContainingIgnoreCase(keyword);
      return categorieMapper.toDtoList(categories);
   }

   public void importExcelFile(MultipartFile file) {
      try (InputStream is = file.getInputStream();
           Workbook workbook = new XSSFWorkbook(is)) {

         Sheet sheet = workbook.getSheetAt(0);
         if (sheet == null) {
            throw new RuntimeException("No sheet found in the Excel file.");
         }

         for (Row row : sheet) {
            if (row.getRowNum() == 0) {
               continue; // Skip header
            }

            // Check if the row is empty or has insufficient cells
            if (row.getLastCellNum() < 3) {
               System.err.println("Skipping row " + row.getRowNum() + ": Insufficient columns.");
               continue;
            }

            // Safely retrieve cell values
            String questionText = getCellStringValue(row.getCell(0), row.getRowNum(), 0);
            String responseText = getCellStringValue(row.getCell(1), row.getRowNum(), 1);
            String categorieName = getCellStringValue(row.getCell(2), row.getRowNum(), 2);

            // Skip row if any required field is missing
            if (questionText == null || responseText == null || categorieName == null) {
               System.err.println("Skipping row " + row.getRowNum() + ": Missing required data.");
               continue;
            }

            // Find or create Categorie
            Categorie categorie = categorieRepo.findByCategorieName(categorieName);
            if (categorie == null) {
               categorie = new Categorie();
               categorie.setCategorieName(categorieName);
               categorie = categorieRepo.save(categorie);
            }

            // Create Question
            Question question = new Question();
            question.setQuestionText(questionText);
            question.setCreationDate(new Date());
            question.setCreatedby("Excel Import");
            question.setCategorie(categorie);

            Question savedQuestion = questionRepository.save(question);

            // Create Reponse
            Reponse reponse = new Reponse();
            reponse.setResponseText(responseText);
            reponse.setCreationDate(new Date());
            reponse.setCreatedby("Excel Import");
            reponse.setQuestion(savedQuestion);

            reponseRepository.save(reponse);
         }

      } catch (Exception e) {
         e.printStackTrace();
         throw new RuntimeException("Failed to import data from Excel file: " + e.getMessage());
      }
   }

   // Helper method to safely retrieve string cell value
   private String getCellStringValue(Cell cell, int rowNum, int colNum) {
      if (cell == null) {
         System.err.println("Cell at row " + rowNum + ", column " + colNum + " is null.");
         return null;
      }

      try {
         switch (cell.getCellType()) {
            case STRING:
               return cell.getStringCellValue().trim();
            case NUMERIC:
               return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
               return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
               return cell.getCellFormula();
            default:
               System.err.println("Unsupported cell type at row " + rowNum + ", column " + colNum + ": " + cell.getCellType());
               return null;
         }
      } catch (Exception e) {
         System.err.println("Error reading cell at row " + rowNum + ", column " + colNum + ": " + e.getMessage());
         return null;
      }
   }

   @Override
   @Transactional
   public void deleteQuestionsAndResponses(List<Long> questionIds) {
      for (Long questionId : questionIds) {
         deleteQuestionAndReponse(questionId); // Reuse your safe method
      }
   }
}