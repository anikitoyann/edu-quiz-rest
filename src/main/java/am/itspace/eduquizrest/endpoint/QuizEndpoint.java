package am.itspace.eduquizrest.endpoint;

import com.example.eduquizcommon.dto.AnswerDto;
import com.example.eduquizcommon.dto.CreateQuizRequestDto;
import com.example.eduquizcommon.dto.QuizDto;
import com.example.eduquizcommon.dto.SingleQuizDto;
import com.example.eduquizcommon.entity.Answer;
import com.example.eduquizcommon.entity.Question;
import com.example.eduquizcommon.entity.QuestionOption;
import com.example.eduquizcommon.entity.Quiz;
import com.example.eduquizcommon.mapper.mapper.QuizMapper;
import com.example.eduquizcommon.service.QuestionOptionService;
import com.example.eduquizcommon.service.QuestionService;
import com.example.eduquizcommon.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizEndpoint {
    private final QuizService quizService;
    private final QuizMapper quizMapper;
    private final QuestionService questionService;
    private final QuestionOptionService questionOptionService;

    @PostMapping("/add")
    public ResponseEntity<QuizDto> create(@RequestBody CreateQuizRequestDto createQuizRequestDto) {
        Quiz quiz = quizService.save(quizMapper.map(createQuizRequestDto));
        return ResponseEntity.ok(quizMapper.mapToDto(quiz));
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuizDto>> getAllQuiz() {
        List<QuizDto> quizDto = quizMapper.mapListToDtos(quizService.findAll());
        return ResponseEntity.ok(quizDto);
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<?> deleteById(@PathVariable("quizId") int id) {
        quizService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> singleItem(@PathVariable("id") int id) {
        Optional<Quiz> byId = quizService.findById(id);
        if (byId.isPresent()) {
            Quiz quiz = byId.get();
            return ResponseEntity.ok(quiz);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/single/{quizId}")
    public ResponseEntity<SingleQuizDto> getSingleQuiz(@PathVariable int quizId) {
        Optional<Quiz> byId = quizService.findById(quizId);
        if (byId.isPresent()) {
            Quiz quiz = byId.get();
            List<Question> questions = questionService.findAllQuestionByQuiz_id(quizId);

            Map<Integer, AnswerDto> answers = new HashMap<>();
            for (Question question : questions) {
                AnswerDto answerDto = new AnswerDto();
                answerDto.setQuestion(question);
                answers.put(question.getId(), answerDto);
            }

            Map<Question, List<QuestionOption>> questionOptionsMap = new HashMap<>();
            for (Question question : questions) {
                List<QuestionOption> options = questionOptionService.getAllQuestionOptionByQuestionId(question.getId());
                questionOptionsMap.put(question, options);
            }
            SingleQuizDto responseDto = new SingleQuizDto();
            responseDto.setQuiz(quiz);
            responseDto.setQuestion((Question) questions);
            responseDto.setAnswer((Answer) answers);
            responseDto.setQuestionOption((QuestionOption) questionOptionsMap);


            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Quiz> update(@PathVariable("id") int id, @RequestBody Quiz quiz) {
        Optional<Quiz> byId = quizService.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Quiz quizDb = byId.get();
        if (quiz.getTitle() != null && !quiz.getTitle().isEmpty()) {
            quizDb.setTitle(quiz.getTitle());
        }
        if (quiz.getCreatedDateTime() != null) {
            quizDb.setCreatedDateTime(quiz.getCreatedDateTime());
        }
        return ResponseEntity.ok(quizService.save(quizDb));
    }
}
